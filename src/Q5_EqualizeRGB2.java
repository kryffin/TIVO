import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class Q5_EqualizeRGB2 implements PlugInFilter {

    private int width, height, maxValue;
    private int[][] hist; //histograms

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        width = imagePlus.getWidth();
        height = imagePlus.getHeight();
        maxValue = 255;
        return DOES_RGB; //handles RGB images
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        //get the color processor of the current processor
        ColorProcessor cp = (ColorProcessor)imageProcessor;

        // histograms

        hist = new int[3][maxValue]; //array of 3 histograms, one for each component

        //red histogram
        cp.setRGBWeights(1, 0, 0); //keep only the red component
        hist[0] = cp.getHistogram(); //histogram affected by the weights

        //green histogram
        cp.setRGBWeights(0, 1, 0); //keep only the green component
        hist[1] = cp.getHistogram(); //histogram affected by the weights

        //blue histogram
        cp.setRGBWeights(0, 0, 1); //keep only the blue component
        hist[2] = cp.getHistogram(); //histogram affected by the weights

        // LookUpTables

        //red LUT
        int[] lut_r = new int[maxValue+1]; //LookUpTable
        for (int ng = 0; ng < maxValue; ng++) {
            lut_r[ng] = (int)(maxValue * R(ng, 0));
        }

        //green LUT
        int[] lut_g = new int[maxValue+1]; //LookUpTable
        for (int ng = 0; ng < maxValue; ng++) {
            lut_g[ng] = (int)(maxValue * R(ng, 1));
        }

        //blue LUT
        int[] lut_b = new int[maxValue+1]; //LookUpTable
        for (int ng = 0; ng < maxValue; ng++) {
            lut_b[ng] = (int)(maxValue * R(ng, 2));
        }

        //for each pixel
        for (int p = 0; p < width * height; p++) {
            int px = imageProcessor.get(p); //current pixel

            int r = (px & 0xFF0000) >> 16; //get corresponding red bits and offset them
            int g = (px & 0x00FF00) >> 8; //get corresponding green bits and offset them
            int b = (px & 0x0000FF); //get corresponding blue bits and offset them

            //applying the equalization
            r = lut_r[r];
            g = lut_g[g];
            b = lut_b[b];

            //build back the color pixel by offsetting the bits to their original places
            px = (r << 16) | (g << 8) | b;

            //placing the pixel on the image
            imageProcessor.set(p, px);
        }

    }

    //returns the proportion of pixels with intensity n for a specific component as seen in the slide :
    //  "Egalisation d'histogramme (1)" -> "Histogramme normalisé Pi"
    private double P (int n, int c) {
        return (double)hist[c][n] / (double)(width * height);
    }

    //returns the proportion of pixels with intensity n or lower for a specific component as seen in the slide :
    //  "Egalisation d'histogramme (2)" -> "Histogramme cummulé normalisé Ri"
    private double R (int n, int c) {
        double r = 0.;
        for (int k = 0; k < n; k++) {
            r += P(k, c);
        }
        return r;
    }

}
