package metachess.loader;

import metachess.exceptions.FileContentException;
import metachess.exceptions.LoadException;

/** Class of a loader that, among other things, load variables
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public abstract class VariableLoader implements Loader {

    protected String file;
    protected abstract void setVariable(String name, String value) throws FileContentException;

    protected void readVariable(String line) throws FileContentException {
	int i = line.indexOf('#');
	if(i != -1)
	    line = line.substring(0,i);
	line = line.replaceAll("\\s","");
	i = line.indexOf('=');
	if(i != -1) {
	    String var = line.substring(0,i);
	    String value = line.substring(i+1, line.length());
	    setVariable(var.toLowerCase(), value.toLowerCase());
	}
    }

    public abstract void loadResource(String s) throws LoadException;

}

