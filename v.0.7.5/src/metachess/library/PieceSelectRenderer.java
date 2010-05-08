package metachess.library;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Class of the piece selection Jlist's cell renderer
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PieceSelectRenderer implements ListCellRenderer {

    private boolean isWhite;

    /** Create a piece selection cell renderer
     * @param white whether the pieces should be white
     */
    public PieceSelectRenderer(boolean white) {
	setWhite(white);
    }

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	String text = value.toString();
	text = text.substring(0,text.lastIndexOf('.'));

	renderer.setIcon(PiecesImages.getScaledImage(text, isWhite, 20));
	renderer.setText(text);
	renderer.setOpaque(isSelected);	

	return renderer;

    }

    /** Set the pieces in the renderer white or black
     * @param white whether the pieces are white
     */
    public void setWhite(boolean white) {
	isWhite = white;
    }

}

