package metachess.boards;

import metachess.game.Piece;

/** Class of a removed square
 * @author Agbeladem (7DD)
 * @version 0.8.3
 */
public class EmptySquare extends AbstractSquare {

    /** Create an empty square
     * @param x this square's column (X Coord)
     * @param y this square's row (Y Coord)
     */
    public EmptySquare(int i, int j) {
	super(i, j);
	piece = new Piece();
    }
    @Override
    public boolean isNull() { return true; }

    @Override
    public boolean hasPiece() { return true; }

    @Override
    public boolean isGreen() { return false; }

    @Override
    public Object clone() {
	return new EmptySquare(i, j);
    }


}