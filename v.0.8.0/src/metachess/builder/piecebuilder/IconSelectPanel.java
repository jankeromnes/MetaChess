package metachess.builder.piecebuilder;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metachess.builder.PanelTitle;
import metachess.library.PieceImageList;

/** Class of the Icon Selection Panel in the Piece Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
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
			pbb.changeIcon(images.getName());
		}
	    };

	add(new PanelTitle("Model Icon"));
	add(images);

    }

    /** (Re)initialize this panel */
    public void init() {
	images.removeListSelectionListener(listEv);
	images.init();
	images.addListSelectionListener(listEv);
	pbb.changeIcon(images.getName());
    }


}

