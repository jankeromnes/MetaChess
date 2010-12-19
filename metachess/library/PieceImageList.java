package metachess.library;

import java.awt.Component;
import java.io.File;

import javax.swing.JList;
import javax.swing.JScrollPane;

/** Class of the Pieces Images graphical list
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PieceImageList extends JList implements ResourceList {

    private static final long serialVersionUID = 1L;

    /** Creation of a pieces images list */
    public PieceImageList() {
	super();
	setOpaque(false);
	setCellRenderer(new PieceImageSelectRenderer());
    }

    /** (Re)initialize this list */
    public void init() {
	setListData(Resource.PIECE_IMAGES.getFiles());
	setSelectedIndex(0);
    }

    /** Set this list's selected image
     * @param v the new value (file path) of the selected image
     */
    public void setValue(String v) {
	setSelectedValue((Object)(new File(v).getName()), true);
    }

    @Override
    public String getName() {
	return getSelectedValue().toString();
    }

    @Override
    public Component getComponent() {
	return new JScrollPane(this);
    }

}

