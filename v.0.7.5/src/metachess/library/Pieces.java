package metachess.library;

import java.util.HashMap;

import metachess.game.Piece;

public class Pieces {

    private static Pieces instance = new Pieces();
    private HashMap<String, Piece> map;

    private Pieces() {
	map = new HashMap<String, Piece>();
    }

    public static boolean hasPiece(String pieceName) {
	return instance.map.containsKey(pieceName);
    }

    public static void addPiece(Piece p) {
	instance.map.put(p.getName(), p);
    }

    public static Piece getPiece(String pieceName, boolean isWhite) {
	if(!hasPiece(pieceName))
	    Loader.loadPiece(pieceName);
	Piece piece = new Piece(instance.map.get(pieceName));
	piece.setWhite(isWhite);
	piece.setImage(PiecesImages.getImage(pieceName, isWhite));
	return piece;

    }

}