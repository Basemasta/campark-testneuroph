package testneuroph.model.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
public class ResizeImage {

    private final static int INT_CENT = 100;

    public final static int DEFAULT_MAX_WIDTH = 1000;

    public final static int DEFAULT_MAX_HEIGHT = 1000;

    public static byte[] resizeImagePourcentage(byte[] originalImage, int pourcentage) {
	return ImageUtils.bufferedImageToByteArray(resizeImagePourcentage(ImageUtils.byteArrayToBufferedImage(originalImage), pourcentage));
    }
    
    /**
     * Resize byte[] image with a default max width (1000) and a default max
     * height (1000)
     *
     * @param originalImage the original image
     * @return the new resized image
     */
    public static byte[] resizeImageWithMaxWidthMaxHeight(byte[] originalImage) {
	return ImageUtils.bufferedImageToByteArray(resizeImageWithMaxWidthMaxHeight(ImageUtils.byteArrayToBufferedImage(originalImage)));
    }

    /**
     * Resize byte[] image with a max width and max height
     *
     * @param originalImage the original image
     * @param maxWidth the max width
     * @param maxHeight the max height
     * @return the new resized image
     */
    public static byte[] resizeImageWithMaxWidthMaxHeight(byte[] originalImage, int maxWidth, int maxHeight) {
	return ImageUtils.bufferedImageToByteArray(resizeImageWithMaxWidthMaxHeight(ImageUtils.byteArrayToBufferedImage(originalImage), maxWidth, maxHeight));
    }

    /**
     * Resize BufferedImage with a pourcentage
     *
     * @param originalImage the original image
     * @param pourcentage the pourcentage
     * @return the new resized image
     */
    public static BufferedImage resizeImagePourcentage(BufferedImage originalImage, int pourcentage) {
	BufferedImage resizedImage = originalImage;
	if (originalImage != null) {
	    Dimension imgSize = new Dimension(originalImage.getWidth(), originalImage.getHeight());
	    Dimension newImgSize = new Dimension((imgSize.width * pourcentage) / INT_CENT, (imgSize.height * pourcentage) / INT_CENT);
	    if (imgSize.width != newImgSize.width || imgSize.height != newImgSize.height) {
		resizedImage = resizeImage(originalImage, newImgSize);
	    }
	}
	return resizedImage;
    }

    /**
     * Resize BufferedImage with a default max width (1000) and a default max
     * height (1000)
     *
     * @param originalImage the original image
     * @return the new resized image
     */
    public static BufferedImage resizeImageWithMaxWidthMaxHeight(BufferedImage originalImage) {
	return resizeImageWithMaxWidthMaxHeight(originalImage, DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
    }

    /**
     * Resize BufferedImage with a max width and max height
     *
     * @param originalImage the original image
     * @param maxWidth the max width
     * @param maxHeight the max height
     * @return the new resized image
     */
    public static BufferedImage resizeImageWithMaxWidthMaxHeight(BufferedImage originalImage, int maxWidth, int maxHeight) {
	BufferedImage resizedImage = originalImage;
	if (originalImage != null && maxWidth > 0 && maxHeight > 0) {
	    Dimension imgSize = new Dimension(originalImage.getWidth(), originalImage.getHeight());
	    Dimension boundary = new Dimension(maxWidth, maxHeight);
	    Dimension newImgSize = getScaledDimension(imgSize, boundary);
	    if (imgSize.width != newImgSize.width || imgSize.height != newImgSize.height) {
		resizedImage = resizeImage(originalImage, newImgSize);
	    }
	}
	return resizedImage;
    }

    /**
     * Resize image with new dimension
     *
     * @param originalImage the original image
     * @param newImgSize the new dimension
     * @return the new resized image
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, Dimension newImgSize) {
	int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	BufferedImage resizedImage = new BufferedImage(newImgSize.width, newImgSize.height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, newImgSize.width, newImgSize.height, null);
	g.dispose();
	return resizedImage;
    }

    /**
     * Get new scaled dimension of an image with boundary
     *
     * @param imgSize original image dimension
     * @param boundary boundary dimension
     * @return the new dimension
     */
    private static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
	int original_width = imgSize.width;
	int original_height = imgSize.height;
	int bound_width = boundary.width;
	int bound_height = boundary.height;
	int new_width = original_width;
	int new_height = original_height;
	// first check if we need to scale width
	if (original_width > bound_width) {
	    //scale width to fit
	    new_width = bound_width;
	    //scale height to maintain aspect ratio
	    new_height = (new_width * original_height) / original_width;
	}
	// then check if we need to scale even with the new height
	if (new_height > bound_height) {
	    //scale height to fit instead
	    new_height = bound_height;
	    //scale width to maintain aspect ratio
	    new_width = (new_height * original_width) / original_height;
	}
	return new Dimension(new_width, new_height);
    }
}
