// Importation des paquets nécessaires. Le plugin n'est pas lui-même un paquet (pas de mot-clé package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;						// pour GenericDialog
public class Change_Echelle implements PlugInFilter {
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}
	
	public void run (ImageProcessor ip){
		// dialogue permettant de fixer la valeur du facteur d’echelle
		GenericDialog gd = new GenericDialog ( " Facteur d’echelle ",IJ.getInstance());

		gd.addSlider(" facteur ", 0.0, 10.0, 2.0 );
		gd.showDialog();
		if (gd.wasCanceled()) {
			IJ.error(" PlugIn cancelled ");
			return;
		}
	
		double ratio = (double)gd.getNextNumber();
		int w = ip.getWidth();
		int h = ip.getHeight();
		byte[] pixels = (byte[])ip.getPixels();
		int w_new = (int)(w * ratio);
		int h_new = (int)(h * ratio);
		ImagePlus result = NewImage.createByteImage(" Retailler ", w_new, h_new, 1, NewImage.FILL_BLACK);
		ImageProcessor ipr = result.getProcessor();
		byte[] pixelsr = (byte[])ipr.getPixels();

		for (int y = 0; y < h_new; y++) {
			for (int x = 0; x < w_new; x++) {
				//int xp = (int)(x/ratio);
				//int yp = (int)(y/ratio);
				//pixelsr[x + (y * w_new)] = pixels[xp + (yp * w)];

				ipr.putPixel(x, y, (int)ip.getInterpolatedValue(x/ratio, y/ratio));
			}
		}

		result.show();
		result.updateAndDraw();
	}
}
