package testneuroph.model.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
public class ImagesLoader {

    public static Map<String, BufferedImage> getDataFromDirPath(String dirPath) {
	Map<String, BufferedImage> res = new HashMap<>();
	File dir = new File(dirPath);
	if (dir.isDirectory()) {
	    try {
		ImagesIterator imagesIterator = new ImagesIterator(dir);
		while (imagesIterator.hasNext()) {
		    File imgFile = imagesIterator.next();
		    BufferedImage bufferedImage = ImageIO.read(imgFile);
		    String filenameOfCurrentImage = imagesIterator.getFilenameOfCurrentImage();
		    StringTokenizer st = new StringTokenizer(filenameOfCurrentImage, "._");
		    res.put(st.nextToken(), bufferedImage);
		}
	    } catch (IOException ex) {
		Logger.getLogger(ImagesLoader.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return res;
    }
}
