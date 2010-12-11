package metachess.panel.state;

import javax.swing.JPanel;

import metachess.game.Game;

/** Class of the state panel
 * @author Jan
 * @version 0.8.9
 */
public class StatePanel extends JPanel {

    private Game game;

    /** Create a state panel
     * @param g the current game
     */
    public StatePanel(Game g) {
	game = g;
    }

}
