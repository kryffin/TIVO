import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class Q3_Equalize implements PlugInFilter {

    private int width, height, maxValue;
    private int[] hist; //histogram

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        width = imagePlus.getWidth();
        height = imagePlus.getHeight();
        maxValue = 255;
        return DOES_8G; //handles 8bits grayscale images
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        hist = imageProcessor.getHistogram(); //histogram
        int[] lut = new int[maxValue+1]; //LookUpTable

        //for each intensity value, fill the LookUpTable for histogram equalization
        for (int ng = 0; ng < maxValue; ng++) {
            lut[ng] = (int)(maxValue * R(ng));
        }

        imageProcessor.applyTable(lut);
    }

    //returns the proportion of pixels with intensity n as seen in the slide :
    //  "Egalisation d'histogramme (1)" -> "Histogramme normalisé Pi"
    private double P (int n) {
        return hist[n] / (double)(width * height);
    }

    //returns the proportion of pixels with intensity n or lower as seen in the slide :
    //  "Egalisation d'histogramme (2)" -> "Histogramme cummulé normalisé Ri"
    private double R (int n) {
        double r = 0.; //result

        //sum(0->n) of Pi(n)
        for (int k = 0; k < n; k++) {
            r += P(k);
        }

        return r;
    }

}
