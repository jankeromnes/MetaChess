package metachess.builder.piecebuilder;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import metachess.game.MoveType;

public class MovesListModel extends AbstractListModel {

    private static final long serialVersionUID = 1L;
    private ArrayList<MoveType> moves;

    public MovesListModel() {
	moves = new ArrayList<MoveType>();
    }

    public String getElementAt(int index) {
	return moves.get(index).toString();
    }
    
    public void add(MoveType mt) {
	if(!moves.contains(mt))
	    moves.add(mt);
	update();
    }

    public ArrayList<MoveType> getMoves() {
	return moves;
    }

    public void setMoves(ArrayList<MoveType> m) {
	moves = m;
	update();
    }

    public int getSize() {
	return moves.size();
    }

    public void update() {
	fireContentsChanged(this, 0, getSize());
    }

    public void delete(int i) {
	assert(i < getSize()): "MovesListModel : Wrong index for delete";
	moves.remove(i);
	update();
    }
    
}


