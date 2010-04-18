package metachess.builder.piecebuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import metachess.builder.BuilderBox;

public class MovesBox extends JDialog {

	private static final long serialVersionUID = 1L;
	private final DirectionChoicePanel dcp;
    private final DirectionGrid dg;
    private final MovesSettingPanel msp;

    private final MovesPanel panel;

    public MovesBox(BuilderBox frame, MovesPanel panel) {
	super(frame, "New Move", true);
	setMinimumSize(new Dimension(700, 400));
       	setLocationRelativeTo(frame);
	setLayout(new BorderLayout());
	this.panel = panel;


	dcp = new DirectionChoicePanel(this);
	dg = new DirectionGrid();
	msp = new MovesSettingPanel(this);

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

    public void setAbled(boolean a) {
	dg.setAbled(a);
	dg.fill(dcp.getDirection());
    }

    public void setWalking(boolean w) {
	dg.toggle(w);
	dg.fill(dcp.getDirection());
    }


    public void launch() {

	dg.init();
	dcp.init();
	msp.init();
	setVisible(true);
    }

    private void addMoves() {

	char type = msp.getTypeValue();
	char range = msp.getRangeValue();


	if(dcp.isAdvancedDirection()) {
	    for(int i = 0 ; i < 8 ; i++)
		if(dg.isButtonActivated(i))
		    panel.addMove(type,(char)('0'+(i<4?i:i+1)), range);
	} else
	    panel.addMove(type, dcp.getDirection(), range);

	dispose();
    }


}

