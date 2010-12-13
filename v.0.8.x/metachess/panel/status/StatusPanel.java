package metachess.panel.status;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import metachess.game.Game;
import metachess.library.Colour;
import metachess.library.PieceImages;

/** Class of the state panel
 * @author Jan
 * @version 0.8.9
 */
public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// I know the correct word is 'statuses'. Now let's get a little creative... Jan ;)
	private Vector<PlayerStatus> statii;

    /** Create a status panel
     * @param g the current game
     */
    public StatusPanel(Game g) {
    	super();
    	
    	setPreferredSize(new Dimension(250, 30));
    	
    	statii = new Vector<PlayerStatus>();
    	
    	statii.add(new PlayerStatus(true));
    	statii.add(new PlayerStatus(false));
    	
    	for(PlayerStatus status : statii) {
    		add(status);
        	add(Box.createHorizontalGlue());
    	}
    	
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    public void setActivePlayer(boolean white) {
    	for(PlayerStatus status : statii) {
    		status.setActivePlayer(white);
    	}
    }

	public void clear() {
		for(PlayerStatus status : statii) {
    		status.clear();
    	}
	}

}
