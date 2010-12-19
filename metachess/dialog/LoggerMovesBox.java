package metachess.dialog;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.ArrayList;

import metachess.game.Move;

/** Class of the logger's moves' text format
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class LoggerMovesBox extends JDialog {

    private final JTextArea field;

    /** Create a logger moves box */
    public LoggerMovesBox() {
	super();
	setTitle("Moves Text Format");
	field = new JTextArea();
	field.setPreferredSize(new Dimension(200, 200));

	add(new JScrollPane(field));
	pack();
    }

    /** Launch this box with a given list of moves
     * @param moves the list of moves ; the moves must be resolved
     */
    public void launch(ArrayList<Move> moves) {
	StringBuilder s = new StringBuilder();
	int i = 0;
	int n = moves.size();
	while(i < n) {
	    if(i%2 == 0) {
		s.append(1+i/2);
		s.append(". ");
	    } else s.append("\t ... ");
	    s.append(moves.get(i));
	    if(++i % 2 == 0)
		s.append("\n");
	}
	field.setText(s.toString());
	setVisible(true);
    }

}

