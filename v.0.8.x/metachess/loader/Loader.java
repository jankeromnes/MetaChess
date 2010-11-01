package metachess.loader;

/** Interface of a resource loader (as a singleton)
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public interface Loader {

    /** Load the resource contained in a specified file.
     * @param file the file which data this loader will extract
     */
    public void loadResource(String file);

}

