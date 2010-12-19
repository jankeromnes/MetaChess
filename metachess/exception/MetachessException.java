package metachess.exception;

/** Main class for all the exceptions of metachess
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public abstract class MetachessException extends Exception {

	private static final long serialVersionUID = 1L;

	/** Create a Metachess Exception
     * @param msg the message that will be shown if this exception is printed
     */
    public MetachessException(String msg) {
	super(msg+" !");
    }

}

