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
	
	private Vector<PlayerView> players;

    /** Create a state panel
     * @param g the current game
     */
    public StatusPanel(Game g) {
    	super();
    	
    	setPreferredSize(new Dimension(250, 30));
    	
    	players = new Vector<PlayerView>();
    	
    	players.add(new PlayerView(true));
    	players.add(new PlayerView(false));
    	
    	for(PlayerView player : players) {
    		add(player);
        	add(Box.createHorizontalGlue());
    	}
    	
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    

}
