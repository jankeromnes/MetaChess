package metachess.dialog;

import javax.swing.JOptionPane;

import metachess.exception.MetachessException;

/** Class of the Error Dialog Box
 * @author Agbeladem
 * @version 0.8.5
 */
public class ErrorBox {

    /** Show a critical error dialog box
     * @param e the exception that was encountered
     */
    public ErrorBox(MetachessException e) {
	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	//System.exit(1);
    }

}
