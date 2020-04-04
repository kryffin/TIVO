// Importation des paquets necessaires. Le plugin n'est pas lui-meme un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;						// pour classe GenericDialog et Newimage

import java.util.Arrays;

public class Filtre_Median implements PlugInFilter {

    private int w, h;
    private int[] pixels;
    private ImageProcessor ipI;

    public int setup(String arg, ImagePlus imp) {
        w = imp.getWidth ();
        h = imp.getHeight ();
        return DOES_8G;
    }

    public void run( ImageProcessor ip){
        ipI = ip;
        ImagePlus result = NewImage.createByteImage (" Filtrage ", w, h, 1,NewImage.FILL_BLACK );
        ImageProcessor ipr = result.getProcessor ();
        int N = 3; // taille du demi - masque
        int[] voisins;

        for (int j = 0; j < h-1; j++) {
            for (int i = 0; i < w-1; i++) {

                voisins = new int[N*N]; //tableau dépendant du nombre de voisins
                int mid = (int)(N / 2.);
                int tab = 0, tabEffectif = 9;

                //nombre de pixels effectifs (en cas de coin ou de bordure)
                if ((i == 0 && j == 0) || (i == w-1 && j == h-1)) {
                    tabEffectif = 4;
                } else if (i == 0 || j == 0 || i == w-1 || j == h-1) {
                    tabEffectif = 6;
                }

                //récupération des pixels avoisinants
                for (int ik = -mid; ik < N-mid; ik++) {
                    for (int jk = -mid; jk < N-mid; jk++) {
                        if (i+ik < 0 || i+ik > w-1 || j+jk < 0 || j+jk > h-1) {
                            voisins[tab] = -1;
                        } else {
                            voisins[tab] = I(i+ik, j+jk);
                        }
                        tab++;
                    }
                }

                // découpage du tableau si éncessaire et tri de ses valeurs
                int[] voisinsTries = new int[tabEffectif];
                for (int ite = 0; ite < tabEffectif; ite++) {
                    int valMin = Integer.MAX_VALUE, indMin = -1;
                    for (int ind = 0; ind < tab; ind++) {
                        if (valMin > voisins[ind] && voisins[ind] != -1) {
                            valMin = voisins[ind];
                            indMin = ind;
                        }
                    }
                    voisins[indMin] = -1;
                    voisinsTries[ite] = valMin;
                }
                ipr.set(i + (j * w), mediane(voisinsTries));
            }
        }
        result.show ();
        result.updateAndDraw ();
    }

    private int I(int i, int j) {
        return ipI.get(i + (j * w));
    }

    static int mediane (int a[]){
        int [] effectifs = new int [256]; // tableau des effectifs
        for (int value : a) {
            effectifs[value]++;
        }
        int somme = 0;
        for (int i=0 ; i <= 255 ; i++) {
            somme = somme + effectifs[i] ;
            if (2* somme >= a.length ) {
                return i;
            }
        }
        return -1;
    }

}
