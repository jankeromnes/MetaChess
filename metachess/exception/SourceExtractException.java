package metachess.exception;


/** Exception while extracting from a source
 * @author Jan (7DD)
 * @version 0.8.4
 */
public class SourceExtractException extends JarExtractException {

	private static final long serialVersionUID = 1L;

	/** Create a file load exception
     * @param file the name of the file
     */
    public SourceExtractException(String source) {
	super("Could not extract source file '"+source+"'");
    }

}
