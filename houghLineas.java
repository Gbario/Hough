package hough;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class houghLineas {

    public static void main(String[] args) {
        // Cargar la librer�a nativa de OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Cargar la imagen en escala de grises (blanco y negro)
        // Esto simplifica el proceso de detecci�n de bordes
        Mat image = Imgcodecs.imread("bloque.jpg", Imgcodecs.IMREAD_GRAYSCALE);

        // Mat para almacenar la imagen con bordes detectados
        Mat edges = new Mat();
        
        // Aplicar el detector de bordes Canny
        // Los valores 100 y 200 son los umbrales m�nimo y m�ximo respectivamente
        Imgproc.Canny(image, edges, 100, 200);

        // Guardar la imagen con los bordes detectados
        Imgcodecs.imwrite("bordes.jpg", edges);
        System.out.println("Imagen con bordes detectados generada correctamente.");
        
        // Crear un objeto Mat para almacenar las l�neas detectadas
        Mat lines = new Mat();
        
        // Aplicar la Transformada de Hough para detectar las l�neas en la imagen de bordes
        Imgproc.HoughLines(edges, lines, 1, Math.PI / 180, 125);

        // Dibujar las l�neas detectadas sobre la imagen original
        // La transformaci�n de Hough devuelve las l�neas en forma de par�metros rho y theta
        for (int i = 0; i < lines.rows(); i++) {
            // Obtener los valores de rho y theta para cada l�nea detectada
            double[] data = lines.get(i, 0);
            double rho = data[0];
            double theta = data[1];

            // Calcular las coordenadas (x0, y0) del punto de intersecci�n de la l�nea
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            double x0 = a * rho;
            double y0 = b * rho;

            // Calcular dos puntos en la l�nea para dibujarla en la imagen
            Point pt1 = new Point(Math.round(x0 + 1000 * (-b)), Math.round(y0 + 1000 * (a)));
            Point pt2 = new Point(Math.round(x0 - 1000 * (-b)), Math.round(y0 - 1000 * (a)));

            // Dibujar la l�nea en la imagen original
            Imgproc.line(image, pt1, pt2, new Scalar(0, 0, 0), 1);
        }

        // Guardar la imagen con las l�neas detectadas
        Imgcodecs.imwrite("resultado_lineas.jpg", image);
        System.out.println("Imagen con l�neas detectadas generada correctamente.");
    }
}