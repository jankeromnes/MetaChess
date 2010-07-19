package metachess.builder;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import metachess.builder.piecebuilder.PieceBuilderBox;
import metachess.builder.setupbuilder.SetupBuilderBox;
import metachess.library.PieceImages;

/** Class of the Builderbox (dialog window)
 * @author Agbeladem (7DD)
 * @version 0.7.3
 */
public class BuilderBox extends JDialog {

    private static final long serialVersionUID = 1L;
    private final SetupBuilderBox setup;
    private final PieceBuilderBox piece;

    /** Create a new Builderbox (only called once) */
    public BuilderBox() {
	super();

	setTitle("Metachess - Builder");

	
	JTabbedPane tabs = new JTabbedPane();
	PieceImages.load();

	

	setup = new SetupBuilderBox();
	piece = new PieceBuilderBox(this);
	
	tabs.add(setup, "Setup Builder");
       	tabs.add(piece,"Piece Builder");

	tabs.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    init();
		}
	    });


	add(tabs);

	pack();

    }

    /** Show the Builderbox */
    public void launch() {
	setVisible(true);
	init();
    }

    /** (Re)initialize the Builderbox */
    public void init() {
	setup.init();
       	piece.init();
    }


    public static void main(String[] a) {
	BuilderBox b = new BuilderBox();
	b.setVisible(true);
	b.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

}

