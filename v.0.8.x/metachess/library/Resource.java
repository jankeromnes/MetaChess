package metachess.library;

import java.io.File;
import java.io.FilenameFilter;

/** Enum of Resources
 * @author Agbeladem (7DD)
 * @version 0.8.3
 */
public enum Resource {

    RESOURCES(""),
	PIECE_IMAGES("images/pieces","^W.+", "png", "pieceImage", new PieceImageList()),
	PIECES("pieces", "^.+", "mcp", "piece", new PiecesList()),
	SETUPS("setups", "^.+", "mcs","setup", new SetupList());

    private static String folder = null;
    
    private static String getDataFolder() {
		if(folder == null) {
		    String folder = System.getenv("appdata");
		    if(folder != null) folder += File.separator + "MetaChess" + File.separator; 
		    else folder = System.getProperty("user.home") + File.separator + ".metachess" + File.separator;
		    Resource.folder = folder;
		}
	return folder;
    }

    private FilenameFilter filter = new FilenameFilter() {
	    public boolean accept(File f, String name) {
		return new File(f.toString()+'/'+name).isFile() && name.matches(file);
	    }
	};

    private String link;
    private String file;
    private String name;
    private String extension;
    private ResourceList defaultList;

    /** Create new Resource folder
     * @param folder the folder
     */
    private Resource(String folder) {
    	this(folder, ".+");
    }

    /** Create new Resource folder
     * @param folder the folder
     * @param regex Regular Expression of the Resource files  
     */
    private Resource(String folder, String regex) {
		link = folder;
		file = regex;
    }

    /** Create new Resource folder
     * @param folder the folder
     * @param regex Regular Expression of the Resource files
     * @param ext extension of the Resource files
     * @param itemName the name of the Resource folder's content
     */
    private Resource(String folder, String regex, String ext, String itemName, ResourceList list) {
	this(folder, regex+"\\."+ext+'$');
	extension = ext;
	name = itemName;
	defaultList = list;
    }

    /** Get absolute File for this resource
     * @return the absolute File
     */
    public File getFile() {
	return new File(getPath(false));
    }

    /** Get File for this resource 
     * @param relative whether the path is relative or not.
     * If it is, refers to the path inside the jar's resources folder
     * Otherwise, it refers to the application data folder
     * @return the File
     */
    public File getFile(boolean relative) {
	return new File(getPath(relative));
    }
    
    

    /** Get the Resource file's basic extension */
    public String getExtension() {
	return extension;
    }

    /** Get the Resource folder's name
     * @return the name of the Resource folder's content
     */
    public String getName() {
	return name;
    }

    /** Get absolute Path of the Resource folder 
     * @return the String of the path*/
    public String getPath() {
	return getPath(false);
    }

    /** Get Path of the Resouce folder 
     * @param relative whether the path is relative or not.
     * If it is, refers to the path inside the jar's resources folder
     * Otherwise, it refers to the application data folder
     * @return the String of the path
     */
    public String getPath(boolean relative) {
	return (relative? "resources/" : getDataFolder()) + link;
    }


    /** List all the files in the Resource folder
     * @return an array of File
     * 
     * IMPORTANT NOTE : For Eclipse users, don't forget to set the
     * working directory to ${workspace_loc:PROJECT_NAME_HERE/bin}
     * See Run > Run Configurations > (your config) > Arguments
     */
    public String[] getFiles() {
	return getFile().list(filter);
    }

    /** Get a resource list of all the elements of this resource 
     * @return the list component
     */
    public ResourceList getDefaultList() {
	return defaultList;
    }

    @Override
	public String toString() {
 	return (name == null ? super.toString(): name) + " ("+link+')';
    }

}

