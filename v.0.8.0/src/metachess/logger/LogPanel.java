package metachess.logger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import metachess.game.Game;
import metachess.library.Colour;
import metachess.library.ToolIcons;

public class LogPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final LogList list;
    private final JList listcomp;
    private final Game game;
    private final JButton backButton;
    private final JButton forwardButton;

    private JLabel lab;

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
		    game.jump(list.getMoves(listcomp.getSelectedIndex()));
		    update();
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	);



	backButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    undo();
		}
	    });

        forwardButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    redo();
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

    public void addMove(Move m) {
    	list.addMove(m);
    	update();
    }

    public void clearMoves() {
    	list.clearMoves();
    }

    public void undo() {
    	assert(list.isBackable()): "undo : Action forbidden";
	    game.jump(list.back());
	    update();
    }

    public void redo() {
    	assert(list.isForwardable()): "redo: Action forbidden";
	    game.jump(list.forward());
	    update();
    }

    public void update() {
    	backButton.setEnabled(list.isBackable());
    	forwardButton.setEnabled(list.isForwardable());
    	int index = list.getLastIndex();
    	listcomp.setSelectedIndex(index);
    	listcomp.ensureIndexIsVisible(index);
    	lab.setText(String.valueOf(list.getLastIndex()+1));
    	game.updateMenu(list.isBackable(),list.isForwardable());
    }

    public void saveGame(File file) {
    	list.saveGame(file);
    }

    public void loadGame(ArrayList<Move> m) {
    	list.loadGame(m);
    	game.jump(list.getMoves());
    	update();
   }

	public ArrayList<Move> getMoves() {
		return list.getMoves();
	}


}


