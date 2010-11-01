package metachess.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import metachess.library.PieceImages;
import metachess.library.Resource;

/** Singleton of the Piece Image Loader
 * @author Agbeladem (7DD)
 * @version 0.8.4
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
	if(b)
	    instance.loadResource("pieces_images.mci");
    }

    /** Load the images if they haven't already been loaded.
     * This should be called everywhere the piece images may be needed
     */
    public static void load() {
	load(!instance.loaded);
    }

    @Override
    public void loadResource(String file) {
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
		    PieceImages.put(piece+"_WHITE", line.substring(first+1, last));
		    PieceImages.put(piece+"_BLACK", line.substring(last+1, length ));
		}
		line = br.readLine();
	    }
	    br.close();
	    loaded = true;
	} catch(IOException e) {
	    e.printStackTrace();
	}

    }

}
