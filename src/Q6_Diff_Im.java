import ij.*;
import ij.io.OpenDialog;
import ij.process.*;
import ij.plugin.frame.*;

/**
 * @author I. Debled-Rennesson & Nicolas "Kryffin" Kleinhentz
 */
public class Q6_Diff_Im extends PlugInFrame {

	public Q6_Diff_Im() {
		super ( "Soustraction entre images");
	}

	public void run ( String arg) {
		IJ.showMessage("Veuillez choisir l'image A puis l'image B pour la soustraction");

		//loading image A
	    OpenDialog od = new OpenDialog("Choisissez l'image 1");
		ImagePlus imgDiffA = new ImagePlus(od.getPath());
		ImageProcessor ipDiffA = imgDiffA.getProcessor();

		//loading image B
        od = new OpenDialog("Choisissez l'image 2");
        ImagePlus imgDiffB = new ImagePlus(od.getPath());
		ImageProcessor ipDiffB = imgDiffB.getProcessor();

		//width and height of image B
		int w = ipDiffB.getWidth();
		int h = ipDiffB.getHeight();

		//result image
		ImageProcessor ipRes = new ByteProcessor(w,h);
		ImagePlus imgRes = new ImagePlus("Soustraction", ipRes);

		//for each pixels
		for (int p = 0; p < w * h; p++) {
			//substract as seen in slide "Soustraction d'Images"
			int px = Math.max(ipDiffA.get(p) - ipDiffB.get(p), 0);

			//setting the new pixel to the result image
			ipRes.set(p, px);
		}

		//display of the result
		imgRes.show();
		imgRes.updateAndDraw();
	}	
}
		
		