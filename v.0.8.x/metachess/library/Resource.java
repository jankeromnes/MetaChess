package metachess.library;

import java.io.File;
import java.io.FilenameFilter;

/** Enum of Resources
 * @author Agbeladem (7DD)
 * @version 0.7.3
 */
public enum Resource {

    RESOURCES("resources"),
	PIECES_IMAGES("resources/images/pieces","^W.+", "png", "pieceImage", new PieceImageList()),
	PIECES("resources/pieces", "^.+", "mcp", "piece", new PiecesList()),
	SETUPS("resources/setups", "^.+", "mcs","setup", new SetupList());

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
	link = folder+'/';
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
     * @param relative whether the path is relative or not
     * @return the String of the path
     */
    public String getPath(boolean relative) {
	return (relative? "../../" : "/") + link;
    }

    /** Get Relative Path of the Resource folder,
     * starting from the folder where the
     * application was originally executed
     * @return the path
     */
    /*
    public String getPathFromExecFolder() {
	return "/resources/"+ link;
    }
    */

    /** List all the files in the Resource folder
     * @return an array of File
     * 
     * IMPORTANT NOTE : For Eclipse users, don't forget to set the
     * working directory to ${workspace_loc:PROJECT_NAME_HERE/bin}
     * See Run > Run Configurations > (your config) > Arguments
     */
    public String[] getFiles() {
	return new File(link).list(filter);
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

