package metachess.builder.setupbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metachess.library.Colour;
import metachess.library.Pieces;
import metachess.library.PiecesList;

/** Class of the tool selection panel
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class ToolSelectPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final SetupBuilderBox parent;
    private final ListSelectionListener listEv;

    private final PiecesList images;
    private final JRadioButton squareToggler;
    private final JRadioButton pieceEraser;
    private final JRadioButton piece;
    private final JButton whiteButton;
    private final JButton blackButton;

    private boolean white = true;

    /** Create a new tool selection panel
     * @param arg the Setup Builderbox
     */
    public ToolSelectPanel(SetupBuilderBox arg) {
	super();
	parent = arg;


	// RADIO BUTTONS

	ButtonGroup bg = new ButtonGroup();
	squareToggler = new JRadioButton("Square toggler");
	pieceEraser = new JRadioButton("Piece eraser");
	piece = new JRadioButton("Add new Piece");
	ActionListener ev = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Object o = e.getSource();
		    boolean b = o == piece;
		    images.setEnabled(b);
		    setTool(b? Tool.ADDING_PIECE : (o == pieceEraser ? Tool.ERASING_PIECE : Tool.TOGGLING_SQUARE) );
		}

	    };

	squareToggler.addActionListener(ev);
	pieceEraser.addActionListener(ev);
	piece.addActionListener(ev);

	squareToggler.setAlignmentX(CENTER_ALIGNMENT);
	pieceEraser.setAlignmentX(CENTER_ALIGNMENT);
	piece.setAlignmentX(CENTER_ALIGNMENT);

	bg.add(squareToggler);
	bg.add(pieceEraser);
	bg.add(piece);


	// PIECES LIST

	images = new PiecesList();

	listEv = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    if(! e.getValueIsAdjusting() && images.isEnabled())
			setTool(Tool.ADDING_PIECE);
		}
	    };
	images.addListSelectionListener(listEv);





	// BUTTONS

	blackButton = new JButton();
	blackButton.setBackground(Colour.BLACK.getColor());

	whiteButton = new JButton();
	whiteButton.setBackground(Colour.WHITE.getColor());

	Border space = BorderFactory.createEmptyBorder(0, 0, 50, 120);
	whiteButton.setBorder(BorderFactory.createCompoundBorder(whiteButton.getBorder(),space));
	blackButton.setBorder(BorderFactory.createCompoundBorder(blackButton.getBorder(), space));

	ActionListener colorEv = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Object o = e.getSource();
		    boolean b = o == whiteButton;
		    if(white ^ b) {
			white = b;
			images.setWhite(white);
		    }
		    if(images.isEnabled())
			setTool(Tool.ADDING_PIECE);
		}
	    };
	whiteButton.addActionListener(colorEv);
	blackButton.addActionListener(colorEv);



	JPanel buttons = new JPanel();
	buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
	buttons.add(whiteButton);
	buttons.add(blackButton);



	// INIT

	init();

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(squareToggler);
	add(pieceEraser);
	add(piece);
	add(images.getComponent());
	add(buttons);

    }

    /** Initialize the tool selection panel */
    public void init() {
	piece.setSelected(true);
	images.removeListSelectionListener(listEv);
	images.init();
	setTool(Tool.ADDING_PIECE);
	images.addListSelectionListener(listEv);
	white = true;
    }

    public boolean isWhite() {
	return white;
    }

    private void setTool(Tool t) {
	if(t == Tool.ADDING_PIECE)
	    parent.setPiece(Pieces.getPiece(images.getName(), white));
	parent.setTool(t);
    }


}


