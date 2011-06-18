package metachess.exception;

/** Exception for resource writing problems
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public class WriteException extends MetachessException {

	private static final long serialVersionUID = 1L;

	/** Create a writing exception
     * @param folder the folder where the problem occured
     */
    public WriteException(String folder) {
	super("Write Exception >> Cannot write to destination folder "+folder);
    }


}
