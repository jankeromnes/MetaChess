package metachess.library;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JScrollPane;

/** Class of the Pieces Images List
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PiecesImagesList extends JList implements ResourceList {

	private static final long serialVersionUID = 1L;

	/** Creation of a pieces images list */
    public PiecesImagesList() {
	super();
	setOpaque(false);
	setCellRenderer(new PieceImageSelectRenderer());
    }

    public void init() {
	setListData(Resource.PIECES_IMAGES.getFiles());
	setSelectedIndex(0);
    }

    public String getName() {
	return getSelectedValue().toString();
    }

    public Component getComponent() {
	return new JScrollPane(this);
    }

}

