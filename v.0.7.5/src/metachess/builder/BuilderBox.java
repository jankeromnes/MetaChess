package metachess.builder;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import metachess.builder.piecebuilder.PieceBuilderBox;
import metachess.builder.setupbuilder.SetupBuilderBox;
import metachess.library.PiecesImages;

public class BuilderBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private final SetupBuilderBox setup;
    private final PieceBuilderBox piece;

    public BuilderBox() {
	super("Metachess - Builder");

	
	JTabbedPane tabs = new JTabbedPane();
	PiecesImages.load();

	

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

    public void launch() {
	setVisible(true);
	init();
    }

    public void init() {
	setup.init();
       	piece.init();
    }


    public static void main(String[] a) {
	BuilderBox b = new BuilderBox();
	b.setVisible(true);
    	b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

