package metachess.library;

import java.util.HashMap;

import metachess.game.Piece;

/** Singleton of the Piece Letters library
 * @author Agbeladem (7DD)
 * @version 0.9.1
 */
public class PieceLetters {

    private static PieceLetters instance = new PieceLetters();
    private HashMap<String, String> letters;

    private PieceLetters() {
	letters = new HashMap<String, String>();
    }

    /** Put a piece's letter in this library
     * @param key the name of the piece
     * @param value the name of the letter that will represent the added piece
     */
    public static void put(String key, String value) {
	instance.letters.put(key, value);
    }

    /** Get the letter of a given piece
     * @param piece the name of the piece
     * @return the letter (or group of letters) that represents the piece
     */
    public static String get(String piece) {
	assert Pieces.hasPiece(piece): "The piece must exist in the list";
	return instance.letters.containsKey(piece)
	    ? instance.letters.get(piece)
	    : String.valueOf(Pieces.getPiece(piece).getLetter());
    }

    /** Get the letter of a given piece
     * @param piece the piece
     * @return the letter (or group of letters) that represents the piece
     */
    public static String get(Piece piece) {
	return get(piece.getName());
    }

}
