package metachess.builder;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class PanelTitle extends JLabel {

	private static final long serialVersionUID = 1L;
	public PanelTitle(String text) {
	super("<html><h3><u>"+text+"</u></h3></html>");
	setAlignmentX(JComponent.CENTER_ALIGNMENT);
	setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
    }


}

