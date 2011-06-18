package metachess.panel.logger;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import metachess.library.Colour;

/** Class of the renderer in the moves history list
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class LogRenderer implements ListCellRenderer {

    private static final Dimension size = new Dimension(230, 16);
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	renderer.setPreferredSize(size);
	if(!isSelected)
	    renderer.setBackground(((index % 2 == 0)? Colour.WHITE_BG : Colour.BLACK_BG).getColor());
	String text = (index%2==0?Integer.toString(index/2+1)+'.': "..." )+' '+(String)value;
	renderer.setText(text);
	renderer.setHorizontalAlignment(JLabel.CENTER);
	return renderer;

    }

    /** Get the dimension of one renderer component
     * @return the preferred size of the labels rendered by this renderer
     */
    public static Dimension getSize() {
	return size;
    }

}


