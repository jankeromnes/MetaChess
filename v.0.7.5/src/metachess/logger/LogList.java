package metachess.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class LogList extends AbstractListModel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lastIndex;
    private ArrayList<Move> moves;

    public LogList() {
	moves = new ArrayList<Move> ();
	lastIndex = -1;
    }

    public String getElementAt(int index) {
	return moves.get(index).toString();
    }

    public void addMove(Move m) {
	lastIndex++;

	while(moves.size() > lastIndex)
	    moves.remove(lastIndex);
	moves.add(m);
	update();
    }
    
    public int getSize() {
	return moves.size();
    }

    public void update() {
	fireContentsChanged(this,0, getSize());
    }

    public void clearMoves() {
	moves.clear();
	lastIndex = -1;
	update();
    }

    public int getLastIndex() {
	return lastIndex;
    }

    public ArrayList<Move> getMoves() {
	return getMoves(moves.size()-1);
    }

    public ArrayList<Move> getMoves(int max) {

	lastIndex = max++;
	ArrayList<Move> ret = new ArrayList<Move>(max);
	for(int i = 0 ; i < max ; i++)
	    ret.add(moves.get(i));
	return ret;
    }

    public ArrayList<Move> back() {
	return getMoves(lastIndex-1);
    }

    public ArrayList<Move> forward() {
	return getMoves(lastIndex+1);
    }

    public boolean isBackable() {
	return lastIndex > -1;
    }

    public boolean isForwardable() {
	return lastIndex+1 < moves.size();
    }


    public void saveGame(File f) {
	try {
	    ObjectOutputStream s;
	    s = new ObjectOutputStream(new FileOutputStream(f));
	    s.writeObject(moves);
	    s.close();
	} catch(IOException e) {}

    }

    public void loadGame(ArrayList<Move> m) {
	    moves = m;
	    lastIndex = moves.size()-1;
	    update();
    }

}

