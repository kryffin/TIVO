import colorObjects.ColorImage;
import colorObjects.ColorPixel;

import java.io.*;

/**
 * @author Nicolas "Kryffin" Kleinhentz
 */
public class ImageParser {

    private ImageParser() {
    }

    public ColorImage parse (String filename) {
        if (filename == null) return null;

        ColorImage image = new ColorImage();

        File file = new File(filename);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            int l = 0, comp = 0, r = 0, g = 0, b = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    //comment, no need of that
                    continue;
                } else if (line.startsWith("P")) {
                    //first line
                    l++;
                } else if (l == 1) {
                    //line containing the width and height
                    String[] split = line.split("\\s+");
                    image.setWidth(Integer.parseInt(split[0]));
                    image.setHeight(Integer.parseInt(split[1]));
                    l++;
                } else if (l == 2) {
                    //line containing the maximum pixel value
                    image.setMaxValue(Integer.parseInt(line));
                    l++;
                } else {
                    //line containing the component of a pixel
                    switch (comp) {
                        case 0:
                            r = Integer.parseInt(line);
                            comp++;
                            break;

                        case 1:
                            g = Integer.parseInt(line);
                            comp++;
                            break;

                        case 2:
                            b = Integer.parseInt(line);
                            image.addPixel(new ColorPixel(r, g, b));
                            comp = 0;
                            break;

                        default:
                            break;
                    }
                }
            }

            br.close();
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; //something went wrong
    }

    public static ImageParser getINSTANCE() {
        return PPMParserHolder.INSTANCE;
    }

    private static class PPMParserHolder {
        private final static ImageParser INSTANCE = new ImageParser();
    }

}
