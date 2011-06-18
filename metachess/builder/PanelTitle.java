package metachess.builder;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/** Class of a Title Label as used in several panels of the Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */
public class PanelTitle extends JLabel {

	private static final long serialVersionUID = 1L;

    /** Creation of a Title Label
     * @param text the text that will be shown in this label
     */
       public PanelTitle(String text) {
	super("<html><h3><u>"+text+"</u></h3></html>");

	setAlignmentX(Component.CENTER_ALIGNMENT);
	setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
    }


}

