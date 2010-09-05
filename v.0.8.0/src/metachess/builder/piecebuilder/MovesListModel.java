package metachess.builder.piecebuilder;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import metachess.game.MoveType;

/** Class of the moves abstract list in the Moves Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */
public class MovesListModel extends AbstractListModel {

    private static final long serialVersionUID = 1L;
    private ArrayList<MoveType> moves;

    /** Creation of a list model of the moves for the Moves Builderbox */
    public MovesListModel() {
	moves = new ArrayList<MoveType>();
    }

    @Override
    public String getElementAt(int index) {
	return moves.get(index).toString();
    }
    
    /** Add a move to this list
     * @param mt the move to add to this list
     */
    public void add(MoveType mt) {
	if(!moves.contains(mt))
	    moves.add(mt);
	update();
    }

    /** Get all the moves contained in this list
     * @return the moves as an ArrayList
     */
    public ArrayList<MoveType> getMoves() {
	return moves;
    }

    /** Replace all the moves of this list by the moves of a given list
     * @param m the new list that this model will use
     */
    public void setMoves(ArrayList<MoveType> m) {
	moves = m;
	update();
    }

    @Override
    public int getSize() {
	return moves.size();
    }

    /** Notify the model and thus its associated view that this list has been updated */
    public void update() {
	fireContentsChanged(this, 0, getSize());
    }

    /** Delete a move in this list according to its identifier
     * @param i the identifier of the move to delete
     */
    public void delete(int i) {
	assert(i < getSize()): "MovesListModel : Wrong index for delete";
	moves.remove(i);
	update();
    }
    
}


