package metachess.builder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metachess.dialog.ConfirmDialog;
import metachess.dialog.ErrorDialog;
import metachess.dialog.MessageDialog;
import metachess.exception.WriteException;
import metachess.library.Resource;
import metachess.library.ResourceList;

/** Abstract class of a builder's load AND save panel
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public abstract class SavePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    protected final JDialog loader;
    protected final JTextField field;
    protected final Resource res;
    private final ResourceList list;
    private final JButton save;
    private final JButton load;
    private PrintWriter pw;

    /** Creation of a save panel
     * @param r the resource (Setup, Piece, Image) to which it is related
     */
    public SavePanel(Resource r) {
	super();

	res = r;

	// LOADER
	loader = new JDialog();
	loader.setTitle("Load a new "+r.getName());
	loader.setPreferredSize(new Dimension(400, 200));
	list = r.getDefaultList();
	list.init();
	JButton loaderLoad = new JButton("Load");
	loaderLoad.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    loader.setVisible(false);
		    load(list.getName());
		}
	    });
	loader.add(loaderLoad, BorderLayout.SOUTH);
	loader.add(list.getComponent(), BorderLayout.CENTER);
	loader.pack();


	field = new JTextField("new_"+r.getName());

	load = new JButton("Load");
	load.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    loader.setVisible(true);
		}
	    });

	// SAVER
	save = new JButton("Save");
	save.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    save();
		}
	    });
	
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

	add(space());
	add(load);
	add(space());
	add(new JLabel("Save "+r.getName()));
	add(space());
	add(field);
	add(space());
	add(save);
	add(space());

    }
    
    /** Set the value of the field containing the name of the created resource
     * @param name the new value for the field
     */
    public void setName(String name) {
	field.setText(name);
    }

    private Component space() {
	return Box.createHorizontalStrut(10);
    }

    /** Load a Resource
     * @param name the resource's name
     */
    protected void load(String name) {
	setName(name);
    }

    /** Save a Resource */
    protected void save() {

	try {
	    StringBuilder fileName = new StringBuilder(res.getPath());
	    fileName.append(field.getText().toLowerCase());
	    fileName.append('.');
	    fileName.append(res.getExtension());
	    File file = new File(fileName.toString());
	    if(!file.exists() || new ConfirmDialog("Save a "+res.getName(),
						  "If you save this file, it will erase the existing file\n"
						  + "Continue ?", false).launch()) {
		pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		write();
		pw.close();
		new MessageDialog(res.getName()+" file updated");
	    }
	} catch(IOException e) {
	    new ErrorDialog(new WriteException(res.getPath()));
	}

    }

    protected void print(String s) {
	pw.print(s);
    }

    protected void print(StringBuilder s) {
	pw.print(s.toString());
    }

    protected void println(String s) {
	pw.println(s);
    }

    protected void println(StringBuilder s) {
	pw.println(s.toString());
    }

    /** Write the MC Resource to its file */
    public abstract void write();

}

