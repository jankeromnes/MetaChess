package metachess.library;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractListModel;

/** Class of the model for the Piece List
 * @author Agbeladem
 * @version 0.9.0
 */
public class PieceListModel extends AbstractListModel {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> pieces;

    /** Create a list model for the piece list.
     * The list will be empty */
    public PieceListModel() {
	pieces = new ArrayList<String>();
    }

    /** Load a list of pieces in this model
     * @param list the list of names of the pieces that will be used
     */
    public void load(String[] list) {
	pieces.clear();
	for(String s : list) {
	    int n = s.lastIndexOf('.');
	    if(n != -1) s = s.substring(0, n);
	    pieces.add(s);
	}
	update();
    }

     /** Load a list of pieces in this model
     * @param list the list of names of the pieces that will be used
     */
    public void load(ArrayList<String> pieces) {
	this.pieces = pieces;
	update();
    }

    @Override
    public int getSize() {
	return pieces.size();
    }

    @Override
    public String getElementAt(int index) {
	Collections.sort(pieces);
	return pieces.get(index);
    }

    /** Notify the model and thus its associated view that this list has been updated */
    public void update() {
	fireContentsChanged(this, 0, getSize());
    }

    /** Add a piece to this model
     * @param piece the name of the piece that will be added
     */
    public void add(String piece) {
	if(!pieces.contains(piece))
	    pieces.add(piece);
	update();
    }

    /** Remove a piece in this model
     * @param piece the name of the piece that will be removed
     */
    public void remove(String piece) {
	pieces.remove(piece);
	update();
    }

    /** Get the list of all the pieces contained in this model
     * @return the list of pieces in this model
     */
    public ArrayList<String> getPieces() {
	return pieces;
    }

}