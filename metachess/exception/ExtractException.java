package metachess.exception;

/** Exception that may occur during the data extraction process
 * @author Jan (7DD)
 * @version 0.8.5
 */
public class ExtractException extends MetachessException {

	private static final long serialVersionUID = 1L;

	/** Create an extract exception
     * @param msg the explanation about the exception
     */
    public ExtractException(String msg) {
	super("Data Extraction Exception "+msg+" !");
    }

}
