import colorObjects.ColorImage;
import grayscaleObjects.GrayscaleImage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class ImageWriter {

    private ImageWriter() {
    }

    public void writePPM (String filename, ColorImage image) {
        if (filename == null) return;

        File file = new File(filename);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(image.ppmEncoding());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePGM (String filename, GrayscaleImage image) {
        if (filename == null) return;

        File file = new File(filename);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(image.pgmEncoding());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageWriter getINSTANCE() {
        return ImageWriter.PPMWriterHolder.INSTANCE;
    }

    private static class PPMWriterHolder {
        private final static ImageWriter INSTANCE = new ImageWriter();
    }

}
