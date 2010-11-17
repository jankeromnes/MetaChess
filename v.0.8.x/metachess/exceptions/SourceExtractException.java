package metachess.exceptions;


/** Exception while extracting from a source
 * @author Jan (7DD)
 * @version 0.8.4
 */
public class SourceExtractException extends ExtractException {

    /** Create a file load exception
     * @param file the name of the file
     */
    public SourceExtractException(String source) {
	super("Could not extract source file '"+source+"'");
    }

}
