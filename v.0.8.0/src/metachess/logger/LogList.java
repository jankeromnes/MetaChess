package metachess.logger;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

/** Class of the moves history abstract list in the logger
 * @author Agbeladem (7DD)
 * @version 0.8.1
 */
public class LogList extends AbstractListModel {

    private static final long serialVersionUID = 1L;
    private int lastIndex;
    private ArrayList<Move> moves;

    /** Creation of a list model of the moves history */
    public LogList() {
	moves = new ArrayList<Move> ();
	lastIndex = -1;
    }
    
    @Override
    public int getSize() {
	return moves.size();
    }

    @Override
    public String getElementAt(int index) {
	return moves.get(index).toString();
    }

    /** Add a move to this list
     * @param m the move to add
     */
    public void addMove(Move m) {
	lastIndex++;

	while(moves.size() > lastIndex)
	    moves.remove(lastIndex);
	moves.add(m);
	update();
    }

    /** Notify the model and thus its associated view that this list has been updated */
    public void update() {
	fireContentsChanged(this, 0, getSize());
    }

    /** Clear all the moves contained in this list */
    public void clearMoves() {
	moves.clear();
	lastIndex = -1;
	update();
    }

    /** Get the last index of this list
     * @return the last index
     */
    public int getLastIndex() {
	return lastIndex;
    }

    /** Get all the moves contained in this list
     * @return a list of moves
     */
    public ArrayList<Move> getMoves() {
	return getMoves(moves.size()-1);
    }

    /** Get all the moves contained in this list until a given index
     * WARNING: this also affects this list model's last index value
     * @param max at index to which the sublist of moves should end
     * @return the moves history as an ArrayList
     */
    public ArrayList<Move> getMoves(int max) {
	lastIndex = max++;
	ArrayList<Move> ret = new ArrayList<Move>(max);
	for(int i = 0 ; i < max ; i++)
	    ret.add(moves.get(i));
	return ret;
    }

    /** Go back by one move in this list's history */
    public ArrayList<Move> back() {
	assert isBackable();
	return getMoves(lastIndex-1);
    }

    /** Go forward by one move in this list's history */
    public ArrayList<Move> forward() {
	assert isForwardable();
	return getMoves(lastIndex+1);
    }

    /** Tell whether we can go back in this list 
     * @return true if this list contains enough moves to go back
     */
    public boolean isBackable() {
	return lastIndex > -1;
    }

    /** Tell whether we can go further in this list
     * @return true if the user has gone back in the list already
     */
    public boolean isForwardable() {
	return lastIndex+1 < moves.size();
    }

    /* // Obselete by Jan's game.SavedGame
    public void saveGame(File f) {
	try {
	    ObjectOutputStream s;
	    s = new ObjectOutputStream(new FileOutputStream(f));
	    s.writeObject(moves);
	    s.close();
	} catch(IOException e) {}

    }
    */

    /** Load all the moves of a loaded game
     * @param m the list of moves to load in this list
     */
    public void loadGame(ArrayList<Move> m) {
	    moves = m;
	    lastIndex = moves.size()-1;
	    update();
    }

}

