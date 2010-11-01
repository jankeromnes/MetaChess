package metachess.library;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Class of the piece's image selection JList's cell renderer
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PieceImageSelectRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	String text = value.toString();

	renderer.setIcon(PieceImages.getScaledImageFromPath(Resource.PIECE_IMAGES.getPath(false)+text, 20));

	text = text.substring(1,text.lastIndexOf('.'));
	renderer.setText(text);
	renderer.setOpaque(isSelected);	

	return renderer;

    }

}