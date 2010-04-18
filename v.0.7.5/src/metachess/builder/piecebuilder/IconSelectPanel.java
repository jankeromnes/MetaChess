package metachess.builder.piecebuilder;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metachess.builder.PanelTitle;
import metachess.library.Resource;

public class IconSelectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JList images;
    private final PieceBuilderBox pbb;
    private final ListSelectionListener listEv;

    public IconSelectPanel(PieceBuilderBox arg) {
	super();
	pbb = arg;
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	images = new JList();
	images.setOpaque(false);
	images.setCellRenderer(new IconSelectRenderer());

	listEv = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    if(! e.getValueIsAdjusting())
			pbb.changeIcon(images.getSelectedValue().toString());
		}
	    };
	images.addListSelectionListener(listEv);

	add(new PanelTitle("Model Icon"));
	add(images);

    }

    public void init() {
	images.removeListSelectionListener(listEv);
	images.setListData(Resource.PIECES_IMAGES.getFiles());
	images.addListSelectionListener(listEv);
	images.setSelectedIndex(0);
    }


}

