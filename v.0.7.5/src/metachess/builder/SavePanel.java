package metachess.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metachess.library.Resource;

public abstract class SavePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private PrintWriter pw;
    protected final JTextField field;
    protected final Resource res;
    private final JButton button;

    public SavePanel(Resource r) {
	super();

	res = r;

	field = new JTextField("new_"+r.getName());

	button = new JButton("Save");
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    save();
		}
	    });
	
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	


	add(space());
	add(new JLabel("Save "+r.getName()));
	add(space());
	add(field);
	add(space());
	add(button);
	add(space());

    }
    
    private Component space() {
	return Box.createRigidArea(new Dimension(10,10));
    }

    protected void save() {

	try {
	    StringBuilder fileName = new StringBuilder(res.getPathFromExecFolder());
	    fileName.append(field.getText().toLowerCase());
	    fileName.append('.');
	    fileName.append(res.getExtension());
	    pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName.toString() )));
	    write();
	    pw.close();
	    JOptionPane.showMessageDialog(null, res.getName()+" file updated");
	} catch(IOException e) {
	    System.out.println(e);
	}
    }

    protected void print(String s) {
	pw.print(s);
    }

    protected void println(String s) {
	pw.println(s);
    }

    public abstract void write();


}
