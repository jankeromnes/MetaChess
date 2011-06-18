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
import metachess.model.PieceBehaviour;

/** Class of the Moves Panel in the Piece Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public class MovesPanel extends JPanel implements PieceBehaviour {

    private static final long serialVersionUID = 1L;
    private final JCheckBox s_joker;
    private final JCheckBox s_rook;
    private final JCheckBox s_king;
    private final JCheckBox s_pawn;

    private final PieceBuilderBox parent;
    private final JList list;
    private final MovesListModel model;
    private final MovesBox dialog;

    /** Creation of a Piece Moves Panel
     * @param frame the main Builderbox parenting this panel
     * @param pbb the Piece Builderbox to which this panel belongs
     */
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

    /** Add a non-special move in this panel's list, in the MCP format
     * @param type the type of the move's policy, as an MCP character
     * @param dir the direction of the move, as an MCP character
     * @param range the range of the move, as an MCP character
     * @param step the step for the move
     * @param offset the step for the move
     */
    public void addMove(char type, char dir, char range, int step, int offset) {
	model.add(new MoveType(type, dir, range, step, offset));
	update();
    }

    /** Update the authorized moves in the Piece Builderbox */
    public void update() {
	ArrayList<MoveType> a = new ArrayList<MoveType>(model.getMoves());
	parent.update(a);
    }

    /** Replace the moves in this panel by a given list
     * @param moves the new list of non-special moves shown in this panel's moves list
     */
    public void setMoves(ArrayList<MoveType> moves) {
	model.setMoves(moves);
	update();
    }



    // SETTERS

    @Override
    public void setJoker(boolean b) { s_joker.setSelected(b); }
    @Override
    public void setRook(boolean b) {	s_rook.setSelected(b); }
    @Override
    public void setKing(boolean b) {	s_king.setSelected(b); }
    @Override
    public void setPawn(boolean b) {	s_pawn.setSelected(b); }

    // GETTERS

    @Override
    public boolean isJoker() { return s_joker.isSelected(); }
    @Override
    public boolean isRook() { return s_rook.isSelected(); }
    @Override
    public boolean isKing() { return s_king.isSelected(); }
    @Override
    public boolean isPawn() { return s_pawn.isSelected(); }

}




