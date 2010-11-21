package metachess.exceptions;

/** Exception that may occur during the data extraction process
 * @author Jan (7DD)
 * @version 0.8.5
 */
public class FilesExtractException extends ExtractException {

    /** Create an extract exception
     * @param msg the explanation about the exception
     */
    public FilesExtractException(String msg) {
	super("(Files) >> "+msg+" !");
    }

}
