package metachess.panel;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import metachess.game.Game;
import metachess.game.Move;
import metachess.game.SavedGame;
import metachess.model.PanelLinkBehaviour;
import metachess.panel.count.CountPanel;
import metachess.panel.logger.LogPanel;
import metachess.panel.status.StatusPanel;

/** Class of the right panel
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public class MainPanel extends JPanel implements PanelLinkBehaviour {

    private static final long serialVersionUID = 1L;
    private final CountPanel countPanel;
    private final StatusPanel status;
    private final LogPanel histo;


    /** Create a right panel 
     * @param g the current Game
     */
    public MainPanel(Game g) {

	countPanel = new CountPanel();
	histo = new LogPanel(g);
	status = new StatusPanel(g);

	add(status);
	add(Box.createVerticalGlue());
	add(countPanel);
	add(Box.createVerticalGlue());
	add(histo);
	add(Box.createVerticalGlue());

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    @Override
    public ArrayList<Move> getMoves() {
	return histo.getMoves();
    }

    @Override
    public void loadGame(SavedGame sg) {
	countPanel.clear();
	histo.loadGame(sg.getMoves());
    }

    @Override
    public void clear(boolean clear) {
	countPanel.clear();
	status.clear();
	if(clear) {
	    histo.clearMoves();
	    histo.update();
	}
    }

    @Override
    public void addMove(Move m) {
    	histo.addMove(m);
    }

    @Override
    public void undo(){
    	histo.undo();
    }

    @Override
    public void redo() {
    	histo.redo();
    }

    @Override
    public void count(String pieceName, boolean isWhite) {
    	countPanel.add(pieceName, isWhite);
    }

	@Override
	public void setActivePlayer(boolean white) {
		status.setActivePlayer(white);
	}



}



