package metachess.logger;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import metachess.library.Colour;

public class LogRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();


    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	if(!isSelected)
	    renderer.setBackground(((index % 2 == 0)? Colour.WHITE_BG : Colour.BLACK_BG).getColor());
	renderer.setText((String)value);
	return renderer;

    }


}



