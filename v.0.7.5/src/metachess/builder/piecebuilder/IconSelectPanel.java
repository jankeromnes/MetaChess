package metachess.builder.piecebuilder;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metachess.builder.PanelTitle;
import metachess.library.PiecesImages;
import metachess.library.PiecesImagesList;
import metachess.library.Resource;

public class IconSelectPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final PiecesImagesList images;
    private final PieceBuilderBox pbb;
    private final ListSelectionListener listEv;

    public IconSelectPanel(PieceBuilderBox arg) {
	super();
	pbb = arg;
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	images = new PiecesImagesList();

	listEv = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    if(! e.getValueIsAdjusting())
			pbb.changeIcon(Resource.PIECES_IMAGES.getPath(false)+images.getName());
		}
	    };
	images.addListSelectionListener(listEv);

	add(new PanelTitle("Model Icon"));
	add(images);

    }

    public void init() {
	images.removeListSelectionListener(listEv);
	images.init();
	images.addListSelectionListener(listEv);
    }


}

