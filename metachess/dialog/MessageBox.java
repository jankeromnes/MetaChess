package metachess.dialog;

import javax.swing.JOptionPane;

/** Class of the Message Dialog Box
 * @author Agbeladem
 * @version 0.8.5
 */
public class MessageBox {

    /** Show a message dialog box
     * @param message the message to be shown
     */
    public MessageBox(String message) {
	JOptionPane.showMessageDialog(null, message);
    }

}
