package grayscaleObjects;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class GrayscalePixel {

    private int gs; //grayscale value

    public GrayscalePixel (int gs) {
        this.gs = gs;
    }

    // Returns a negative version of the pixel
    public GrayscalePixel toNegative (int M) {
        //negative = M - component
        return new GrayscalePixel(M - gs);
    }

    public int getGs() {
        return gs;
    }

    @Override
    public String toString() {
        return gs + "\n";
    }

}
