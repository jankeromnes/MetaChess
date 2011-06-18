package metachess.library;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;

/** Class of the Piece List
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class PieceList extends JList implements ResourceList {

    private static final long serialVersionUID = 1L;
    private final PieceSelectRenderer renderer;
    private final JScrollPane scroller;
    private final PieceListModel model;

    /** Creation of a default pieces list */
    public PieceList() {
	this(22);
    }

    /** Creation of a pieces list
     * @param size the default size of the rendered piece image
     */
    public PieceList(int size) {
	super();

       	setOpaque(false);

	setVisibleRowCount(-1);
	renderer = new PieceSelectRenderer(true, size);
	setCellRenderer(renderer);

	scroller = new JScrollPane(this);
	scroller.setOpaque(false);

	model = new PieceListModel();
	setModel(model);

    }

    /** Set the pieces in the renderer white or black
     * @param white whether the pieces are white
     */
    public void setWhite(boolean white) {
	renderer.setWhite(white);
	repaint();
    }

    @Override
    public void init() {
	model.load(Resource.PIECES.getFiles());
	setSelectedIndex(0); // If NullPointerException occurs here, 
	                     // check metachess.library.Resource::getFiles()
	setEnabled(true);
    }

    /** (Re)initialize this list with the given pieces
     * @param list the list of the pieces this list will show
     */
    public void init(ArrayList<String> list) {
	assert list != null && list.size() > 0;
	model.load(list);
	setSelectedIndex(0);
	setEnabled(true);
    }

    @Override
    public String getName() {
	return getSelectedValue().toString();
    }

    @Override
    public Component getComponent() {
	return scroller;
    }
    
    @Override
    public PieceListModel getModel() {
	return (PieceListModel) model;
    }

}

