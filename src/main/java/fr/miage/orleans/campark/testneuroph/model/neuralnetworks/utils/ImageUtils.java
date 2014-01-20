package testneuroph.model.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
public class ImageUtils {

    /**
     * Return a byte[] image converted to a BufferedImage if possible (else
     * return null)
     *
     * @param image the byte[] image
     * @return the BufferedImage
     */
    public static BufferedImage byteArrayToBufferedImage(byte[] image) {
	BufferedImage bufferedImage = null;
	InputStream in = new ByteArrayInputStream(image);
	try {
	    bufferedImage = ImageIO.read(in);
	} catch (IOException ex) {
	    Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
	}
	return bufferedImage;
    }

    /**
     * Return a BufferedImage converted to a byte[] image if possible (else
     * return null)
     *
     * @param bufferedImage the BufferedImage
     * @return the byte[] image
     */
    public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage) {
	byte[] image = null;
	try {
	    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
		ImageIO.write(bufferedImage, "jpg", baos);
		baos.flush();
		image = baos.toByteArray();
	    }
	} catch (IOException ex) {
	    Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
	}
	return image;
    }
}
