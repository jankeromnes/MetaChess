package metachess.exceptions;


/** Global exception in extraction process
 * @author Jan (7DD)
 * @version 0.8.4
 */
public class GlobalExtractException extends ExtractException {

    /** Create a file load exception
     * @param file the name of the file
     */
    public GlobalExtractException(String source, String destination) {
	super("Could not extract from '"+source+"' to '"+destination+"'");
    }

}
