package metachess.dialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metachess.game.Game;
import metachess.library.SetupList;

public class GameModeBox extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Game game;

    private SetupList setup;
    private JComboBox whiteAILevel;
    private JComboBox blackAILevel;
    private JCheckBox atomic;
    private JButton launch;
    private JButton cancel;
    private boolean newGame;

    public GameModeBox(Game g) {
	super(g, "New Game", true);
	game = g;

	setMinimumSize(new Dimension(320,280));
	setLocationRelativeTo(g);
	Container content = getContentPane();
	content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
	
	JPanel setups = new JPanel();
	setup = new SetupList();
	setups.add(setup);
	atomic = new JCheckBox("Atomic Chess Rules");
	setups.add(atomic);

	whiteAILevel = new JComboBox(game.getAILevels());
	blackAILevel = new JComboBox(game.getAILevels());
	
	JPanel buttons = new JPanel();
	launch = new JButton("Ok");
	launch.addActionListener(this);
	cancel = new JButton("Cancel");
	cancel.addActionListener(this);
	buttons.add(launch);
	buttons.add(cancel);

	content.add(Box.createVerticalStrut(10));
	content.add(setups);
	content.add(Box.createVerticalStrut(10));
	content.add(new JLabel("White Computer Level"));
	content.add(whiteAILevel);
	content.add(Box.createVerticalStrut(10));
	content.add(new JLabel("Black Computer Level"));
	content.add(blackAILevel);
	content.add(Box.createVerticalStrut(10));
	content.add(buttons);
	
	pack();

    }

    public boolean launch() {
	newGame = false;
	setup.init();
	setup.selectSetup(game.getSetup());
	atomic.setSelected(game.isAtomic());
	whiteAILevel.setSelectedIndex(game.getWhiteAILevel());
	blackAILevel.setSelectedIndex(game.getBlackAILevel());
	setVisible(true);
	return newGame;
    }

    public void actionPerformed(ActionEvent e) {
	newGame = e.getSource() == launch;
	if(newGame) {
	    game.setSetup(setup.getName());
	    game.setWhiteAILevel(whiteAILevel.getSelectedIndex());
	    game.setBlackAILevel(blackAILevel.getSelectedIndex());
	    game.setAtomic(atomic.isSelected());
	}
	setVisible(false);
    }

}


