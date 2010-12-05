package metachess.library;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import java.util.ArrayList; 

/** Class of the Pieces List
 * @author Agbeladem (7DD)
 * @version 0.8.7
 */
public class PiecesList extends JList implements ResourceList {

    private static final long serialVersionUID = 1L;
    private final PieceSelectRenderer renderer;
    private final JScrollPane scroller;

    /** Creation of a default pieces list */
    public PiecesList() {
	this(22);
    }

    /** Creation of a pieces list
     * @param size the default size of the rendered piece image
     */
    public PiecesList(int size) {
	super();

       	setOpaque(false);

	setVisibleRowCount(-1);
	renderer = new PieceSelectRenderer(true, size);
	setCellRenderer(renderer);

	scroller = new JScrollPane(this);
	scroller.setOpaque(false);

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
	setListData(Resource.PIECES.getFiles());
	setSelectedIndex(0); // If NullPointerException occurs here, 
	                     // check metachess.library.Resource::getFiles()
	setEnabled(true);
    }

    /** (Re)initialize this list with the given pieces
     * @param list the list of the pieces this list will show
     */
    public void init(ArrayList<String> list) {
	assert list != null && list.size() > 0;
	setListData(list.toArray());
	setSelectedIndex(0);
	setEnabled(true);
    }

    public void setListData(Object[] o) {
	super.setListData(o);
    }

    @Override
    public String getName() {
	String pieceName = getSelectedValue().toString();
	return pieceName.substring(0, pieceName.lastIndexOf('.'));
    }


    @Override
    public Component getComponent() {
	return scroller;
    }
    
}

