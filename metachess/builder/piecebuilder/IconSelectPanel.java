package metachess.builder.piecebuilder;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metachess.builder.PanelTitle;
import metachess.library.PieceImageList;
import metachess.library.PieceImages;
import metachess.library.Resource;

/** Class of the Icon Selection Panel in the Piece Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public class IconSelectPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final PieceImageList images;
    private final PieceBuilderBox pbb;
    private final ListSelectionListener listEv;

    /** Creation of a Piece Icon Selection Panel
     * @param arg the Piece Builderbox to which this panel belongs
     */
    public IconSelectPanel(PieceBuilderBox arg) {
	super();
	pbb = arg;
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	images = new PieceImageList();

	listEv = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    if(! e.getValueIsAdjusting())
			changeIcon(images.getName());
		}
	    };

	add(new PanelTitle("Model Icon"));
	add(new JScrollPane(images));

    }

    /** (Re)initialize this panel */
    public void init() {
	images.removeListSelectionListener(listEv);
	images.init();
	images.addListSelectionListener(listEv);
	images.setValue(PieceImages.getImage("metamorph", true));
	changeIcon(images.getName());
    }

    /** Change this panel's selected icon
     * @param name the name of the icon
     */
    public void changeSelectedIcon(String name) {
	images.setValue(PieceImages.getImage(name) );
    }

    private void changeIcon(String name) {
	pbb.changeIcon(Resource.PIECE_IMAGES.getPath() + name);
    }

}

