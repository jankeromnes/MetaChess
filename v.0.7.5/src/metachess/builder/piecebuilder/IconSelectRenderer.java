package metachess.builder.piecebuilder;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import metachess.library.PiecesImages;
import metachess.library.Resource;

public class IconSelectRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	String text = value.toString();

	renderer.setIcon(PiecesImages.getScaledImageFromPath(Resource.PIECES_IMAGES.getPath(false)+text, 20));

	text = text.substring(1,text.lastIndexOf('.'));
	renderer.setText(text);
	renderer.setOpaque(isSelected);	

	return renderer;

    }

}