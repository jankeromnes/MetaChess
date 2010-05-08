package metachess.builder.piecebuilder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import metachess.builder.BuilderBox;
import metachess.builder.PanelTitle;
import metachess.game.MoveType;

public class MovesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JCheckBox s_joker;
    private final JCheckBox s_rook;
    private final JCheckBox s_king;
    private final JCheckBox s_pawn;

    private final PieceBuilderBox parent;
    private final JList list;
    private final MovesListModel model;
    private final MovesBox dialog;

    public MovesPanel(BuilderBox frame, PieceBuilderBox pbb) {
	super();
	parent = pbb;
	dialog = new MovesBox(frame, this);


	// Checkboxes
	s_joker = new JCheckBox("Joker");
	s_rook = new JCheckBox("Rook");
	s_king = new JCheckBox("King");
	s_pawn = new JCheckBox("Pawn");
	JPanel checks = new JPanel();
	checks.setLayout(new GridLayout(4,1));
	checks.add(s_joker);
	checks.add(s_rook);
	checks.add(s_king);
	checks.add(s_pawn);

	// List
	model = new MovesListModel();
	list = new JList(model);
	JScrollPane listPane = new JScrollPane(list);
	JPanel listPanel = new JPanel();
	listPane.setPreferredSize(new Dimension(100,70));
	listPanel.setPreferredSize(new Dimension(100,80));
	listPanel.add(listPane);

	// Buttons
	JButton delete = new JButton("Delete Move");
	delete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    int i = list.getSelectedIndex();
		    int n = list.getLastVisibleIndex();
		    if(i != -1 && n != -1) {
			model.delete(i);
			update();
			if(i == n)
			    list.setSelectedIndex(i-1);
		    }
		}
	    });

	JButton addNew = new JButton("Add new Move");
	addNew.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    dialog.launch();
		}
	    });
	JPanel buttons = new JPanel();
	buttons.setLayout(new GridLayout(2,1));
	buttons.add(addNew);
	buttons.add(delete);

	// Add
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(new PanelTitle("Special Power"));
	add(checks);
	add(new PanelTitle("List of the moves"));
	add(buttons);
	add(listPanel);
	add(Box.createVerticalGlue());

    }

    public void addMove(char type, char dir, char range) {
	model.add(new MoveType(type, dir, range, 1, 0));
	update();
    }

    public void update() {
	ArrayList<MoveType> a = new ArrayList<MoveType>(model.getMoves());
	parent.update(a);
    }

    public void setMoves(ArrayList<MoveType> moves) {
	model.setMoves(moves);
	update();
    }

    

    public boolean isJoker() { return s_joker.isSelected(); }
    public boolean isRook() { return s_rook.isSelected(); }
    public boolean isKing() { return s_king.isSelected(); }
    public boolean isPawn() { return s_pawn.isSelected(); }

}




