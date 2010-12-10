package metachess.dialog;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import metachess.game.Game;

/** Class of the file load/save box
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public class FileBox extends JFileChooser {

    private static final long serialVersionUID = 1L;
    private Game game;
    /** Create a file box associated with a given game
     * @param g the game
     */
    public FileBox(Game g) {
	game = g;
	FileNameExtensionFilter f = new FileNameExtensionFilter("MetaChess Games (*.mcg)", "mcg");
	setFileFilter(f);
    }

    /** Launch this box
     * @param s whether it was called to save (true) or to load (false)
     */
    public void launch(boolean s) {
	int ret;
	if(s) {
	    ret = showSaveDialog(game);
	    if(ret == JFileChooser.APPROVE_OPTION) {
	    	String str = getSelectedFile().toString();
	    	if(!str.endsWith(".mcg")) setSelectedFile(new File(str+".mcg"));
	    	game.saveGame(getSelectedFile());
		}
	} else {
	    ret = showOpenDialog(game);
	    if(ret == JFileChooser.APPROVE_OPTION)
		game.loadGame(getSelectedFile());
	}
    }


}
