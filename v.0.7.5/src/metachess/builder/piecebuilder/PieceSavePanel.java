package metachess.builder.piecebuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import metachess.builder.SavePanel;
import metachess.game.MoveType;
import metachess.library.Resource;

import metachess.library.PiecesImages;

public class PieceSavePanel extends SavePanel {

	private static final long serialVersionUID = 1L;
	private PieceBuilderBox parent;

    public PieceSavePanel(PieceBuilderBox b) {
	super(Resource.PIECES);
	parent = b;
    }

    protected void save() {
	super.save();
	try {
	    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(Resource.RESOURCES.getPathFromExecFolder() + "pieces_images.mci", true)));
	    String image = parent.getImageName();
	    StringBuilder s = new StringBuilder(field.getText().toLowerCase());
	    s.append(':');
	    s.append(image);
	    s.append(":B");
	    s.append(image.substring(1,image.length()));
	    pw.println("# Generated from builder");
	    pw.println(s.toString());
	    pw.close();
	    PiecesImages.load(true);
	} catch(IOException e) {
	    System.out.println(e);
	}
    }

    public void write() {
	println("// Metachess v.0.7.3/MCP=v.2");
	println("// Generated from builder\n");
	for(MoveType m: parent.getMoves()) {
	    println(m.getMCPFormat());
	}
	println(parent.getSpecialMoves());

    }
    

}
