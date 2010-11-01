package metachess.library;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

/** Singleton of the Piece Images library
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PieceImages {

    private static PieceImages instance = new PieceImages();
    private HashMap<String, String> images;

    private PieceImages() {
	images = new HashMap<String, String>();
    }

    /** Put an piece's image in this library
     * @param key the name of the piece to add
     * @param value the name of the image that will represent the added piece
     */
    public static void put(String key, String value) {
	instance.images.put(key, value);
    }

    /** Get an image for a specified piece
     * @param pieceName the piece which matching image will be returned
     * @param isWhite whether the piece and thus the returned image is white
     * @return the wanted image for the piece
     */
    public static String getImage(String pieceName, boolean isWhite) {
	return Resource.PIECE_IMAGES.getPath() + instance.images.get(pieceName+'_'+(isWhite ? "WHITE": "BLACK"));
    }

    /** Get an image for a specified white piece
     * @param pieceName the white piece which matching image will be returned
     * @return the wanted image for the piece
     */
    public static String getImage(String pieceName) {
	return getImage(pieceName, true);
    }


    /** Get a scaled image for a specified piece
     * @param pieceName the piece which matching image will be returned
     * @param isWhite whether the piece and thus the returned image is white
     * @param dim the dimension (side of the square in px) of the desired image
     * @return the wanted scaled image for the piece
     */
    public static ImageIcon getScaledImage(String pieceName, boolean isWhite, int dim) {
	return getScaledImageFromPath(getImage(pieceName, isWhite), dim);
    }

    /** Get a scaled version of a given image
     * @param im the image that will be scaled
     * @param dim the dimension (side of the square in px) of the desired image
     * @return the scaled version of the image for the piece
     */
    public static ImageIcon getScaledImage(ImageIcon im, int dim) {
	return new ImageIcon(im.getImage().getScaledInstance(dim, dim, Image.SCALE_SMOOTH));
    }

    /** Get a scaled version of an image given by its path
     * @param path the absolute path of the image that will be scaled
     * @param dim the dimension (side of the square in px) of the desired image
     * @return the scaled image for the piece
     */
    public static ImageIcon getScaledImageFromPath(String path, int dim) {
	return getScaledImage(new ImageIcon(path), dim);
    }
}

