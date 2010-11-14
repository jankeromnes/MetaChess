package metachess.exceptions;

import metachess.library.Resource;

/** Exception while extracting to a destination
 * @author Jan (7DD)
 * @version 0.8.4
 */
public class DestinationExtractException extends ExtractException {

    /** Create a file load exception
     * @param file the name of the file
     */
    public DestinationExtractException(String destination) {
	super("Could not extract to destination file '"+destination+"'");
    }

}
