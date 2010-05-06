package metachess.menus;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import metachess.game.Game;
import metachess.library.Resource;

public class GameModeBox extends JDialog implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;

    private JComboBox setup;
    private JSlider whiteAILevel;
    private JSlider blackAILevel;
    private JCheckBox atomic;
    private JButton launch;
    private JButton cancel;
    private boolean newGame;

    public GameModeBox(Game g) {
	super(g, "New Game", true);
	game = g;

	setSize(300,300);
	setLocationRelativeTo(g);
	Container content = getContentPane();
	content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
	
	setup = new JComboBox();
	whiteAILevel = new JSlider(JSlider.HORIZONTAL, 0, game.getMaxAILevel(), game.getAILevel(true));
	blackAILevel = new JSlider(JSlider.HORIZONTAL, 0, game.getMaxAILevel(), game.getAILevel(false));
	atomic = new JCheckBox("Atomic Chess Rules");

	Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
	labelTable.put( new Integer(0), new JLabel("Human") );
	labelTable.put( new Integer(1), new JLabel("Normal") );
	
	whiteAILevel.setMajorTickSpacing(1);
	whiteAILevel.setMinorTickSpacing(1);
	whiteAILevel.setPaintTicks(true);
	whiteAILevel.setLabelTable( labelTable );
	whiteAILevel.setPaintLabels(true);
	
	blackAILevel.setMajorTickSpacing(1);
	blackAILevel.setMinorTickSpacing(1);
	blackAILevel.setPaintTicks(true);
	blackAILevel.setLabelTable( labelTable );
	blackAILevel.setPaintLabels(true);
	
	JPanel sliders = new JPanel();
	sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
	sliders.add(new JLabel("White Computer Level"));
	sliders.add(whiteAILevel);
	sliders.add(new JLabel("Black Computer Level"));
	sliders.add(blackAILevel);

	content.add(setup);
	content.add(atomic);
	content.add(sliders);

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
	    game.setWhiteAILevel(whiteAILevel.getValue());
	    game.setBlackAILevel(blackAILevel.getValue());
	    game.setAtomic(atomic.isSelected());
	}
	setVisible(false);
    }

}


