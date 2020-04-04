import colorObjects.ColorImage;
import grayscaleObjects.GrayscaleImage;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class Main {

    public static void main (String[] args) {
        if (args.length != 1) {
            System.out.println("Please specify the image as argument of the program");
            return;
        }

        System.out.println("Input :\n" + args[0]);

        /*
        TIVO : TD1 Exercice 3 Questions a, b, c et d
         */

        componentDivider(args[0]); //a
        grayscaleConverter(args[0]); //b
        negativeConverter(args[0]); //c
        halfImage(args[0]); //d
    }

    // Divide an image into 3 different one each containing only a component (red, green and blue)
    private static void componentDivider (String filename) {
        System.out.println("\nDividing image to 3 images corresponding to each individual component...");

        //parse the image to a ColoredImage object
        ColorImage ci = ImageParser.getINSTANCE().parse(filename);

        //create images corresponding to each component only
        ColorImage ci_r = ci.toRedComponent();
        ColorImage ci_g = ci.toGreenComponent();
        ColorImage ci_b = ci.toBlueComponent();

        //filename separator between name and extension
        int sep = filename.length() - 4;

        //filenames for the output
        String filename_r = filename.substring(0, sep) + "_r" + filename.substring(sep);
        String filename_g = filename.substring(0, sep) + "_g" + filename.substring(sep);
        String filename_b = filename.substring(0, sep) + "_b" + filename.substring(sep);

        //write the component only images
        ImageWriter.getINSTANCE().writePPM(filename_r, ci_r);
        ImageWriter.getINSTANCE().writePPM(filename_g, ci_g);
        ImageWriter.getINSTANCE().writePPM(filename_b, ci_b);

        System.out.println("Done! Outputs :\n" + filename_r + "\n" + filename_g + "\n" + filename_b);
    }

    // Convert the image to a grayscale or itself
    private static void grayscaleConverter (String filename) {
        System.out.println("\nConverting image to grayscale...");

        //parse the image to a ColoredImage object
        ColorImage ci = ImageParser.getINSTANCE().parse(filename);

        GrayscaleImage gi = ci.toGrayscale();

        //filename separator between name and extension
        int sep = filename.length() - 4;

        //filename for the output
        String filename_gs = filename.substring(0, sep) + "_gs" + filename.substring(sep);

        //write the grayscale version of the image to a pgm file
        ImageWriter.getINSTANCE().writePGM(filename_gs, gi);

        System.out.println("Done! Output :\n" + filename_gs);
    }

    // Convert the image to a negative of itself
    private static void negativeConverter (String filename) {
        System.out.println("\nConverting image to negative...");

        //parse the image to a ColoredImage object
        ColorImage ci = ImageParser.getINSTANCE().parse(filename);

        ColorImage ci_n = ci.toNegative();

        //filename separator between name and extension
        int sep = filename.length() - 4;

        //filename for the output
        String filename_n = filename.substring(0, sep) + "_n" + filename.substring(sep);

        //write the negative version of the image to a ppm file
        ImageWriter.getINSTANCE().writePPM(filename_n, ci_n);

        System.out.println("Done! Output :\n" + filename_n);
    }

    // half down the image
    private static void halfImage (String filename) {
        System.out.println("\nHalfing the image...");

        //parse the image to a ColoredImage object
        ColorImage ci = ImageParser.getINSTANCE().parse(filename);

        //filename separator between name and extension
        int sep = filename.length() - 4;

        //filename for the output
        String filename_h = filename.substring(0, sep) + "_h" + filename.substring(sep);

        //write the halfed down version of the image to a ppm file
        ImageWriter.getINSTANCE().writePPM(filename_h, ci.halfed());

        System.out.println("Done! Output :\n" + filename_h);
    }

}
