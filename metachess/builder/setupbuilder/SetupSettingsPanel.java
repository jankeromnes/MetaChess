package metachess.builder.setupbuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import metachess.builder.PanelTitle;
import metachess.library.PieceList;
import metachess.library.PieceListModel;

/** Class of the Settings Panel in the Setup Builderbox
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class SetupSettingsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final SetupBuilderBox parent;

    private final JSlider widthSlider;
    private final JSlider heightSlider;

    private final JButton addPiece;
    private final JButton removePiece;
    private final PieceList promotions;
    private final PieceListModel model;
    private final PromotionListBox dialog;

    private final JButton apply;

    /** Creation of a Setup Settings Panel 
     * @param box the Setup Builderbox to which this panel belongs
     */
    public SetupSettingsPanel(SetupBuilderBox box) {
	super();

	parent = box;



	// DIMENSIONS

	widthSlider = new JSlider(1, 16, 8);
	widthSlider.setMinorTickSpacing(1);
	widthSlider.setMajorTickSpacing(3);
	widthSlider.setPaintTicks(true);
	widthSlider.setPaintLabels(true);
	widthSlider.setPaintTrack(true);
	widthSlider.setSnapToTicks(true);


	heightSlider = new JSlider(1, 16, 8);
	heightSlider.setMinorTickSpacing(1);
	heightSlider.setMajorTickSpacing(3);
	heightSlider.setPaintTicks(true);
	heightSlider.setPaintLabels(true);
	heightSlider.setPaintTrack(true);
	heightSlider.setSnapToTicks(true);

	apply = new JButton("Apply Changes");
	apply.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    parent.setBoardDimensions(widthSlider.getValue(), heightSlider.getValue());
		}
	    });





	// PROMOTION

	promotions = new PieceList();
	model = promotions.getModel();

	dialog = new PromotionListBox(model);
	addPiece = new JButton("Add");
	addPiece.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    dialog.launch();
		}
	    });
	removePiece = new JButton("Remove");
	removePiece.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if(! model.getPieces().isEmpty())
			model.remove(promotions.getName());
		}
	    });






	// PROMOTION PANEL

	JScrollPane pane = new JScrollPane(promotions);
	pane.setPreferredSize(new Dimension(200, 180));
	JPanel butsPanel = new JPanel();
	butsPanel.setLayout(new BoxLayout(butsPanel, BoxLayout.X_AXIS));
	butsPanel.add(Box.createHorizontalGlue());
	butsPanel.add(addPiece);
	butsPanel.add(Box.createHorizontalGlue());
	butsPanel.add(removePiece);
	JPanel promotionPanel = new JPanel();
	promotionPanel.setLayout(new BorderLayout());
	promotionPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
	promotionPanel.add(butsPanel, BorderLayout.NORTH);
	promotionPanel.add(pane, BorderLayout.CENTER);
	promotionPanel.setMaximumSize(new Dimension(200, 200));






	// ADD

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	add(new PanelTitle("Board's width"));
	add(widthSlider);
	add(new PanelTitle("Board's height"));
	add(heightSlider);

	add(Box.createVerticalStrut(10));
	add(apply);

	add(Box.createVerticalGlue());
	add(new PanelTitle("Promotion Pieces"));
	add(promotionPanel);
	add(Box.createVerticalGlue());

    }

    /** Set the dimensions of the editable board
     * @param i the board's number of columns
     * @param j the board's number of rows
     */
    public void setBoardDimensions(int i, int j) {
	widthSlider.setValue(i);
	heightSlider.setValue(j);
    }

    /** Get the piece list model used for the promotion
     * @return the model used for setting all the pieces into which a pawn can be promoted
     */
    public PieceListModel getPromotionListModel() {
	return model;
    }

}