package metachess.library;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/** Class of Setups List
 * @author Agbeladem (7DD)
 * @version 0.8.8
 */
public class SetupList extends JComboBox implements ResourceList {

    private static final long serialVersionUID = 1L;

    /** Creation of a default setup list */
    public SetupList() {
	super();
    }
    
    /** Select a specific setup in this list
     * @param str the name of the setup that will be selected
     */
    public void selectSetup(String str) {
    	for(int i = 0 ; i < getItemCount() ; i++) {
    		String s = getItemAt(i).toString();
    		if (s.substring(0,s.lastIndexOf('.')).equals(str)) setSelectedIndex(i);
    	}
    }

    @Override
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

    @Override
    public Component getComponent() {
	JPanel p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	p.add(Box.createVerticalGlue());
	p.add(this);
	p.add(Box.createVerticalGlue());
	return p;
    }

}


