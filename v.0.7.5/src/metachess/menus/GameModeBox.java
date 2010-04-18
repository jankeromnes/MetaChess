package metachess.menus;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import metachess.game.Game;
import metachess.library.Resource;

public class GameModeBox extends JDialog implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;

    private JComboBox setup;
    private JCheckBox whiteAI;
    private JCheckBox blackAI;
    private JCheckBox atomic;
    private JButton launch;
    private JButton cancel;
    private boolean newGame;

    public GameModeBox(Game g) {
	super(g, "New Game", true);
	game = g;

	setSize(250,200);
	setLocationRelativeTo(g);
	Container content = getContentPane();
	content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
	
	setup = new JComboBox();
	whiteAI = new JCheckBox("White is Computer");
	blackAI = new JCheckBox("Black is Computer");
	atomic = new JCheckBox("Atomic Chess Rules");

	content.add(setup);
	content.add(whiteAI);
	content.add(blackAI);
	content.add(atomic);

	JPanel panel = new JPanel();
	launch = new JButton("Ok");
	launch.addActionListener(this);

	cancel = new JButton("Cancel");
	cancel.addActionListener(this);

	panel.add(launch);
	panel.add(cancel);
	content.add(panel);

    }

    public boolean launch() {
	newGame = false;
	setup.removeAllItems();
	for(Object o : Resource.SETUPS.getFiles())
	    setup.addItem(o);
	setVisible(true);
	return newGame;
    }

    public void actionPerformed(ActionEvent e) {
	newGame = e.getSource() == launch;
	if(newGame) {
	    String s = setup.getSelectedItem().toString();
	    game.setSetup(s.substring(0,s.lastIndexOf('.')));
	    game.setWhiteAI(whiteAI.isSelected());
	    game.setBlackAI(blackAI.isSelected());
	    game.setAtomic(atomic.isSelected());
	}
	setVisible(false);
    }

}


