package metachess.library;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/** Class of Setups List
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class SetupsList extends JComboBox implements ResourceList {

	private static final long serialVersionUID = 1L;

	public SetupsList() {
	super();
        setMaximumSize(new Dimension(200, 50));
    }


    public void init() {
	removeAllItems();
	for(Object o : Resource.SETUPS.getFiles())
	    addItem(o);
    }

    @Override
	public String getName() {
	String s = getSelectedItem().toString();
	return s.substring(0,s.lastIndexOf('.'));
    }

    public Component getComponent() {
	JPanel pan = new JPanel();
	pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
	pan.add(Box.createHorizontalGlue());
	pan.add(this);
	pan.add(Box.createHorizontalGlue());
	return pan;
    }

}


