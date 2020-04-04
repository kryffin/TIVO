import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Rotate_90 implements PlugInFilter {

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

        ImageProcessor imageProcessor1 = new ByteProcessor(height, width);
        ImagePlus imagePlus = new ImagePlus("Flip horizontal",imageProcessor1);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                imageProcessor1.putPixel(height-y, x, imageProcessor.getPixel(x, y));
            }
        }

        imagePlus.show();
        imagePlus.updateAndDraw();

    }
}
