package metachess.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;

import metachess.board.ChessBoard;
import metachess.library.PiecesList;

/** Class of the Promotion Box
 * @author Agbeladem (7DD)
 * @version 0.8.7
 */
public class PromotionBox extends JDialog {

	private static final long serialVersionUID = 1L;
	private final ChessBoard board;
    private final PiecesList list;

    /** Create a promotion box
     * @param f the window (most likely Game) to which this dialog is modal
     * @param cb the chessboard on which the promotion will be made
     */
    public PromotionBox(Frame f, ChessBoard cb) {
	super(f, "Choose a promotion piece");
	setLayout(new BorderLayout());

	board = cb;

	list = new PiecesList(30);
	Component scroll = list.getComponent();
	scroll.setPreferredSize(new Dimension(120, 220));

	JButton but = new JButton("Validate");
	but.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    board.validatePromotion((String)(list.getSelectedValue()));
		    setVisible(false);
		}
	    });

	add(scroll);
	add(but, BorderLayout.SOUTH);

	pack();

    }

    /** Launch this box with the given promotion choice
     * @param choice the choice as a list of promotion pieces names
     */ 
    public void launch(ArrayList<String> choice) {
	list.init(choice);
	setVisible(true);
    }


}
