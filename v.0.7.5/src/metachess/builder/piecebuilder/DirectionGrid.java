package metachess.builder.piecebuilder;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import metachess.game.MoveType;
import metachess.library.Colour;

public class DirectionGrid extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JButton[][] squares = new JButton[5][5];
    private final JButton[] walks = new JButton[8];
    private final JButton[] jumps = new JButton[8];
    private boolean walking;
    private boolean able;

    public DirectionGrid() {
	super();
	setLayout(new GridLayout(5,5));
	
	for(int i = 0; i < 5 ; i ++)
	    for(int j = 0 ; j < 5 ; j++) {
		JButton b = new JButton();
		b.setBackground(Colour.WHITE.getColor());
		b.setEnabled(false);
		squares[j][i] = b;
		add(b);
	    }

	squares[2][2].setBackground(Colour.SALMON.getColor());

	walks[0] = squares[1][3];
	walks[1] = squares[2][3];
	walks[2] = squares[3][3];
	walks[3] = squares[1][2];
	walks[4] = squares[3][2];
	walks[5] = squares[1][1];
	walks[6] = squares[2][1];
	walks[7] = squares[3][1];

	jumps[0] = squares[1][4];
	jumps[1] = squares[4][3];
	jumps[2] = squares[3][4];
	jumps[3] = squares[0][3];
	jumps[4] = squares[4][1];
	jumps[5] = squares[1][0];
	jumps[6] = squares[0][1];
	jumps[7] = squares[3][0];

	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		    Color d = Colour.BLUE.getColor();
		    Color l = Colour.DARK_BLUE.getColor();
		    JButton but = (JButton)e.getSource();
		    but.setBackground(but.getBackground().equals(d)? l: d);

		}
	    };

	for(int i = 0 ; i < 8 ; i++) {
	    walks[i].addActionListener(listener);
	    jumps[i].addActionListener(listener);
	}

	init();

    }
    
    public void fill(char c) {

	Color activated = (able?Colour.DARK_BLUE.getColor()
			   :Colour.BLUE.getColor());
	Color desactivated = (able?Colour.BLUE.getColor()
			      :Colour.WHITE.getColor());

	boolean[] connect = MoveType.getDirectionConnectivityFromComboDirection(c);
	JButton[] right = walking ? walks : jumps;

	for(int i = 0 ; i < 8 ; i++)
	    right[i].setBackground(connect[i]?activated:desactivated);

    }

    
    public void toggle(boolean walking) {
	this.walking = walking;
	Color snow = Colour.WHITE.getColor();
	boolean walkEnabled = walking && able;
	boolean jumpEnabled = !walking && able;
	for(int i = 0 ; i < 8 ; i++) {
	    walks[i].setEnabled(walkEnabled);
	    jumps[i].setEnabled(jumpEnabled);
	    walks[i].setBackground(snow);
	    jumps[i].setBackground(snow);
	}


    }

    public void setAbled(boolean able) {
	this.able = able;
	toggle(walking);
    }

    public void init() {
	able = false;
	toggle(true);
	fill('A');	
    }

    public boolean isButtonActivated(int i) {
	assert(i >= 0 && i < 8): "DirectionGrid : bad direction";
	assert(able): "DirectionGrid : Grid not activated";
	return (walking?walks[i]:jumps[i]).getBackground().equals(Colour.DARK_BLUE.getColor());
    }

}

