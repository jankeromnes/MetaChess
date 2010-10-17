package metachess.library;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

/** Class of the Pieces List
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PiecesList extends JList implements ResourceList {

	private static final long serialVersionUID = 1L;
	private final PieceSelectRenderer renderer;
    private final JScrollPane scroller;

    /** Creation of a pieces list */
    public PiecesList() {
	super();
       	setOpaque(false);
	setLayoutOrientation(JList.VERTICAL_WRAP);
	setVisibleRowCount(-1);
	renderer = new PieceSelectRenderer(true);
	setCellRenderer(renderer);

	scroller = new JScrollPane(this);
	scroller.setOpaque(false);
	scroller.setPreferredSize(new Dimension(200,120));

    }

    public void init() {
	setListData(Resource.PIECES.getFiles());
	setSelectedIndex(0); // If NullPointerException occurs here, check metachess.library.Resource:getFiles()
	setEnabled(true);
    }


    @Override
	public String getName() {
	String pieceName = getSelectedValue().toString();
	return pieceName.substring(0, pieceName.lastIndexOf('.'));
    }

    /** Set the pieces in the renderer white or black
     * @param white whether the pieces are white
     */
    public void setWhite(boolean white) {
	renderer.setWhite(white);
	repaint();
    }

    public Component getComponent() {
	return scroller;
    }

}

