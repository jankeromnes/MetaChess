package metachess.library;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class PiecesImages {

    private static PiecesImages instance = new PiecesImages();
    private HashMap<String, String> images;
    private boolean loaded = false;

    private PiecesImages() {
	images = new HashMap<String, String>();
    }

    public static void load(boolean b) {
	if(b)
	    instance.loadImages();
    }

    public static void load() {
	load(!instance.loaded);
    }

    public static String getPath() {
	return Resource.RESOURCES.getPath(false) + "pieces_images.mci";
    }

    private void loadImages() {
	try {

	    BufferedReader br = new BufferedReader(new InputStreamReader(instance.getClass().getResourceAsStream(getPath() )));
	    String line = br.readLine();

	    while(line != null) {

		int length = line.length();
		if(length!=0 && line.charAt(0) != '#') {
		    int first = line.indexOf(":");
		    int last = line.lastIndexOf(":");
		    String piece = line.substring(0,first);
		    instance.images.put(piece+"_WHITE", line.substring(first+1, last));
		    instance.images.put(piece+"_BLACK", line.substring(last+1, length ));
		}
		line = br.readLine();
	    }
	    br.close();
	    loaded = true;
	} catch(IOException e) { System.out.println(e); }

    }

    public static String getImage(String pieceName, boolean isWhite, boolean relative) {
	return Resource.PIECES_IMAGES.getPath(relative) + instance.images.get(pieceName+'_'+(isWhite ? "WHITE": "BLACK"));
    }

    public static String getImage(String pieceName, boolean isWhite) {
	return getImage(pieceName, isWhite, false);
    }

    public static String getImage(String pieceName) {
	return getImage(pieceName, true);
    }


    public static ImageIcon getScaledImage(String pieceName, boolean isWhite, int dim) {
	return getScaledImageFromPath(getImage(pieceName, isWhite), dim);
    }

    public static ImageIcon getScaledImageFromPath(String path, int dim) {
	return getScaledImage(new ImageIcon(instance.getClass().getResource(path)), dim);
    }

    public static ImageIcon getScaledImage(ImageIcon im, int dim) {
	return new ImageIcon(im.getImage().getScaledInstance(dim, dim, Image.SCALE_SMOOTH));
    }

}

