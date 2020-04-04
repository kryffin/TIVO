import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Flip_Horizontal implements PlugInFilter {

    private int width;
    private int height;

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        width = imagePlus.getWidth();
        height = imagePlus.getHeight();

        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {

        for (int x = 0; x < width/2; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = imageProcessor.getPixel(x, y);
                imageProcessor.putPixel(x, y, imageProcessor.getPixel(width - x, y));
                imageProcessor.putPixel(width - x, y, pixel);
            }
        }

    }
}
