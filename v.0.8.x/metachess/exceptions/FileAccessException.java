package metachess.exceptions;

import metachess.library.Resource;

/** Exception for file access problems
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public class FileAccessException extends LoadException {

    /** Create a file load exception
     * @param file the name of the file
     */
    public FileAccessException(String file) {
	super("Cannot reach file '"+file+"'\nof main data folder "+Resource.RESOURCES.getPath());
    }

}
