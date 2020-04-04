import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class Q2_Stretch implements PlugInFilter {

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
        int max = (int)imageProcessor.getStats().max; //maximum intensity value
        int min = (int)imageProcessor.getStats().min; //minimum intensity value

        int[] lut = new int[maxValue+1]; //LookUpTable

        //for each intensity value, fill the LookUpTable as seen in the slide :
        //  "Opération sur les images : Etirement de contraste - implantation"
        for (int ng = 0; ng < maxValue; ng++) {
            lut[ng] = (maxValue * (ng - min)) / (max - min);
        }

        // without the use of applyTable

        /*
        //for each pixel, apply the value stored in the LookUpTable instead
        for (int p = 0; p < width * height; p++) {
            imageProcessor.set(p, lut[imageProcessor.get(p)]);
        }
        */

        // with the use of applyTable
        imageProcessor.applyTable(lut);
    }
}
