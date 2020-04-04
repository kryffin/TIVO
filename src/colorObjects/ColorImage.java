package colorObjects;

import grayscaleObjects.GrayscaleImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class ColorImage {

    private int width; //width in pixels
    private int height; //height in pixels
    private int maxValue; //max value of pixels
    private List<ColorPixel> pixels; //list of pixels

    // Colored image defined by its width and height as well as a list of pixels
    public ColorImage() {
        this.pixels = new ArrayList<ColorPixel>();
    }

    public ColorImage(int width, int height, int maxValue) {
        this();
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
    }

    // Add a pixel to the image
    public void addPixel (ColorPixel pixel) {
        pixels.add(pixel);
    }

    // Returns the same image but only with the specified component 'r', 'g' or 'b'
    private ColorImage componentToImage (char comp) {
        if (comp != 'r' && comp != 'g' && comp != 'b') return null;

        ColorImage image = new ColorImage(width, height, maxValue);

        for (int i = 0; i < width * height; i++) {
            ColorPixel px = pixels.get(i);
            image.addPixel(px.componentOnly(comp));
        }

        return image;
    }

    // Returns a red component only version of the image
    public ColorImage toRedComponent () {
        return componentToImage('r');
    }

    // Returns a green component only version of the image
    public ColorImage toGreenComponent () {
        return componentToImage('g');
    }

    // Returns a blue component only version of the image
    public ColorImage toBlueComponent () {
        return componentToImage('b');
    }

    // Returns a ppm encoding of the image
    public String ppmEncoding () {
        StringBuilder sb = new StringBuilder();

        sb.append("P3\n" + width + " " + height + "\n" + maxValue + "\n" + this);

        return sb.toString();
    }

    public GrayscaleImage toGrayscale () {
        GrayscaleImage image = new GrayscaleImage(width, height, maxValue);

        for (ColorPixel px : pixels) {
            image.addPixel(px.toGrayscale());
        }

        return image;
    }

    // Returns a negative ppm encoding of the image
    public ColorImage toNegative () {
        ColorImage image = new ColorImage(width, height, maxValue);

        for (ColorPixel px : pixels) {
            image.addPixel(px.toNegative(maxValue));
        }

        return image;
    }

    // Returns an halfed down version of the image
    public ColorImage halfed () {
        //taking the ceiling of the division for uneven sized images (result of taking the floor : /glitches/halfed_uneven_floor.ppm)
        int     newWidth = (int) Math.ceil(width/2.),
                newHeight = (int) Math.ceil(height/2.);
        ColorImage image = new ColorImage(newWidth, newHeight, maxValue);

        for (int h = 0; h < height; h+=2) {
            for (int w = 0; w < width; w+=2) {
                //top left pixel
                ColorPixel px1 = this.pixels.get(w + (h * width));

                //top right pixel
                ColorPixel px2;
                if (w == width-1) {
                    //if w = width-1 then the width is uneven (because of the +2 step from 0) so we mean px1 with itself
                    px2 = px1;
                } else {
                    //else there is a top right pixel
                    px2 = this.pixels.get((w + 1) + (h * width));
                }

                //bottom left pixel
                ColorPixel px3;
                if (h == height-1) {
                    //if w = height-1 then the height is uneven (because of the +2 step from 0) so we mean px1 with itself
                    px3 = px1;
                } else {
                    //else there is a bottom left pixel
                    px3 = this.pixels.get(w + ((h + 1) * width));
                }

                //bottom right pixel
                ColorPixel px4;
                if (w == width-1 || h == height-1) {
                    //if either is uneven then again we mean px1 with itself
                    px4 = px1;
                } else {
                    //else there is a bottom right pixel
                    px4 = this.pixels.get((w + 1) + ((h + 1) * width));
                }

                //mean of the pixels
                int r = (px1.getR() + px2.getR() + px3.getR() + px4.getR()) / 4,
                    g = (px1.getG() + px2.getG() + px3.getG() + px4.getG()) / 4,
                    b = (px1.getB() + px2.getB() + px3.getB() + px4.getB()) / 4;

                image.addPixel(new ColorPixel(r, g, b));
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
