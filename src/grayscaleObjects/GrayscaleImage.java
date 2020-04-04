package grayscaleObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class GrayscaleImage {

    private int width; //width in pixels
    private int height; //height in pixels
    private int maxValue; //max value of pixels
    private List<GrayscalePixel> pixels; //list of pixels

    // Grayscale image defined by its width and height as well as a list of pixels
    public GrayscaleImage() {
        this.pixels = new ArrayList<GrayscalePixel>();
    }

    public GrayscaleImage(int width, int height, int maxValue) {
        this();
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
    }

    // Add a pixel to the image
    public void addPixel (GrayscalePixel pixel) {
        pixels.add(pixel);
    }

    // Returns a pgm encoding of the image, thus a grayscale version
    public String pgmEncoding () {
        StringBuilder sb = new StringBuilder();

        sb.append("P2\n" + width + " " + height + "\n" + maxValue + "\n" + this);

        return sb.toString();
    }

    // Returns an halfed down version of the image
    public GrayscaleImage halfed () {
        //taking the ceiling of the division for uneven sized images (result of taking the floor : /glitches/halfed_uneven_floor.ppm)
        int     newWidth = (int) Math.ceil(width/2.),
                newHeight = (int) Math.ceil(height/2.);
        GrayscaleImage image = new GrayscaleImage(newWidth, newHeight, maxValue);

        for (int h = 0; h < height; h+=2) {
            for (int w = 0; w < width; w+=2) {
                //top left pixel
                GrayscalePixel px1 = this.pixels.get(w + (h * width));

                //top right pixel
                GrayscalePixel px2;
                if (w == width-1) {
                    //if w = width-1 then the width is uneven (because of the +2 step from 0) so we mean px1 with itself
                    px2 = px1;
                } else {
                    //else there is a top right pixel
                    px2 = this.pixels.get((w + 1) + (h * width));
                }

                //bottom left pixel
                GrayscalePixel px3;
                if (h == height-1) {
                    //if w = height-1 then the height is uneven (because of the +2 step from 0) so we mean px1 with itself
                    px3 = px1;
                } else {
                    //else there is a bottom left pixel
                    px3 = this.pixels.get(w + ((h + 1) * width));
                }

                //bottom right pixel
                GrayscalePixel px4;
                if (w == width-1 || h == height-1) {
                    //if either is uneven then again we mean px1 with itself
                    px4 = px1;
                } else {
                    //else there is a bottom right pixel
                    px4 = this.pixels.get((w + 1) + ((h + 1) * width));
                }

                int gs = (px1.getGs() + px2.getGs() + px3.getGs() + px4.getGs()) / 4;

                image.addPixel(new GrayscalePixel(gs));
            }
        }

        return image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < width * height; i++) {
            sb.append(pixels.get(i)); //get the corresponding pixel from its coordinates and print it
        }

        return sb.toString();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

}
