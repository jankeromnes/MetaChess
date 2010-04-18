package metachess.builder.setupbuilder;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import metachess.library.PiecesImages;

public class PieceSelectRenderer implements ListCellRenderer {

    PieceSelectPanel parent;

    public PieceSelectRenderer(PieceSelectPanel arg) {
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