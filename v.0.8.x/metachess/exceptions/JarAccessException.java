package metachess.exceptions;


/** Exception for file access problems
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public class JarAccessException extends ExtractException {

    /** Create a file load exception
     * @param file the name of the file
     */
    public JarAccessException(String file) {
	super("Cannot reach Jar file '"+file+"'");
    }

}
