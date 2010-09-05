package metachess.builder.piecebuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import metachess.builder.PanelTitle;

/** Class of the Direction Choice Panel in the Moves Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */ 
public class DirectionChoicePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JRadioButton all, rook, hori, verti, diag, slash, backslash, advanced;

    private final MovesBox parent;
    private boolean isAdvanced;
    private char result;

    /** Create a Moves Direction Choice Panel
     * @param box the Moves Builderbox to which this panel belongs
     */
    public DirectionChoicePanel(MovesBox box) {
	super();
	parent = box;

	all = new JRadioButton("All directions");


	rook = new JRadioButton("Rook-style");
	hori = new JRadioButton("Horizontally");
	verti = new JRadioButton("Vertically");

	diag = new JRadioButton("Diagonally");
	slash = new JRadioButton("Slash direction");
	backslash = new JRadioButton("Backslash direction");

	advanced = new JRadioButton("Advanced");
	advanced.setAlignmentX(CENTER_ALIGNMENT);

	ButtonGroup bg = new ButtonGroup();

	bg.add(all);
	bg.add(rook);
	bg.add(hori);
	bg.add(verti);
	bg.add(diag);
	bg.add(slash);
	bg.add(backslash);
	bg.add(advanced);

	
	ActionListener ev = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Object o = e.getSource();
		    isAdvanced = o == advanced;
		    if(o == all) result = 'A';
		    else if(o == rook) result = 'R';
		    else if(o == verti) result = 'V';
		    else if(o == hori) result = 'H';
		    else if(o == diag) result = 'D';
		    else if(o == slash) result = 'S';
		    else if(o == backslash) result = 'B';
		    parent.setAbled(isAdvanced);
		}
	    };
	all.addActionListener(ev);
	rook.addActionListener(ev);
	hori.addActionListener(ev);
	verti.addActionListener(ev);
	diag.addActionListener(ev);
	slash.addActionListener(ev);
	backslash.addActionListener(ev);
	advanced.addActionListener(ev);	
	
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(Box.createVerticalGlue());
	add(new PanelTitle("Set directions"));
	add(all);
	add(new PanelTitle("Sides"));
	add(rook);
	add(hori);
	add(verti);
	add(new PanelTitle("Diagonals"));
	add(diag);
	add(slash);
	add(backslash);
	add(Box.createVerticalGlue());
	add(advanced);
	add(Box.createVerticalGlue());

    }

    /** (Re)initialize this panel */
    public void init() {
	all.setSelected(true);
	isAdvanced = false;
	result = 'A';
    }

    /** Get the direction chosen in this panel
     * @return the selected direction, as an MCP character
     */
    public char getDirection() {
	return result;
    }

    /** Tell whether the user have to select the authorized directions by himself
     * @return true if he does
     */
    public boolean isAdvancedDirection() {
	return isAdvanced;
    }

}

