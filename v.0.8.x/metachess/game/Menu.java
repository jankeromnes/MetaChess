package metachess.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import metachess.library.ToolIcons;

public class Menu extends JMenuBar {

    private static final long serialVersionUID = 1L;
    
    private Game game;

    private JMenuItem undoMove;
    private JMenuItem redoMove;


    public Menu(Game g){
	super();
	game = g;
		
	// GAME MENU
	JMenu gameMenu = new JMenu("Game");
	gameMenu.setIcon(new ImageIcon(ToolIcons.GAME.getPath()));
	gameMenu.setMnemonic('G');
		

	// New Game
	JMenuItem newGame = new JMenuItem("New Game");
	newGame.setIcon(new ImageIcon(ToolIcons.NEW.getPath()));
	newGame.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		    game.askNewGame();
		}				
	    });
	newGame.setMnemonic('N');
	newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
	gameMenu.add(newGame);
	gameMenu.addSeparator();


	// Save Game
	JMenuItem saveGame = new JMenuItem("Save Game");
	saveGame.setIcon(new ImageIcon(ToolIcons.SAVE.getPath()));
	saveGame.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    game.saveGame();
		}
	    });
	saveGame.setMnemonic('S');
	saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
	gameMenu.add(saveGame);


	// Load Game
	JMenuItem loadGame = new JMenuItem("Load Game");
	loadGame.setIcon(new ImageIcon(ToolIcons.LOAD.getPath()));
	loadGame.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    game.loadGame();
		}
	    });
	loadGame.setMnemonic('L');
	loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
	gameMenu.add(loadGame);
	gameMenu.addSeparator();





	// Quit Game
	JMenuItem quitGame = new JMenuItem("Quit Game");
	quitGame.setIcon(new ImageIcon(ToolIcons.EXIT.getPath()));
	quitGame.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		    System.exit(0);
		}				
	    });
	quitGame.setMnemonic('Q');
	quitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
	gameMenu.add(quitGame);
		
	add(gameMenu);


	// EDIT MENU
	JMenu editMenu = new JMenu("Edit");
	editMenu.setIcon(new ImageIcon(ToolIcons.BUILDER.getPath()));
	editMenu.setMnemonic('E');
		

	// Undo Move
	undoMove = new JMenuItem("Undo Move");
	undoMove.setIcon(new ImageIcon(ToolIcons.LEFT_ARROW.getPath()));
	undoMove.setMnemonic('U');
	undoMove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
	editMenu.add(undoMove);
	undoMove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!game.isBoardLocked()) game.undo();
		}
	    });

	// Redo Move
	redoMove = new JMenuItem("Redo Move");
	redoMove.setIcon(new ImageIcon(ToolIcons.RIGHT_ARROW.getPath()));
	redoMove.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!game.isBoardLocked()) game.redo();
		}				
	    });
	redoMove.setMnemonic('R');
	redoMove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
	editMenu.add(redoMove);
	editMenu.addSeparator();

	// BuilderPannel
	JMenuItem builder = new JMenuItem("Open Builder");
	builder.setIcon(new ImageIcon(ToolIcons.BUILDER.getPath()));
	builder.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    game.launchBuilder();
		}
	    });
	builder.setMnemonic('B');
	builder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
	editMenu.add(builder);


	add(editMenu);
		
    }


    public void update (boolean backable, boolean forwardable) {
	undoMove.setEnabled(backable);
	redoMove.setEnabled(forwardable);
    }

}



