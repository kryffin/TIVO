package colorObjects;

import grayscaleObjects.GrayscalePixel;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class ColorPixel {

    private int r; //red component
    private int g; //green component
    private int b; //blue component

    public ColorPixel (int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Returns a pixel with only one component, specified, the others being 0
    public ColorPixel componentOnly (char comp) {
        switch (comp) {
            case 'r':
                return new ColorPixel(r, 0, 0);

            case 'g':
                return new ColorPixel(0, g, 0);

            case 'b':
                return new ColorPixel(0, 0, b);

            default:
                return new ColorPixel(0, 0, 0);
        }
    }

    // Return a gray value of the pixel
    public GrayscalePixel toGrayscale () {
        //grayscale = 0.299 * red + 0.587 * green + 0.114 * blue
        return new GrayscalePixel((int)((0.299 * r) + (0.587 * g) + (0.114 * b)));
    }

    // Returns a negative version of the pixel
    public ColorPixel toNegative (int M) {
        //negative = M - component
        return new ColorPixel(M - r, M - g, M - b);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return r + "\n" + g + "\n" + b + "\n";
    }

}
