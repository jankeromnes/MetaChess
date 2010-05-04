package metachess.builder.setupbuilder;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import metachess.library.PiecesImages;

/** Class of the piece selection Jlist's cell renderer
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PieceSelectRenderer implements ListCellRenderer {

    private ToolSelectPanel parent;

    /** Create a piece selection cell renderer
     * @param arg the tool selection panel
     */
    public PieceSelectRenderer(ToolSelectPanel arg) {
	parent = arg;
    }

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	String text = value.toString();
	text = text.substring(0,text.lastIndexOf('.'));

	renderer.setIcon(PiecesImages.getScaledImage(text, parent.isWhite(), 20));
	renderer.setText(text);
	renderer.setOpaque(isSelected);	

	return renderer;

    }

}