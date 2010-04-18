package metachess.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import metachess.game.Game;

public class Menu extends JMenuBar {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;

    private JMenuItem undoMove;
    private JMenuItem redoMove;


    public Menu(Game g){
	super();
	game = g;
		
	// GAME MENU
	JMenu gameMenu = new JMenu("Game");
	gameMenu.setMnemonic('G');
		

	// New Game
	JMenuItem newGame = new JMenuItem("New Game");
	newGame.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		    game.askNewGame();
		}				
	    });
	newGame.setMnemonic('N');
	newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
	gameMenu.add(newGame);
	gameMenu.addSeparator();


	// Save Game
	JMenuItem saveGame = new JMenuItem("Save Game");
	saveGame.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    game.saveGame();
		}
	    });
	saveGame.setMnemonic('S');
	saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
	gameMenu.add(saveGame);


	// Load Game
	JMenuItem loadGame = new JMenuItem("Load Game");
	loadGame.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    game.loadGame();
		}
	    });
	loadGame.setMnemonic('L');
	loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
	gameMenu.add(loadGame);
	gameMenu.addSeparator();





	// Quit Game
	JMenuItem quitGame = new JMenuItem("Quit Game");
	quitGame.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		    System.exit(0);
		}				
	    });
	quitGame.setMnemonic('Q');
	quitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
	gameMenu.add(quitGame);
		
	add(gameMenu);


	// EDIT MENU
	JMenu editMenu = new JMenu("Edit");
	editMenu.setMnemonic('E');
		

	// Undo Move
	undoMove = new JMenuItem("Undo Move");
	undoMove.setMnemonic('U');
	undoMove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
	editMenu.add(undoMove);
	undoMove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    game.undo();
		}
	    });

	// Redo Move
	redoMove = new JMenuItem("Redo Move");
	redoMove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    game.redo();
		}				
	    });
	redoMove.setMnemonic('R');
	redoMove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));
	editMenu.add(redoMove);
	editMenu.addSeparator();

	// BuilderPannel
	JMenuItem builder = new JMenuItem("Open Builder");
	builder.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    game.launchBuilder();
		}
	    });
	builder.setMnemonic('B');
	builder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
	editMenu.add(builder);


	add(editMenu);
		
    }


    public void update (boolean backable, boolean forwardable) {
	undoMove.setEnabled(backable);
	redoMove.setEnabled(forwardable);
    }

}



