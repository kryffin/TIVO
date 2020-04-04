import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class Q1_Contrast implements PlugInFilter {

    private int width, height, maxValue;

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        width = imagePlus.getWidth();
        height = imagePlus.getHeight();
        maxValue = 255;
        return DOES_8G; //handles 8bits grayscale images
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        double contrast = 0;
        double mean = imageProcessor.getStats().mean; //mean of the pixels value

        //for each pixel (double sum as seen in the slide "Contraste d'une image")
        for (int p = 0; p < width * height; p++) {
            int i = imageProcessor.get(p); //intensity of the pixel
            i /= maxValue; //normalize intensity between [0, 1]
            contrast += (i - mean) * (i - mean); //sum of (intensity - mean)²
        }

        contrast = Math.sqrt((1.f/(width * height)) * contrast);

        //display of the contrast
        IJ.showMessage("Contraste : " + contrast);
    }
}
