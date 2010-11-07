package metachess.library;

import java.util.HashMap;

import metachess.dialog.ErrorDialog;
import metachess.exceptions.LoadException;
import metachess.game.Piece;
import metachess.loader.PieceLoader;

/** Singleton of the Pieces library
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class Pieces {

    private static Pieces instance = new Pieces();
    private HashMap<String, Piece> map;

    private Pieces() {
	map = new HashMap<String, Piece>();
    }

    /** Tell whether a Piece has been loaded in this library
     * @param pieceName the piece which presence will be checked
     * @return true if it has
     */
    public static boolean hasPiece(String pieceName) {
	return instance.map.containsKey(pieceName);
    }

    /** Add a Piece to this library
     * @param p the piece to add
     */
    public static void addPiece(Piece p) {
	instance.map.put(p.getName(), p);
    }

    /** Get a white Piece from this library
     * @param pieceName the name of the piece that will be sought
     * @return the piece
     */
    public static Piece getPiece(String pieceName) {
	return getPiece(pieceName, true);
    }

    /** Get a Piece from this library of the given name and color
     * @param pieceName the name of the piece that will be sought
     * @param isWhite whether the wanted piece is white
     */
    public static Piece getPiece(String pieceName, boolean isWhite) {

	try {
	    if(!hasPiece(pieceName))
		PieceLoader.load(pieceName);
	} catch(LoadException e) {
	    new ErrorDialog(e);
	}

	Piece piece = new Piece(instance.map.get(pieceName));
	piece.setWhite(isWhite);
	piece.setImage(PieceImages.getImage(pieceName, isWhite));
	return piece;

    }

}

