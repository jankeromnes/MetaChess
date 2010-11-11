package metachess.logger;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import metachess.library.Colour;

/** Class of the renderer in the moves history list
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public class LogRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	if(!isSelected)
	    renderer.setBackground(((index % 2 == 0)? Colour.WHITE_BG : Colour.BLACK_BG).getColor());
	String text = (index%2==0?Integer.toString(index/2+1)+'.': "..." )+' '+(String)value;
	renderer.setText(text);
	renderer.setHorizontalAlignment(JLabel.CENTER);
	return renderer;

    }


}



