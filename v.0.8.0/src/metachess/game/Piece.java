package metachess.game;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import metachess.boards.AbstractBoard;
import metachess.boards.AbstractSquare;
import metachess.library.PieceImages;

/** Class of an Abstract Piece
 * @author Agbeladem and Jan (7DD)
 * @version 0.8.0
 */
public class Piece {

    enum BrowseType {
	CHECK_KING, GREEN_SQUARES
    }

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

    /** Create a new empty piece */
    public Piece() {

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
    		price/=83.4;
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

    /** Reset the available move types with a given list
     * @param m the list
     */
    public void setMoves(ArrayList<MoveType> m) {
	 moves.clear();
	 for(MoveType mt : m)
	     addMoveType(mt);
     }
     public ArrayList<MoveType> getMoveTypes() { return moves; }


     // BOARD BROWSING COMMANDS

     private boolean setGreen(AbstractSquare c, AbstractBoard ab) {

	 // TODO test king is in check
     /* PROBLEM : new board also tests if king is in check */
     //PlayableBoard actualBoard = (PlayableBoard)ab;
	 //AIBoardTree checkTestBoard = new AIBoardTree(actualBoard, 0);
	 //boolean kingInCheck = checkTestBoard.isKingInCheck();
    	 
     boolean kingInCheck = false;
	 c.setGreen(!kingInCheck);
	 return !kingInCheck;
     }

     private boolean checkKing(AbstractSquare c) {
	 return c.getPiece() != null && c.getPiece().isKing();
     }



     private boolean browseSquare(AbstractSquare c, AbstractBoard b, BrowseType f) {
	 boolean ret;

	 switch(f) {
	 case GREEN_SQUARES:
	     ret = setGreen(c, b);
	     break;
	 case CHECK_KING:
	     ret = checkKing(c);
	     break;
	 default:
	     ret = false;
	 }

	 return ret;
     }

     private boolean checkSquare(int i, int j, AbstractBoard ab, BrowseType f, MoveType m) {
	 boolean b = ab.isSquareValid(i,j) && ((m.isAttackType() 
						&& ab.hasPiece(i, j)
						&& ab.getPiece(i, j).isWhite() != white)
					       || (m.isWalkType()
						   && ! ab.hasPiece(i, j)));
	 return b && browseSquare(ab.getSquare(i, j), ab, f);
     }


     private boolean browseBoard(int i, int j, AbstractBoard ab, BrowseType f) {


	 boolean movable = false;

	 if(king && !moved) 
	     // CASTLE
	     for(int dir = -1 ; dir < 2 ; dir += 2) {
		 int xx = (ab.getCols()-1)*((dir+1)/2);
		 if(ab.squareExists(xx, j) && ab.hasPiece(xx, j)) {
		     Piece p = ab.getSquare(xx, j).getPiece();
		     if(p.isRook() && !p.hasMoved()) {
			 boolean possible = true;
			 for(xx = i+dir ; xx > 0 && xx < (ab.getCols()-1) ; xx += dir)
			     possible &= !(ab.getSquare(xx, j).hasPiece());
			 if(possible && f == BrowseType.GREEN_SQUARES)
			     movable |= setGreen(ab.getSquare(i+(2*dir), j), ab);
		     }
		 }
	     }
	 // JOKER
	 if(joker) {
	     Piece jokerPiece = ab.getJokerPiece();
	     if(jokerPiece != null) {
		 boolean wasWhite = jokerPiece.isWhite();
		 jokerPiece.setWhite(white);
		 movable |= jokerPiece.browseBoard(i, j, ab, f);
		 jokerPiece.setWhite(wasWhite);
	     }
	 }

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
		movable |= browseSquare(ab.getSquare(i,j), ab, f);
		s+=k;
	    }
	    
	    if(!m.isPawnType() || (j == 1 && white) || (j+2 == ab.getRows() && !white) ) {

		// MIDDLE DIRECTION
		if(m.isSquare()) {
		    int max = ab.getMaxDistance();
		    for(;m.isInRange(s) && s < max;s+=k)
			for(int kk = 0 ; kk < s*2 ; kk ++) {
			    movable |= checkSquare(i-s, j-s+kk, ab, f, m);
			    movable |= checkSquare(i-s+kk+1, j-s, ab, f, m);
			    movable |= checkSquare(i+s, j-s+kk+1, ab, f, m);
			    movable |= checkSquare(i-s+kk, j+s, ab, f, m);
			}
		    
		} else {

		    AbstractSquare c = null;
		    boolean b = ab.squareExists(i+s*x, j+s*y);
		    if(b) {
			c = ab.getSquare(i+s*x, j+s*y);
			if(m.isWalkType())
			    // WALKS
			    while(b && !c.hasPiece() && m.isInRange(s)) {
				if(f == BrowseType.GREEN_SQUARES && ! m.isHopperType())
				    movable |= browseSquare(c, ab, f);
				s += k;
				b = ab.squareExists(i+s*x, j+s*y);
				if(b)
				    c = ab.getSquare(i+s*x, j+s*y);
			    }
			else while(b && !c.hasPiece() && m.isInRange(s)) {
				s+=k;
				b = ab.squareExists(i+s*x, j+s*y);
				if(b)
				    c = ab.getSquare(i+s*x, j+s*y);
			    }
			    
		    
			// ATTACKS
			if(m.isInRange(s) && c.hasPiece()) {
			    if(m.isHopperType()) {
				s += k;
				if(ab.squareExists(i+s*x, j+s*y)) {
				    c = ab.getSquare(i+s*x, j+s*y);
				    if(((c.hasPiece() && m.isAttackType()
					 && c.getPiece().isWhite() != white)
					|| !c.hasPiece() && m.isWalkType()) )
					movable |= browseSquare(c, ab, f);
				}
			    } else if(m.isAttackType() && c.getPiece().isWhite() != white) {

				movable |= browseSquare(c, ab, f);
			    }
			}
		    }
		}
	    }
	}

	return movable;

    }

    /** Set all the squares reachable by the piece green
     * @param i the piece's square's column (X Coord)
     * @param j the piece's square's row (Y Coord)
     * @param board the abstract board of the piece
     * @return true if the piece is movable
     */
    public boolean setGreenSquares(int i, int j, AbstractBoard board) {
	return browseBoard(i, j, board, BrowseType.GREEN_SQUARES);
    }

    /** Check whether the piece has a king in range
     * @param i the piece's square's column (X Coord)
     * @param j the piece's square's row (Y Coord)
     * @param board the abstract board of the piece
     * @return true if it has
     */
    public boolean checkKingInRange(int i, int j, AbstractBoard ab) {
	return browseBoard(i, j, ab, BrowseType.CHECK_KING);
    }


    //  GETTERS / SETTERS

    public ImageIcon getImage() { return image; }
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

    public String getName() { return name; }
    public boolean isWhite() { return white; }
    public boolean hasMoved() { return moved; }


    public boolean isKing()  { return king;  }
    public boolean isPawn()  { return pawn;  }
    public boolean isRook()  { return rook;  }
    public boolean isJoker() { return joker; }

    public void setImage(String i)  {

	image = new ImageIcon(getClass().getResource(i));

	icons = new HashMap<Integer, ImageIcon>();
	icons.put(new Integer(image.getIconWidth()), image);

    }


    public void setName(String s) { name = s; }
    public void setWhite(boolean b) { white = b ; }
    public void setMoved(boolean b) { moved = b ; }

    public void setKing(boolean b)  { king = b ;  }
    public void setPawn(boolean b)  { pawn = b ;  }
    public void setRook(boolean b)  { rook = b ;  }
    public void setJoker(boolean b) { joker = b ; }

    public String getMCSFormat() {
	StringBuilder s = new StringBuilder();
	s.append(white?'W':'B');
	s.append(name);
	s.append("\t");
	return s.toString();
    }

    @Override
	public String toString() {
	StringBuilder s = new StringBuilder();
	s.append(name);
	s.append("   _");
	s.append(hashCode());
	s.append("\n\timage = "+image);
	s.append("\n\twhite = "+white);
	s.append("\n\tking = "+king);
	s.append("\n\trook = "+rook);
	s.append("\n\tjoker = "+joker);
	s.append("\n\tpawn = "+pawn);
	return s.toString();
    }


}


