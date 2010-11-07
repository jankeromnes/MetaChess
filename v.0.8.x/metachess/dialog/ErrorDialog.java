package metachess.dialog;

import javax.swing.JOptionPane;

import metachess.exceptions.MetachessException;

/** Class of the Error Dialog Box
 * @author Agbeladem
 * @version 0.8.4
 */
public class ErrorDialog {

    /** Show a critical error dialog box and exit the program
     * @param the exception that was encountered
     */
    public ErrorDialog(MetachessException e) {
	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	System.exit(1);
    }

}
