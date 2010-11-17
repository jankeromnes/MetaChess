package metachess.dialog;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import metachess.game.Game;

public class FileBox extends JFileChooser {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Game game;
    boolean saving;

    public FileBox(Game g) {
	game = g;
	FileNameExtensionFilter f = new FileNameExtensionFilter("MetaChess Games (*.mcg)", "mcg");
	setFileFilter(f);
    }


    public void launch(boolean s) {
	saving = s;
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
