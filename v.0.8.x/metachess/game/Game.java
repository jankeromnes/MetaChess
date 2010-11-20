package metachess.game;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import metachess.ai.AIBoardTree;
import metachess.boards.ChessBoard;
import metachess.boards.GraphicalBoard;
import metachess.builder.BuilderBox;
import metachess.dialog.ErrorDialog;
import metachess.dialog.FileBox;
import metachess.dialog.GameModeBox;
import metachess.exceptions.MetachessException;
import metachess.library.DataExtractor;
import metachess.loader.GameLoader;
import metachess.panel.count.CountPanel;
import metachess.panel.logger.LogPanel;

/** Main Class of a Metachess Game and its window
 * @author Jan and Agbeladem (7DD)
 * @version 0.8.6
 */
public class Game extends JFrame {

    private static final long serialVersionUID = 1L;
    private boolean atomic;
    private int whiteAILevel;
    private int blackAILevel;
    private String[] AILevels = {"Human", "Very Easy", "Easy", "Average", "Master", "Elder"};
    private String setup;
    private final Menu menu;
    private final ChessBoard board;
    private final GraphicalBoard gb;
    private final CountPanel countPanel;
    private final LogPanel histo;
    private final GameModeBox gmBox;
    private final FileBox fileBox;
    private final BuilderBox builder;

    /** Create a new game
     * @param setup the file name of the desired setup (without the extension)
     */
    public Game(String setup) {
    	super("MetaChess");

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	this.setup = setup;

	atomic = false;
	whiteAILevel = 0;
	blackAILevel = 3;


	gmBox = new GameModeBox(this);
	fileBox = new FileBox(this);

	menu = new Menu(this);
	setJMenuBar(menu);

	builder = new BuilderBox();

	board = new ChessBoard(this);
	board.init(setup, atomic);

	gb = new GraphicalBoard(board);
	gb.init();
	gb.update();
	add(gb, BorderLayout.CENTER);
	
	
	countPanel = new CountPanel();
	histo = new LogPanel(this);
	
	JPanel p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	
	p.add(countPanel);
	p.add(Box.createVerticalGlue());
	p.add(histo);
	p.add(Box.createVerticalGlue());
	
	add(p, BorderLayout.EAST);
	
	pack();
	setVisible(true);
	    
	    
    }

    /** Jump to a given position of the game's logger
     * @param moves a list of all the played moves since the beginning
     */
    public void jump(ArrayList<Move> moves) {
		//newGame(moves.isEmpty());
    	if(!board.isLocked()) {
			newGame(false);
	    	board.jump(moves);
    	}
    }

    /**  Add a piece to the count list when it's been taken
     * @param pieceName the name of the taken piece
     * @param isWhite whether the piece's color is white
     */
    public void count(String pieceName, boolean isWhite) {
    	countPanel.add(pieceName, isWhite);
    }

    /** Ask for a new game with the gamebox dialog box*/
    public void askNewGame() {
    	if(!board.isLocked() && gmBox.launch()) newGame();
    }
    
    /** Start a new game */
    private void newGame() { newGame(true); }

    /** Start a new game
     * @param clear tells whether the logger should be cleared
     */
    private void newGame(boolean clear) {
	board.init(setup, atomic);
	gb.init();
	gb.update();
	countPanel.clear();
	if(clear) {
	    histo.clearMoves();
	    histo.update();
	}
    }
    

    /** End the last game */
    public void endGame() {
    	// to replace with EndGameDialog("winner is : " + board.getWinner()); (pseudo-code)
	    AIBoardTree aiboard = new AIBoardTree(board, 1);
		System.out.println("\nGAME OVER! (Final score : " + aiboard.getBestMoveSequence().getScore() + ")");
    }

    /** Add a move to the logger
     * @param m the move
     */
    public void addMove(Move m) {
    	histo.addMove(m);
    }

    /** Undo last move */
    public void undo(){
    	histo.undo();
    }

    /** Redo last undone move */
    public void redo() {
    	histo.redo();
    }

    /** Update the menu to enable/disable the Undo or Redo items
     * @param backable whether the Undo command is available
     * @param forwardable whether the Redo command is avaiable
     */
    public void updateMenu(boolean backable, boolean forwardable) {
    	menu.update(backable, forwardable);
    }

    /** Launch the file box to save the game */
    public void saveGame() {
    	if(!board.isLocked()) fileBox.launch(true);
    }

    /** Save the game to a given path
     * @param file the path
     */
    public void saveGame(File file) {
	/*
    	try {
    	    ObjectOutputStream s;
    	    s = new ObjectOutputStream(new FileOutputStream(file));
    	    s.writeObject(getSavedGame());
    	    s.close();
    	} catch(Exception e) {
	    e.printStackTrace();
	}
	*/
    }
    
    /*
    public SavedGame getSavedGame() {
    	return new SavedGame(setup, atomic, whiteAILevel, blackAILevel, histo.getMoves());
    }
    */

    public void loadGame() {
    	if(!board.isLocked()) fileBox.launch(false);
    }
    
    public void loadGame(File file) {

	try {
	    GameLoader.load(file);
	    loadGame(GameLoader.getSavedGame());
	} catch(MetachessException e) { new ErrorDialog(e); }

	/*
    	try {
    	    ObjectInputStream s;
    	    s = new ObjectInputStream(new FileInputStream(file));
    	    SavedGame sg = (SavedGame)(s.readObject());
    	    loadGame(sg);
    	    s.close();
    	}
    	catch(Exception e) { e.printStackTrace(); }
	*/

    }
    
    public void loadGame(SavedGame sg){
	setup = sg.getSetup();
	atomic = sg.isAtomic();
	board.init(setup, atomic);
	whiteAILevel = sg.getWhiteAILevel();
	blackAILevel = sg.getBlackAILevel();
	histo.loadGame(sg.getMoves());
	countPanel.clear();
    }


    public void launchBuilder() {
	builder.launch();
    }

    public boolean isAtomic() { return atomic; }
    
    public int getAILevel(boolean white) { return (white ? whiteAILevel : blackAILevel); }
    public int getMaxAILevel() { return AILevels.length; }
    public String[] getAILevels() { return AILevels; }
    public boolean isBoardLocked() { return board.isLocked(); }
    public String getSetup() { return setup; }
    public void setSetup(String s) { setup = s; }
    public void setWhiteAILevel(int wAI) { whiteAILevel = wAI; }
    public void setBlackAILevel(int bAI) { blackAILevel = bAI; }
    public void setAtomic(boolean a) { atomic = a; }

    public static void main(String[] argv) {
	DataExtractor.checkDataVersion();
	try {
	    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	} catch(Exception e) {}
			
	new Game(argv.length == 1 ? argv[0] : "classic"); 
    }


}

