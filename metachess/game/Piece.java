package metachess.game;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import metachess.board.PlayableBoard;
import metachess.library.PieceImages;
import metachess.model.PieceBehaviour;
import metachess.square.AbstractSquare;

/** Class of an Abstract Piece
 * @author Agbeladem and Jan (7DD)
 * @version 0.8.8
 */
public class Piece implements PieceBehaviour {

    enum BrowseType {
	CHECK_KING, GREEN_SQUARES, CHOICE_LIST, CHECK_RANGE;
    }

    private ArrayList<AbstractSquare> choices;
    private ArrayList<MoveType> moves;
    private HashMap<Integer, ImageIcon> icons;

    private String name;
    private ImageIcon image;
    private boolean white;
    private boolean moved;

    private boolean pawn;
    private boolean king;
    private boolean joker;
    private boolean rook;
    
    private float price;
    private boolean priceCalculated;

    // Temporary fields
    private Coords newCoords;
    /** Create a new empty piece */
    public Piece() {

	choices = new ArrayList<AbstractSquare>();
	moves = new ArrayList<MoveType>();

	pawn = false;
	joker = false;
	king = false;
	rook = false;
	
	priceCalculated = false;

    }

    /** Create a clone of another piece
     * @param p the other piece
     */
    public Piece(Piece p) {
	choices = new ArrayList<AbstractSquare>();
	moves = p.getMoveTypes();
	pawn = p.isPawn();
	joker = p.isJoker();
	king = p.isKing();
	rook = p.isRook();
	name = p.getName();
    }
    
    /** Calculate the price of the piece from its available moves and conditions
     * @return the price
     */
    public float getPrice() {
    	if(!priceCalculated) {
	    price = 0;
	    for(MoveType m : moves)
		price+=m.getPrice();
	    if(pawn) price += 20;
	    if(rook) price += 10;
	    price/=83.4;
	    if(joker) price += 5;
	    priceCalculated = true;
    	}
    	return price;
    }

    /** Add an available move type for this piece
     * @param m the move type
     */
    public void addMoveType(MoveType m) {

	char type = m.getType();
	char range = m.getRange();
	int step = m.getStep();
	int offset = m.getOffset();

	if(m.isComboDirection()) {
	    if(m.isSlash()) {
		moves.add(new MoveType(type, '0', range, step, offset));
		moves.add(new MoveType(type, '8', range, step, offset));
	    }
	    if(m.isBackSlash()) {
		moves.add(new MoveType(type, '2', range, step, offset));
		moves.add(new MoveType(type, '6', range, step, offset));
	    }
	    if(m.isVertically()) {
		moves.add(new MoveType(type, '1', range, step, offset));
		moves.add(new MoveType(type, '7', range, step, offset));
	    }
	    if(m.isHorizontally()) {
		moves.add(new MoveType(type, '3', range, step, offset));
		moves.add(new MoveType(type, '5', range, step, offset));
	    }
	} else 
	    moves.add(m);

	
    }

    /** Reset the available move types for this piece with a given list
     * @param m the new list of move types in this piece
     */
    public void setMoves(ArrayList<MoveType> m) {
	 moves.clear();
	 for(MoveType mt : m)
	     addMoveType(mt);
     }
    
    /** Get the list of all the move types
     * @return the list of move types for this piece
     */
     public ArrayList<MoveType> getMoveTypes() {
	 return moves;
     }


     // BOARD BROWSING COMMANDS

     private boolean setGreen(AbstractSquare c, PlayableBoard ab) {
	 c.setGreen(true);
	 return true;
     }

     private boolean checkKing(AbstractSquare c) {
	 return c.hasPiece() && c.getPiece().isKing();
     }

     private boolean browseSquare(AbstractSquare c, PlayableBoard b, BrowseType f) {
	 boolean ret;

	 switch(f) {
	 case GREEN_SQUARES:
	     ret = setGreen(c, b);
	     break;
	 case CHECK_KING:
	     ret = checkKing(c);
	     break;
	 case CHOICE_LIST:
	     ret = true;
	     if(!choices.contains(c))
		 choices.add(c);
	     break;
	 case CHECK_RANGE:
	     ret = c.getCoords().equals(newCoords);
	     break;
	 default:
	     ret = false;
	 }

	 return ret;
     }

     private boolean checkSquare(int i, int j, PlayableBoard ab, BrowseType f, MoveType m) {
	 boolean b = ab.isSquareValid(i,j) && ((m.isAttackType() 
						&& ab.hasPiece(i, j)
						&& ab.getPiece(i, j).isWhite() != white)
					       || (m.isWalkType()
						   && ! ab.hasPiece(i, j)));
	 return b && browseSquare(ab.getSquare(i, j), ab, f);
     }


     private boolean browseBoard(int i, int j, PlayableBoard board, BrowseType f) {


	 boolean movable = false;
	 if(king && !moved) 
	     // CASTLE
	     for(int dir = -1 ; dir < 2 ; dir += 2) {
		 int xx = dir > 0 ? board.getCols()-1 : 0; // X coord of the rook
		 if(board.isSquareValid(xx, j) && board.hasPiece(xx, j)) {
		     Piece p = board.getSquare(xx, j).getPiece();
		     if(p.isRook() && !p.hasMoved()) {
			 int diff = dir > 0 ? xx - i :  i - xx; // Distance between king and rook
			 boolean possible = !board.isKingInRange() && diff > 2;
			 if(possible)
			     for(xx = i+dir ; xx > 0 && xx < (board.getCols()-1) ; xx += dir)
				 possible &= !(board.isSquareValid(xx, j) && board.getSquare(xx, j).hasPiece());
			 if(possible)
			     movable |= browseSquare(board.getSquare(i+(2*dir), j), board, f);
		     }
		 }
	     }

	 ArrayList<MoveType> moves = new ArrayList<MoveType>(this.moves);
	 // JOKER
	 if(joker) {
	     Piece jokerPiece = board.getJokerPiece();
	     if(jokerPiece != null) 
		 moves.addAll(jokerPiece.getMoveTypes());
	 }
	 
	 Move lastMove = board.getLastMove();
	 boolean pawnType = lastMove == null ? false: lastMove.isPawnType();

	 for(MoveType m : moves) {

	     int s = m.getOffset();
	     int k = m.getStep();
	     int x = m.getXDiff();
	     int y = m.getYDiff();

	     if(! white) {
		 x=-x;
		 y=-y;
	     }

	     if(s == 0 && m.isWalkType()) {
		 movable |= browseSquare(board.getSquare(i,j), board, f);
		 s+=k;
	    }
	    
	    if(!m.isPawnType() || (j == 1 && white) || (j+2 == board.getRows() && !white) ) {

		// MIDDLE DIRECTION
		if(m.isSquare()) {
		    int max = board.getMaxDistance();
		    for(;m.isInRange(s) && s < max;s+=k)
			for(int kk = 0 ; kk < s*2 ; kk ++) {
			    movable |= checkSquare(i-s, j-s+kk, board, f, m);
			    movable |= checkSquare(i-s+kk+1, j-s, board, f, m);
			    movable |= checkSquare(i+s, j-s+kk+1, board, f, m);
			    movable |= checkSquare(i-s+kk, j+s, board, f, m);
			}
		    
		} else {

		    AbstractSquare c = null;
		    boolean b = board.isSquareValid(i+s*x, j+s*y);
		    if(b) {
			c = board.getSquare(i+s*x, j+s*y);
			if(m.isWalkType())
			    // WALKS
			    while(b && !c.hasPiece() && m.isInRange(s)) {
				if(! m.isHopperType())
				    movable |= browseSquare(c, board, f);
				s += k;
				b = board.isSquareValid(i+s*x, j+s*y);
				if(b)
				    c = board.getSquare(i+s*x, j+s*y);
			    }
			else while(b && !c.hasPiece() && m.isInRange(s)) {
				s+=k;
				b = board.isSquareValid(i+s*x, j+s*y);
				if(b)
				    c = board.getSquare(i+s*x, j+s*y);
			    }

			boolean enPassant = pawnType && isPawn() && lastMove.getOldCoords().getCoords().add(lastMove.getNewCoords()).divide(2).equals(c.getCoords().substract(new Coords(x, y)));
			if(enPassant) {
			    c = board.getSquare(c.getCoords().substract(new Coords(x, y)));
			    s--;
			}

			// ATTACKS
			if(m.isInRange(s) && (c.hasPiece() || enPassant)) {
			    if(!enPassant && m.isHopperType()) {
				s += k;
				if(board.isSquareValid(i+s*x, j+s*y)) {
				    c = board.getSquare(i+s*x, j+s*y);
				    if(((c.hasPiece() && m.isAttackType()
					 && c.getPiece().isWhite() != white)
					|| !c.hasPiece() && m.isWalkType()) )
					movable |= browseSquare(c, board, f);
				}
			    } else if(m.isAttackType() && (enPassant || c.getPiece().isWhite() != white))
				movable |= browseSquare(c, board, f);
			}
		    }
		}
	    }
	}
	return movable;

    }

    /** Set all the squares reachable by this piece green
     * @param i this piece's square's column (X Coord)
     * @param j this piece's square's row (Y Coord)
     * @param board the abstract board of the piece
     * @return true if the piece is movable
     */
    public boolean setGreenSquares(int i, int j, PlayableBoard board) {
	return board.isForeseer()
	    ? board.getSquare(i, j).setGreenSquares(board)
	    : browseBoard(i, j, board, BrowseType.GREEN_SQUARES);
    }

    public ArrayList<AbstractSquare> getChoiceList(int i, int j, PlayableBoard board) {
	choices.clear();
	browseBoard(i, j, board, BrowseType.CHOICE_LIST);
	return choices;
    }

    /** Check whether this piece has a king in range
     * @param i this piece's square's column (X Coord)
     * @param j this piece's square's row (Y Coord)
     * @param board the abstract board of the piece
     * @return true if it has
     */
    public boolean checkKingInRange(int i, int j, PlayableBoard board) {
	return browseBoard(i, j, board, BrowseType.CHECK_KING);
    }

    public int getChoiceCount(int i, int j, PlayableBoard board) {
	
	return board.isForeseer()
	    ? board.getSquare(i, j).getChoiceListSize()
	    : getChoiceList(i, j, board).size();
    }

    /** Check whether this piece can reach a given square,
     * ie whether this move would be legal or not
     * @param oldc the current coordinates of the piece
     * @param newc the coordinates that have to be tested
     * @param board the abstract board of the piece
     * @return true if it has
     */
    public boolean checkSquareInRange(Coords oldc, Coords newc, PlayableBoard board) {
	newCoords = newc;
	return browseBoard(oldc.getColumn(), oldc.getRow(), board, BrowseType.CHECK_RANGE);
    }




    // IMAGE

    /** Get the graphical image which corresponds to this piece
     * @return the unscaled image
     */
    public ImageIcon getImage() { return image; }

    /** Get a scaled version of the image corresponding to this piece
     * @param dims the dimension of the image's square's side 
     * @return the scaled version of the image that will be used in GraphicalSquare
     */
    public ImageIcon getImage(int dims) {
	Integer i = new Integer(dims);
	if(icons.containsKey(i) )
	    return icons.get(i);
	else {
	    ImageIcon ii = PieceImages.getScaledImage(image, dims);
	    icons.put(i,ii);
	    return ii;
	}
    }

    /** Set the image that represents this piece
     * @param i the absolute path of the image
     */
    public void setImage(String i)  {
	image = new ImageIcon(i);
	icons = new HashMap<Integer, ImageIcon>();
	icons.put(new Integer(image.getIconWidth()), image);

    }



    //  GETTERS / SETTERS

    /** Get the letter of this piece
     * @return the first letter of this piece's name, in lower case
     */
    public char getLetter() {
	return name.toUpperCase().charAt(0);
    }

    /** Get this piece's name
     * @return the name as a string
     */
    public String getName() { 
	return name;
    }

    /** Tell whether this piece is white
     * @return true if it is
     */
    public boolean isWhite() {
	return white;
    }

    /** Tell whether this piece has been moved
     * @return true if it has
     */
    public boolean hasMoved() {
	return moved;
    }

    @Override
    public boolean isKing()  { return king;  }
    @Override
    public boolean isPawn()  { return pawn;  }
    @Override
    public boolean isRook()  { return rook;  }
    @Override
    public boolean isJoker() { return joker; }


    /** Set this piece's name
     * @param s the new name for this piece
     */
    public void setName(String s) {
	name = s;
    }

    /** Set this piece color
     * @param b true if this piece is white
     */
    public void setWhite(boolean b) {
	white = b;
    }

    /** Set whether this piece has been moved already
     * @param b true if it has
     */
    public void setMoved(boolean b) {
	moved = b;
    }

    @Override
    public void setKing(boolean b)  { king = b;  }
    @Override
    public void setPawn(boolean b)  { pawn = b;  }
    @Override
    public void setRook(boolean b)  { rook = b;  }
    @Override
    public void setJoker(boolean b) { joker = b; }

    public String getMCSFormat() {
	StringBuilder s = new StringBuilder();
	s.append(white?'W':'B');
	s.append(name);
	s.append("\t");
	return s.toString();
    }

    @Override
	public String toString() {
	StringBuilder s = new StringBuilder("piece:\t");
	s.append(getMCSFormat());
	if(king) s.append(" king");
	if(rook) s.append(" rook");
	if(joker)s.append(" joker");
	if(pawn) s.append(" pawn");
	s.append("( price = ");
	s.append(getPrice());
	s.append(" )");
	return s.toString();
    }


}


