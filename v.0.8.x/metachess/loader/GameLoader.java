package metachess.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import metachess.exceptions.FileAccessException;
import metachess.exceptions.FileContentException;
import metachess.exceptions.LoadException;
import metachess.game.SavedGame;

/** Singleton of the Game Loader
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public class GameLoader extends VariableLoader {

    private static GameLoader instance = new GameLoader();

    private SavedGame sg;

    private boolean atomic;
    private String setup;
    private int whiteAILevel;
    private int blackAILevel;

    @Override
    public void loadResource(String file) throws LoadException {
	sg = new SavedGame();
	try {
	    this.file = file;
	    BufferedReader br = new BufferedReader(new FileReader(file));

	    String line = br.readLine();
	    while(line.indexOf("{BEGIN}") == -1 && line != null) {
		readVariable(line);
		line = br.readLine();
	    }

	    StreamTokenizer st = new StreamTokenizer(br);
	    st.eolIsSignificant(true);
	    st.wordChars('0', '9');
	    int next = st.nextToken();
	    while(next != StreamTokenizer.TT_EOF) {
		if(next == StreamTokenizer.TT_WORD) {
		} else if(next != StreamTokenizer.TT_EOL)
		    throw new FileContentException("Invalid token value : "+next, file);
		next = st.nextToken();
	    }
	    br.close();
	} catch(IOException e) {
	    throw new FileAccessException(file);
	}
    }

    protected void setVariable(String name, String value) throws FileContentException {

	if(name.equals("atomic"))
	    sg.setAtomic(value.equals("TRUE"));
	else if(name.equals("setup"))
	    sg.setSetup(value.toLowerCase());
	else if(name.equals("whitelevel"))
	    sg.setWhiteAILevel(Integer.parseInt(value));
	else if(name.equals("blacklevel"))
	    sg.setBlackAILevel(Integer.parseInt(value));
 	else throw new FileContentException("Unknown variable \""+name+'"', file);
	
    }

    /** Get the saved game model that was loaded by this game loader
     * @return the game model
     */
    public static SavedGame getSavedGame() {
	return instance.sg;
    }

    /** Load a saved game
     * @param file the file in which the game is saved
     */
    public static void load(File f) throws LoadException {
	instance.loadResource(f.getPath());
    }

}

