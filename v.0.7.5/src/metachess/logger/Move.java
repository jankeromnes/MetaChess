package metachess.logger;

import java.io.Serializable;

import metachess.boards.AbstractBoard;

public class Move implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private int oldx;
    private int oldy;
    private int newx;
    private int newy;
    private transient AbstractBoard board;
    private String name;

    public Move() {};

    public Move(int oldxarg, int oldyarg
		, int newxarg, int newyarg
		, AbstractBoard abstractBoard) {
	
	board = abstractBoard;
	oldx = oldxarg;
	oldy = oldyarg;
	newx = newxarg;
	newy = newyarg;
	
	StringBuilder s = new StringBuilder();
	s.append(board.getSquare(oldx,oldy).getName());
	s.append(" => ");
	s.append(board.getSquare(newx,newy).getName());
	name = s.toString();

    }

    public int getOldX() { return oldx; }
    public int getOldY() { return oldy; }
    public int getNewX() { return newx; }
    public int getNewY() { return newy; }
   
    @Override
	public String toString() {
	return name;
    }

}

