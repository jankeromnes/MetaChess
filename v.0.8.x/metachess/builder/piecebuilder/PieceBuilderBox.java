package metachess.builder.piecebuilder;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import metachess.boards.GraphicalBoard;
import metachess.boards.SampleBoard;
import metachess.builder.BuilderBox;
import metachess.game.MoveType;
import metachess.game.Piece;
import metachess.library.PieceImages;
import metachess.library.Pieces;
import metachess.library.Resource;
import metachess.squares.AbstractSquare;

/** Class of the Piece tab in the Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */
public class PieceBuilderBox extends JPanel {

    private static final long serialVersionUID = 1L;
    private final SampleBoard bs;
    private final GraphicalBoard gb;
    private final IconSelectPanel isp;
    private final MovesPanel mp;
    
    private ArrayList<MoveType> moves;
    private String image;
    
    /** Create a new Piece Builderbox */
    public PieceBuilderBox(BuilderBox frame) {
	super();

	setLayout(new BorderLayout());

	bs = new SampleBoard();
	gb = new GraphicalBoard(bs);
	isp = new IconSelectPanel(this);
	mp = new MovesPanel(frame, this);

	add(new PieceSavePanel(this), BorderLayout.NORTH);
	add(gb , BorderLayout.CENTER);
	add(mp, BorderLayout.EAST);
	add(isp, BorderLayout.WEST);
	
	
    }
    
    /** (Re)initialize this Builderbox and all its contained panels */
    public void init() {
	update(new ArrayList<MoveType>());
	isp.init();
    }

    /** Load a given piece in this panel
     * @param name the name of the piece to load
     */
    public void load(String name) {
	Piece p = Pieces.getPiece(name);
	mp.setMoves(p.getMoveTypes());
	changeIcon(PieceImages.getImage(name));
    }


    /** Update the authorized moves shown in this Builderbox
     * @param m the list of moves that will be enabled for the created piece
     */
    public void update(ArrayList<MoveType> m) {
	moves = m;
	//bs.deactivateSquare();
	Piece p;
	for(AbstractSquare s: bs) {
	    p = s.getPiece();
	    if(p.getName().equals("metamorph")) {
		p.setMoves(m);
		p.setGreenSquares(s.getRow(), s.getColumn(), bs);
	    }
	}
	gb.update();
	bs.resetIterator();
    }

    /** Get the list of moves types that this piece will be able to use
     * @return the list of moves as ArrayList
     */
    public ArrayList<MoveType> getMoves() {
	return moves;
    }


    /** Change the appearance of the piece that this panel creates
     * @param fileName the path of the image that will represent the piece
     */
    public void changeIcon(String fileName) {
	image = fileName;
	fileName = Resource.PIECES_IMAGES.getPath(false)+image;
	Piece p;
	for(AbstractSquare s : bs) {
	    p = s.getPiece();
	    if(p.getName().equals("metamorph"))  {
		p.setImage(fileName);
		gb.update();
	    }
	}
	bs.resetIterator();
    }

    /** Get the name of the created piece's appearance
     * @return the path of the image that wil represent the piece
     */
    public String getImageName() {
	return image;
    }

    /** Get the list of the special moves of the created piece
     * @return the list of special moves in the MCP format
     */
    public String getSpecialMoves() {
	StringBuilder s = new StringBuilder();
	if(mp.isJoker()) s.append("SJ\n");
	if(mp.isKing()) s.append("SK\n");
	if(mp.isRook()) s.append("SR\n");
	if(mp.isPawn()) s.append("SP\n");
	return s.toString();
    }

}


