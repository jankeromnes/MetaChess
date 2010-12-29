package metachess.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import metachess.dialog.ErrorBox;
import metachess.exception.FileAccessException;
import metachess.exception.FileContentException;
import metachess.exception.LoadException;
import metachess.library.PieceLetters;
import metachess.library.Resource;

/** Singleton of the Piece Letter Loader
 * @author Agbeladem (7DD)
 * @version 0.9.1
 */
public class PieceLetterLoader implements Loader {

    private static PieceLetterLoader instance = new PieceLetterLoader();
    private boolean loaded;

    private PieceLetterLoader() {
	loaded = false;
    }

    /** Load the letters or do nothing if it's already been done */
    public static void load() {
	try {
	    if(!instance.loaded)
		instance.loadResource("pieces_letters.mcl");
	} catch(LoadException e) {
	    new ErrorBox(e);
	}
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
		if(length != 0 && line.charAt(0) != '#') {
		    int column = line.indexOf(":");
		    int first = line.indexOf("\"");
		    int last = line.lastIndexOf("\"");
		    if(column != -1 && column < first) {
			if(first == -1 || last == first)
			    throw new FileContentException("No letter given", name);
			String piece = line.substring(0, column);
			String letter = line.substring(first+1, last);
			PieceLetters.put(piece, letter);
		    }
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

