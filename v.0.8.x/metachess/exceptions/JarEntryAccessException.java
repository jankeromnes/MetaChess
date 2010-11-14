package metachess.exceptions;

import metachess.library.Resource;

/** Exception for file access problems
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public class JarEntryAccessException extends ExtractException {

    /** Create a file load exception
     * @param file the name of the file
     */
    public JarEntryAccessException(String file) {
	super("Cannot reach Jar entry '"+file+"'");
    }

}
