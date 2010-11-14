package metachess.dialog;

import javax.swing.JOptionPane;

/** Class of the Message Dialog Box
 * @author Agbeladem
 * @version 0.8.5
 */
public class MessageDialog {

    /** Show a message dialog box
     * @param message the message to be shown
     */
    public MessageDialog(String message) {
	JOptionPane.showMessageDialog(null, message);
    }

}
