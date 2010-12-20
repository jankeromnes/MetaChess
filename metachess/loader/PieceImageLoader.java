package metachess.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import metachess.dialog.ErrorBox;
import metachess.exception.FileAccessException;
import metachess.exception.LoadException;
import metachess.library.PieceImages;
import metachess.library.Resource;

/** Singleton of the Piece Image Loader
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public class PieceImageLoader implements Loader {

    private static PieceImageLoader instance = new PieceImageLoader();
    private boolean loaded;

    private PieceImageLoader() {
	loaded = false;
    }

    /** Load the images or do nothing
     * @param b whether the images shall be loaded
     */
    public static void load(boolean b) {
	try {
	    if(b)
		instance.loadResource("pieces_images.mci");
	} catch(LoadException e) {
	    new ErrorBox(e);
	}
    }

    /** Load the images if they haven't already been loaded.
     * This should be called everywhere the piece images may be needed
     */
    public static void load() {
	load(!instance.loaded);
    }

    @Override
    public void loadResource(String file) throws LoadException {
	String name = file;
	try {
	    file = Resource.RESOURCES.getPath()+file;
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String line = br.readLine();
	    while(line != null) {
		int length = line.length();
		if(length!=0 && line.charAt(0) != '#') {
		    int first = line.indexOf(":");
		    int last = line.lastIndexOf(":");
		    String piece = line.substring(0,first);
		    String whiteImage = line.substring(first+1, last);
		    String blackImage = line.substring(last+1, length);
		    String path = Resource.PIECE_IMAGES.getPath();
		    if(! new File(path+whiteImage).exists())  whiteImage = "Wmetamorph.png";
		    if(! new File(path+blackImage).exists())  blackImage = "Bmetamorph.png";
		    PieceImages.put(piece+"_WHITE", whiteImage);
		    PieceImages.put(piece+"_BLACK", blackImage);
		}
		line = br.readLine();
	    }
	    br.close();
	    loaded = true;
	} catch(IOException e) {
	    throw new FileAccessException(name);
	}

    }

}
