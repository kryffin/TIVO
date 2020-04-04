// Importation des paquets necessaires. Le plugin n'est pas lui-meme un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;						// pour classe GenericDialog et Newimage

public class Filtre_Moyenneur implements PlugInFilter {

	private int w, h;
	private int[] pixels;
	private ImageProcessor ipI;
	private int poids;

	public int setup(String arg, ImagePlus imp) {
		w = imp.getWidth ();
		h = imp.getHeight ();
		return DOES_8G;
	}

	public void run( ImageProcessor ip){
		ipI = ip;
		ImagePlus result = NewImage.createByteImage (" Filtrage ", w, h, 1,NewImage.FILL_BLACK );
		ImageProcessor ipr = result.getProcessor ();

		//modifier le masque à volonté, garder sous forme de matrice CARREE et de taille impaire
		int [][] masque = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};

		for (int j = 0; j < h-1; j++) {
			for (int i = 0; i < w-1; i++) {
				poids = 0;
				int filtre = 0;
				int mid = (int)(masque.length / 2.);

				//récupération des pixels avoisinants
				for (int ik = -mid; ik < masque.length-mid; ik++) {
					for (int jk = -mid; jk < masque[ik+mid].length-mid; jk++) {
						filtre += I(i+ik, j+jk);
					}
				}
				
				filtre /= poids;
				ipr.set(i + (j * w), filtre);
			}
		}
		result.show ();
		result.updateAndDraw ();
	}

  private int I(int i, int j) {
	if (i < 0 || i > w-1 || j < 0 || j > h-1) {
		return 0;
	}
	poids++;
    return ipI.get(i + (j * w));
  }

}
