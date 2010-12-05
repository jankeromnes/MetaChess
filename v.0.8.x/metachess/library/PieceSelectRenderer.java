package metachess.library;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Class of the piece selection Jlist's cell renderer
 * @author Agbeladem (7DD)
 * @version 0.8.7
 */
public class PieceSelectRenderer implements ListCellRenderer {

    private boolean isWhite;
    private int size;

    /** Create a piece selection cell renderer */
    public PieceSelectRenderer() {
	this(true, 20);
    }
    /** Create a piece selection cell renderer
     * @param white whether the pieces should be white
     */
    public PieceSelectRenderer(boolean white) {
	this(white, 20);
    }

    /** Create a piece selection cell renderer
     * @param white whether the pieces should be white
     * @param size the dimension of the piece image
     */
    public PieceSelectRenderer(boolean white, int size) {
	this.size = size;
	setWhite(white);
    }

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	String text = value.toString();

	// Just in case the name of the piece ends with ".mcp"
	int n = text.lastIndexOf('.');
	if(n != -1) text = text.substring(0, n);

	renderer.setIcon(PieceImages.getScaledImage(text, isWhite, size));
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

