package metachess.exception;

/** Exception for any wrong contents in the loaded files
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public class FileContentException extends LoadException {

	private static final long serialVersionUID = 1L;

	/** Create a file load exception
     * @param msg the explanation about the exception
     * @param file the name of the file where the problem was found
     */
    public FileContentException(String msg, String file) {
	super("Invalid data content :\n\t"+msg, file);
    }


}
