package metachess.game;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;

import metachess.boards.ChessBoard;
import metachess.boards.GraphicBoard;
import metachess.builder.BuilderBox;
import metachess.logger.LogPanel;
import metachess.logger.Move;
import metachess.menus.FileBox;
import metachess.menus.GameModeBox;
import metachess.menus.Menu;


public class Game extends JFrame {

    private static final long serialVersionUID = 1L;
    private boolean atomic;
    private boolean whiteAI;
    private boolean blackAI;
    private String setup;
    private final Menu menu;
    private final ChessBoard board;
    private final GraphicBoard gb;
    private final LogPanel histo;
    private final GameModeBox gmBox;
    private final FileBox fileBox;
    private final BuilderBox builder;

    public Game() {
    	super("MetaChess");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    	atomic = false;
    	whiteAI = false;
    	blackAI = true;
    	setup = "test";

    	builder = new BuilderBox();
    	gmBox = new GameModeBox(this);
    	fileBox = new FileBox(this);

    	menu = new Menu(this);
    	setJMenuBar(menu);
	
    	board = new ChessBoard(this);
    	board.init(setup, atomic);
    	gb = new GraphicBoard(board);
    	add(gb, BorderLayout.CENTER);

    	histo = new LogPanel(this);
    	add(histo, BorderLayout.EAST);

    	pack();
    	setVisible(true);
		
    }
	
    public void jump(ArrayList<Move> moves) {
    	newGame(moves.isEmpty());
    	board.jump(moves);
    }

    public void askNewGame() {
    	if(gmBox.launch())
    		newGame();
    }
    
    public void newGame() { newGame(true); }

    public void newGame(boolean clear){
    	board.init(setup, atomic);
    	gb.init();
    	if(clear) {
    		histo.clearMoves();
    		histo.update();
    	}
    }
	
    public void endGame() {
    	newGame();
    }

    public void addMove(Move m) {
    	histo.addMove(m);
    }

    public void undo(){
    	histo.undo();
    }

    public void redo() {
    	histo.redo();
    }

    public void updateMenu(boolean backable, boolean forwardable) {
    	menu.update(backable, forwardable);
    }

    public void saveGame() {
    	fileBox.launch(true);
    }
    public void saveGame(File file) {
    	try {
    	    ObjectOutputStream s;
    	    s = new ObjectOutputStream(new FileOutputStream(file));
    	    s.writeObject(getSavedGame());
    	    s.close();
    	} catch(Exception e) { System.out.println(e); }
    }
    
    public SavedGame getSavedGame() {
    	return new SavedGame(setup, atomic, whiteAI, blackAI, histo.getMoves());
    }

    public void loadGame() {
    	fileBox.launch(false);
    }
    public void loadGame(File file) {
    	try {
    	    ObjectInputStream s;
    	    s = new ObjectInputStream(new FileInputStream(file));
    	    SavedGame sg = (SavedGame)(s.readObject());
    	    loadGame(sg);
    	    s.close();
    	}
    	catch(Exception e) { System.out.println(e); }
    }
    
    public void loadGame(SavedGame sg){
	    setup = sg.getSetup();
	    atomic = sg.isAtomic();
	    whiteAI = sg.isAI(true);
	    blackAI = sg.isAI(false);
	    histo.loadGame(sg.getMoves());
    }


    public void launchBuilder() {
	builder.launch();
    }

    public boolean isAtomic() { return atomic; }
    
    public boolean isAI(boolean white) { return (white ? whiteAI : blackAI); }

    public void setSetup(String s) { setup = s; }
    public void setWhiteAI(boolean wAI) { whiteAI = wAI; }
    public void setBlackAI(boolean bAI) { blackAI = bAI; }
    public void setAtomic(boolean a) { atomic = a; }
    

    public static void main(String[] argv) {
    	new Game()/*.askNewGame()*/;
    }


}

