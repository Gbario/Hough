package hough;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class houghCirculos {

    public static void main(String[] args) {
        // Cargar la librer�a nativa de OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Cargar la imagen en escala de grises (blanco y negro)
        Mat image = Imgcodecs.imread("bloque2.png", Imgcodecs.IMREAD_GRAYSCALE);

        // Verificar si la imagen fue cargada correctamente
        if (image.empty()) {
            System.out.println("No se pudo cargar la imagen.");
            return;
        }


        // Mat para almacenar la imagen con bordes detectados
        Mat edges = new Mat();

        // Aplicar el detector de bordes Canny

        Imgproc.Canny(image, edges, 185, 320);

        // Guardar la imagen con los bordes detectados
        Imgcodecs.imwrite("bordes_circulos.jpg", edges);
        System.out.println("Imagen con bordes detectados generada correctamente.");

        // Mat para almacenar los c�rculos detectados

        Mat circles = new Mat();
        
        // Aplicar la Transformada de Hough para detectar c�rculos

        Imgproc.HoughCircles(edges, circles, Imgproc.HOUGH_GRADIENT, 1, 10, 200, 60, 10, 100);

        // Dibujar los c�rculos detectados sobre la imagen original
        for (int i = 0; i < circles.cols(); i++) {
            // Obtener los valores de centro y radio para cada c�rculo detectado
            double[] data = circles.get(0, i);
            int x = (int) data[0]; // Coordenada x del centro
            int y = (int) data[1]; // Coordenada y del centro
            int radius = (int) data[2]; // Radio del c�rculo

            if (radius > 50) {
                // Dibujar el c�rculo que estamos buscando con linea mas gruesa
                Imgproc.circle(image, new Point(x, y), radius, new Scalar(0, 0, 0), 5);
                System.out.println("Posible perforaci�n roscada detectada en: (" + x + ", " + y + ") con radio: " + radius);
            } else {
                // Dibujar el resto de los c�rculos con linea mas fina
                Imgproc.circle(image, new Point(x, y), radius, new Scalar(0, 0, 0), 2);
            }

            // Dibujar el centro del c�rculo
            Imgproc.circle(image, new Point(x, y), 2, new Scalar(0, 0, 0), 3);
        }
        

        // Guardar la imagen con los c�rculos detectados
        Imgcodecs.imwrite("resultado_circulos.jpg", image);
        System.out.println("Imagen con c�rculos detectados generada correctamente.");
    }
}
