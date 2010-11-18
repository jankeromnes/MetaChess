package metachess.panel.logger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import metachess.game.Game;
import metachess.game.Move;
import metachess.library.Colour;
import metachess.library.ToolIcons;

/** Class of the Panel that contains all the current game's moves history
 * and the associated buttons
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class LogPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Game game;
    private final LogList list;
    private final JButton backButton;
    private final JButton forwardButton;
    private final JList listcomp;

    private JLabel lab;

    /** Creation of a panel of the moves history
     * @param g the current Game
     */
    public LogPanel(Game g) {
	super();

	game = g;

	setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();

	backButton = new JButton();
	forwardButton = new JButton();
       
	backButton.setIcon(new ImageIcon(getClass().getResource(ToolIcons.LEFT_ARROW.getPath())));
	forwardButton.setIcon(new ImageIcon(getClass().getResource(ToolIcons.RIGHT_ARROW.getPath())));


	list = new LogList();
	listcomp = new JList(list);
	listcomp.setBackground(Colour.GREY.getColor());
	listcomp.setSelectionMode(JList.VERTICAL);
	listcomp.setVisibleRowCount(10);
	listcomp.setCellRenderer(new LogRenderer());
	listcomp.setSelectionBackground(Colour.DARK_BLUE.getColor());
	JScrollPane comp = new JScrollPane(listcomp);
	comp.setPreferredSize(new Dimension(100,200));
	
	listcomp.addMouseListener(new MouseListener() {
		public void mouseClicked(MouseEvent e) {
		    if(!game.isBoardLocked()) {
		    	game.jump(list.getMoves(listcomp.getSelectedIndex()));
			    update();
		    }
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	);



	backButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if(!game.isBoardLocked()) { undo(); }
		}
	    });

        forwardButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if(!game.isBoardLocked()) { redo(); }
		}
	    });

        
        c.gridy = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
	
        c.weightx = 0.5;
        add(backButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;

	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 2;
	c.weightx = 0.5;
	add(forwardButton, c);

	c.anchor = GridBagConstraints.SOUTH;
	c.fill = GridBagConstraints.NONE;
	c.gridx = 0;
	c.gridy = 1;
	c.weightx = 1.0;
	c.gridwidth = 3;
	add(comp, c);

	c.gridy=2;
	lab = new JLabel();
	add(lab,c);

	update();

    }

    /** Add a move to this panel's list
     * @param m the move to add
     */
    public void addMove(Move m) {
    	list.addMove(m);
    	update();
    }

    /** Remove all the moves shown in this panel */
    public void clearMoves() {
    	list.clearMoves();
    }

    /** Undo the last move, called by the back button's listener */
    public void undo() {
    	assert(list.isBackable()): "undo : Action forbidden";
		game.jump(list.back());
		update();
    }

    /** Undo the last move, called by the forward button's listener */
    public void redo() {
    	assert(list.isForwardable()): "redo: Action forbidden";
		game.jump(list.forward());
		update();
    }

    /** Update the buttons and the list */
    public void update() {
    	backButton.setEnabled(list.isBackable());
    	forwardButton.setEnabled(list.isForwardable());
    	int index = list.getLastIndex();
	if(index >= 0) {
	    listcomp.setSelectedIndex(index);
	    listcomp.ensureIndexIsVisible(index);
	} else listcomp.clearSelection();

    	lab.setText(String.valueOf(list.getLastIndex()+1));
    	game.updateMenu(list.isBackable(),list.isForwardable());
    }

    /* // Obsolete by Jan's game.SavedGame
    public void saveGame(File file) {
    	list.saveGame(file);
    }
    */

    /** Load all the moves in a game
     * @param m the list of moves
     */
    public void loadGame(ArrayList<Move> m) {
    	list.loadGame(m);
    	game.jump(list.getMoves());
    	update();
   }

    /** Get the current list of moves shown in this panel
     * @return the list
     */
    public ArrayList<Move> getMoves() {
	return list.getMoves();
    }


}


