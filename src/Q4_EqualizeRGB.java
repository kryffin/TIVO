import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class Q4_EqualizeRGB implements PlugInFilter {

    private int width, height, maxValue;
    private int[] hist; //histogram

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        width = imagePlus.getWidth();
        height = imagePlus.getHeight();
        maxValue = 255;
        return DOES_RGB; //handles RGB images
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        hist = imageProcessor.getHistogram(); //histogram of weighted grayscale conversion

        int[] lut = new int[maxValue+1]; //LookUpTable

        //for each intensity value, fill the LookUpTable for histogram equalization
        for (int ng = 0; ng < maxValue; ng++) {
            lut[ng] = (int)(maxValue * R(ng));
        }

        //for each pixel
        for (int p = 0; p < width * height; p++) {
            int px = imageProcessor.get(p); //current pixel

            int r = (px & 0xFF0000) >> 16; //get corresponding red bits and offset them
            int g = (px & 0x00FF00) >> 8; //get corresponding green bits and offset them
            int b = (px & 0x0000FF); //get corresponding blue bits and offset them

            //applying the equalization
            r = lut[r];
            g = lut[g];
            b = lut[b];

            //build back the color pixel by offsetting the bits to their original places
            px = (r << 16) | (g << 8) | b;

            //placing the pixel on the image
            imageProcessor.set(p, px);
        }

    }

    //returns the proportion of pixels with intensity n as seen in the slide :
    //  "Egalisation d'histogramme (1)" -> "Histogramme normalisé Pi"
    private double P (int n) {
        return hist[n] / (double)(width * height);
    }

    //returns the proportion of pixels with intensity n or lower as seen in the slide :
    //  "Egalisation d'histogramme (2)" -> "Histogramme cummulé normalisé Ri"
    private double R (int n) {
        double r = 0.;
        for (int k = 0; k < n; k++) {
            r += P(k);
        }
        return r;
    }

}
