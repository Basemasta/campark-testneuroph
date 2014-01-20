package testneuroph.model.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
public class ImagesIterator implements Iterator<File> {

    private Iterator<File> imageIterator;
    private String currentFilename = null;

    /**
     * Creates image Iterator for the specified dir
     *
     * @param dir
     * @throws java.io.IOException
     */
    public ImagesIterator(File dir) throws IOException {
	if (!dir.isDirectory()) {
	    throw new IOException(dir + " is not a directory!");
	}

	String[] imageFilenames = dir.list(new FilenameFilter() {
	    @Override
	    public boolean accept(File dir, String name) {
		if (name.length() > 4) {
		    String fileExtension = name.substring(name.length() - 4, name.length());
		    return ".jpg".equalsIgnoreCase(fileExtension)
			    || ".png".equalsIgnoreCase(fileExtension);
		}
		return false;
	    }
	});

	List<File> imageFiles = new ArrayList<>();
	for (String imageFile : imageFilenames) {
	    imageFiles.add(new File(dir, imageFile));
	}

	imageIterator = imageFiles.iterator();
    }

    @Override
    public boolean hasNext() {
	return imageIterator.hasNext();
    }

    /**
     * Loads and returns next image
     *
     * @return Retruns next image file from directory as File object
     */
    @Override
    public File next() {
	File nextFile = imageIterator.next();
	currentFilename = nextFile.getName();
	return nextFile;
    }

    @Override
    public void remove() {
	imageIterator.remove();
    }

    public String getFilenameOfCurrentImage() {
	return currentFilename;
    }
}
