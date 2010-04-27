package metachess.game;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import metachess.boards.AbstractBoard;
import metachess.boards.AbstractSquare;
import metachess.library.PiecesImages;

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

	if(m.isComboDirection()) {
	    if(m.isSlash()) {
		moves.add(new MoveType(type, '0', range));
		moves.add(new MoveType(type, '8', range));
	    }
	    if(m.isBackSlash()) {
		moves.add(new MoveType(type, '2', range));
		moves.add(new MoveType(type, '6', range));
	    }
	    if(m.isVertically()) {
		moves.add(new MoveType(type, '1', range));
		moves.add(new MoveType(type, '7', range));
	    }
	    if(m.isHorizontally()) {
		moves.add(new MoveType(type, '3', range));
		moves.add(new MoveType(type, '5', range));
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
    private ArrayList<MoveType> getMoveTypes() { return moves; }

 
    // BOARD BROWSING COMMANDS

    private boolean setGreen(AbstractSquare c, AbstractBoard abstractBoard) {

    	boolean kingInCheck = false;
    	c.setGreen(!kingInCheck);
    	return !kingInCheck;
    }

    private boolean checkKing(AbstractSquare c) {
	return c.getPiece() != null && c.getPiece().isKing();
    }

    private boolean checkSquare(AbstractSquare c, AbstractBoard b, BrowseType f) {
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

    private boolean browseBoard(int i, int j, AbstractBoard abstractBoard, BrowseType f) {

	boolean movable = false;

	if(king && abstractBoard.getCols() == 8 && !moved) 
	    // ROOK
	    for(int dir = -1 ; dir < 2 ; dir += 2) {
		Piece p = abstractBoard.getSquare(7*((dir+1)/2), j).getPiece();
		if(p != null && p.isRook() && !p.hasMoved()) {
		    boolean possible = true;
		    for(int xx = i+dir ; xx > 0 && xx < 7 ; xx += dir)
			possible &= !(abstractBoard.getSquare(xx, j).hasPiece());
		    if(possible && f == BrowseType.GREEN_SQUARES)
			movable |= setGreen(abstractBoard.getSquare(i+(2*dir), j), abstractBoard);
		}
	    }
	    
	// JOKER
	if(joker) {
	    Piece jokerPiece = abstractBoard.getJokerPiece();
	    if(jokerPiece != null) {
		boolean wasWhite = jokerPiece.isWhite();
		jokerPiece.setWhite(white);
		movable |= jokerPiece.browseBoard(i, j, abstractBoard, f);
		jokerPiece.setWhite(wasWhite);
	    }
	}

	for(MoveType m : moves) {

	    int s = 1;
	    int x = m.getXDiff();
	    int y = m.getYDiff();
	    if(! white) {
		x=-x;
		y=-y;
	    }

	    AbstractSquare c = abstractBoard.getSquare(i+s*x,j+s*y);


	    if(!m.isPawnType() || (j == 1 && white) || (j+2 == abstractBoard.getRows() && !white) ) {

		// WALKS
		while(c != null && m.isWalkType() && c.getPiece() == null && m.isInRange(s)) {
		    if(f == BrowseType.GREEN_SQUARES && ! m.isHopperType())
			movable |= setGreen(c, abstractBoard);
		    s++;
		    c = abstractBoard.getSquare(i+s*x,j+s*y);
		}
	    
		// ATTACKS
		if(m.isInRange(s) && c != null && c.getPiece() != null) {

		    if(m.isHopperType()) {
			s++;
			c = abstractBoard.getSquare(i+s*x, j+s*y);

			if(c != null && ((c.getPiece() != null && m.isAttackType()
					  && c.getPiece().isWhite() != white)
					 || c.getPiece() == null && m.isWalkType()) )
			    movable |= checkSquare(c, abstractBoard, f);

		    } else if(m.isAttackType() && c.getPiece().isWhite() != white)
			movable |= checkSquare(c, abstractBoard, f);
		}
	    }
	    

	}

	return movable;

    }

    
    public boolean setGreenSquares(int i, int j, AbstractBoard board) {
	return browseBoard(i, j, board, BrowseType.GREEN_SQUARES);
    }

    public boolean checkKingInRange(int i, int j, AbstractBoard abstractBoard) {
	return browseBoard(i, j, abstractBoard, BrowseType.CHECK_KING);
    }


    //  GETTERS / SETTERS

    public ImageIcon getImage() { return image; }
    public ImageIcon getImage(int dims) {
	Integer i = new Integer(dims);
	if(icons.containsKey(i) )
	    return icons.get(i);
	else {
	    ImageIcon ii = PiecesImages.getScaledImage(image, dims);
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


