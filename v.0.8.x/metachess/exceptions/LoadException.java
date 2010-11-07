package metachess.exceptions;

/** Exception for any problems that may occur from the loader
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public abstract class LoadException extends MetachessException {

    /** Create a load exception
     * @param msg the explanation about the exception
     */
    public LoadException(String msg) {
	super("Load Exception >> "+msg+" !");
    }

    /** Create a file load exception
     * @param msg the explanation about the exception
     * @param file the name of the file where the problem was found
     */
    public LoadException(String msg, String file) {
	super("Load Exception in file '"+file+"' >> "+msg+" !");
    }


}
