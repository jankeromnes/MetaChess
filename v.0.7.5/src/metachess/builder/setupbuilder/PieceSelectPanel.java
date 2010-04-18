package metachess.builder.setupbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import metachess.library.Colour;
import metachess.library.Pieces;
import metachess.library.Resource;

public class PieceSelectPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SetupBuilderBox parent;
    private final ListSelectionListener listEv;

    private final JList images;
    private final JRadioButton eraser;
    private final JRadioButton piece;
    private final JButton whiteButton;
    private final JButton blackButton;

    private boolean white = true;

    public PieceSelectPanel(SetupBuilderBox arg) {
	super();
	parent = arg;


	// RADIO BUTTONS

	ButtonGroup bg = new ButtonGroup();
	eraser = new JRadioButton("Eraser");
	piece = new JRadioButton("Add new Piece");
	ActionListener ev = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Object o = e.getSource();
		    boolean b = o == piece;
		    images.setEnabled(b);
		    changeTool(b);
		}

	    };
	eraser.addActionListener(ev);
	piece.addActionListener(ev);

	eraser.setAlignmentX(CENTER_ALIGNMENT);
	piece.setAlignmentX(CENTER_ALIGNMENT);

	bg.add(eraser);
	bg.add(piece);


	// PIECES LIST

	images = new JList();
	images.setOpaque(false);
	images.setLayoutOrientation(JList.VERTICAL_WRAP);
	images.setVisibleRowCount(-1);
	images.setCellRenderer(new PieceSelectRenderer(this));
	listEv = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    if(! e.getValueIsAdjusting() && images.isEnabled())
			changeTool(true);
		}
	    };
	images.addListSelectionListener(listEv);

	JScrollPane scroller = new JScrollPane(images);
	scroller.setOpaque(false);
	scroller.setPreferredSize(new Dimension(200,120));




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
			images.repaint();
		    }
		    changeTool(images.isEnabled());
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
	add(eraser);
	add(piece);
	add(scroller);
	add(buttons);

    }


    public void init() {
	piece.setSelected(true);
	images.removeListSelectionListener(listEv);
	images.setListData(Resource.PIECES.getFiles());
	images.setSelectedIndex(0); // If NullPointerException occurs here, check metachess.library.Resources:getFiles()
	images.setEnabled(true);
	changeTool(true);
	images.addListSelectionListener(listEv);
	white = true;
    }

    public boolean isWhite() {
	return white;
    }

    private void changeTool(boolean b) {
	if(b) {
	    String pieceName = images.getSelectedValue().toString();
	    pieceName = pieceName.substring(0, pieceName.lastIndexOf('.'));
	    parent.changeTool(Pieces.getPiece(pieceName, white));
	} else
	    parent.changeTool(null);
    }



}

