import ij.*;
import ij.io.OpenDialog;
import ij.process.*;
import ij.plugin.frame.*;

/**
 * @author I. Debled-Rennesson & Nicolas "Kryffin" Kleinhentz
 */
public class Q7_Diff_Im extends PlugInFrame {

    public Q7_Diff_Im() {
        super ( "Différences entre images");
    }

    public void run ( String arg) {
        IJ.showMessage("Veuillez choisir l'image A puis l'image B pour l'affichage des différences");

        //loading image A
        OpenDialog od = new OpenDialog("Image A");
        ImagePlus imgDiffA = new ImagePlus(od.getPath());
        ImageProcessor ipDiffA = imgDiffA.getProcessor();

        //loading image B
        od = new OpenDialog("Image B");
        ImagePlus imgDiffB = new ImagePlus(od.getPath());
        ImageProcessor ipDiffB = imgDiffB.getProcessor();

        //width and height of image B
        int w = ipDiffB.getWidth();
        int h = ipDiffB.getHeight();

        //result image
        ImageProcessor ipRes = new ByteProcessor(w,h);
        ImagePlus imgRes = new ImagePlus("Différences", ipRes);

        //for each pixels
        for (int p = 0; p < w * h; p++) {
            //absolute of substraction of the pixels
            int px = Math.abs(ipDiffA.get(p) - ipDiffB.get(p));

            //setting the new pixel to the result image
            ipRes.set(p, px);
        }

        //display of the result
        imgRes.show();
        imgRes.updateAndDraw();
    }
}
		
		