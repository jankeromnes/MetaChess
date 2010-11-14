package metachess.dialog;

import javax.swing.JOptionPane;

/** Class of the Confirm Dialog Box
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public class ConfirmDialog {

    private String message;
    private boolean yesNo;
    private String title;

    /** Create a confirm dialog box
     * @param title the title of this box
     * @param question the question that will be shown on this box
     * @param yesno whether this is a yes-no question (or a OK-Cancel question)
     */
    public ConfirmDialog(String title, String question, boolean yesno) {
	this.title = title;
	message = question;
	yesNo = yesno;
    }

    /** Show this dialog box and return the user's choice
     * @return true if OK has been pressed
     */
    public boolean launch() {
	return JOptionPane.showConfirmDialog(null,
					     message,
					     title,
					     yesNo? JOptionPane.YES_NO_OPTION: JOptionPane.OK_CANCEL_OPTION,
					     JOptionPane.INFORMATION_MESSAGE)
	    == (yesNo ? JOptionPane.YES_OPTION: JOptionPane.OK_OPTION);
    }

}

