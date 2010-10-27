package metachess.library;

import java.awt.Component;

import javax.swing.JComboBox;

/** Class of Setups List
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class SetupList extends JComboBox implements ResourceList {

	private static final long serialVersionUID = 1L;

	public SetupList() {
	super();
    }

    public void init() {
	removeAllItems();
	for(Object o : Resource.SETUPS.getFiles())
	    addItem(o);
    }
    
    public void selectSetup(String str) {
    	for(int i = 0 ; i < getItemCount() ; i++) {
    		String s = getItemAt(i).toString();
    		if (s.substring(0,s.lastIndexOf('.')).equals(str)) setSelectedIndex(i);
    	}
    }

    @Override
	public String getName() {
	String s = getSelectedItem().toString();
	return s.substring(0,s.lastIndexOf('.'));
    }

    public Component getComponent() {
	return this;
    }

}


