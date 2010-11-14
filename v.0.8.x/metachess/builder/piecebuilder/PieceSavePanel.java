package metachess.builder.piecebuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import metachess.builder.SavePanel;
import metachess.game.MoveType;
import metachess.library.Resource;
import metachess.loader.PieceImageLoader;

/** Class of the Piece Builderbox save panel
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public class PieceSavePanel extends SavePanel {

    private static final long serialVersionUID = 1L;
    private PieceBuilderBox parent;

    /** Creation of a Piece Builderbox save panel
     * @param b the Piece Builderbox
     */
    public PieceSavePanel(PieceBuilderBox b) {
	super(Resource.PIECES);
	parent = b;
    }

    @Override
    protected void save() {
	super.save();
	try {
	    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(Resource.RESOURCES.getPath() + "pieces_images.mci", true)));
	    String image = parent.getImageName();
	    StringBuilder s = new StringBuilder(field.getText().toLowerCase());
	    s.append(':');
	    s.append(image);
	    s.append(":B");
	    s.append(image.substring(1,image.length()));
	    pw.println("# Generated from builder");
	    pw.println(s.toString());
	    pw.close();
	    PieceImageLoader.load(true);
	} catch(IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void write() {
	println("// Metachess v.0.8.2/MCP=v.2");
	println("// Generated from builder\n");
	for(MoveType m: parent.getMoves())
	    println(m.getMCPFormat());
	println(parent.getSpecialMoves());

    }

    @Override
    protected void load(String name) {
	super.load(name);
	parent.load(name);
    }
    

}
