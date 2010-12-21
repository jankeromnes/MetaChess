package metachess.builder.setupbuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import metachess.library.PieceList;
import metachess.library.PieceListModel;

/** Class of the Promotion Box for the Setup Builder
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class PromotionListBox extends JDialog {

	private static final long serialVersionUID = 1L;
	private final PieceListModel model;
    private final PieceList list;
    private final JButton validate;

    /** Create a promotion box for the setup builder
     * @param m the piece list model of the promotion pieces' list
     */
    public PromotionListBox(PieceListModel m) {
	setTitle("Add a promotion piece");
	model = m;

	list = new PieceList();
	list.init();

	validate = new JButton("Add promotion piece");
	validate.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    model.add(list.getName());
		    setVisible(false);
		}
	    });

	setPreferredSize(new Dimension(250, 350));
	setLayout(new BorderLayout());
	add(new JScrollPane(list));
	add(validate, BorderLayout.SOUTH);
	pack();
    }

    /** Launch this box */
    public void launch() {
	setVisible(true);
    }

}

