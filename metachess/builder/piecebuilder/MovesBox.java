package metachess.builder.piecebuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import metachess.builder.BuilderBox;

/** Class of the Move Builderbox for the piece builder
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class MovesBox extends JDialog {

    private static final long serialVersionUID = 1L;
    private final DirectionChoicePanel dcp;
    private final DirectionGrid dg;
    private final MovesSettingsPanel msp;

    private final MovesPanel panel;

    /** Creation of a Move Builderbox
     * @param frame the main Builderbox that parents this box
     * @param panel the panel from which this box has been opened
     */
    public MovesBox(BuilderBox frame, MovesPanel panel) {
	super(frame, "New Move", true);
	setMinimumSize(new Dimension(700, 530));
       	setLocationRelativeTo(frame);
	setLayout(new BorderLayout());
	this.panel = panel;


	dcp = new DirectionChoicePanel(this);
	dg = new DirectionGrid();
	msp = new MovesSettingsPanel(this);

	JPanel p = new JPanel();
	JButton but = new JButton("Add New Move");
	p.setLayout(new BorderLayout());
	p.add(but);

	but.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    addMoves();
		}
	    });

	add(dcp, BorderLayout.WEST);
	add(dg, BorderLayout.CENTER);
	add(msp, BorderLayout.EAST);
	add(p, BorderLayout.SOUTH);

    }

    /** Enable or disable this panel's direction grid
     * @param a true if it must be enabled, false otherwise
     */
    public void setAbled(boolean a) {
	dg.setAbled(a);
	dg.fill(dcp.getDirection());
    }

    /** Set this panel's direction grid movement policy
     * @param w true if it is normal, false if it's a jump (like a knight)
     */
    public void setWalking(boolean w) {
	dg.toggle(w);
	dg.fill(dcp.getDirection());
    }

    /** Launch this panel, ie (re)init all its contained elements */
    public void launch() {
	dg.init();
	dcp.init();
	msp.init();
	setVisible(true);
    }

    private void addMoves() {

	char type = msp.getTypeValue();
	char range = msp.getRangeValue();
	int step = msp.getStepValue();
	int offset = msp.getOffsetValue();

	if(dcp.isAdvancedDirection()) {
	    for(int i = 0 ; i < 8 ; i++)
		if(dg.isButtonActivated(i))
		    panel.addMove(type,(char)('0'+(i<4?i:i+1)), range, step, offset);
	} else
	    panel.addMove(type, dcp.getDirection(), range, step, offset);

	dispose();
    }


}

