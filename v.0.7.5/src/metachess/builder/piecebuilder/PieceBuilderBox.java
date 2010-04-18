package metachess.builder.piecebuilder;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import metachess.boards.AbstractSquare;
import metachess.boards.GraphicBoard;
import metachess.boards.SampleBoard;
import metachess.builder.BuilderBox;
import metachess.game.MoveType;
import metachess.game.Piece;
import metachess.library.Resource;

public class PieceBuilderBox extends JPanel {

	private static final long serialVersionUID = 1L;
	private final SampleBoard bs;
	private final GraphicBoard gb;
    private final IconSelectPanel isp;
    private final MovesPanel mp;
    
    private ArrayList<MoveType> moves;
    private String image;
    
    public PieceBuilderBox(BuilderBox frame) {
	super();

	setLayout(new BorderLayout());

	bs = new SampleBoard();
	gb = new GraphicBoard(bs);
	isp = new IconSelectPanel(this);
	mp = new MovesPanel(frame, this);

	add(new PieceSavePanel(this), BorderLayout.NORTH);
	add(gb , BorderLayout.CENTER);
	add(mp, BorderLayout.EAST);
	add(isp, BorderLayout.WEST);
	
	
    }
    
    public void init() {
	update(new ArrayList<MoveType>());
	isp.init();
    }

    public void changeIcon(String fileName) {
	Piece p;
	image = fileName;
	fileName = Resource.PIECES_IMAGES.getPath(false)+image;
	for(AbstractSquare s : bs) {
	    p = s.getPiece();
	    if(p.getName().equals("metamorph"))  {
		p.setImage(fileName);
		gb.update();
	    }
	}
	bs.resetIterator();
    }

    public void update(ArrayList<MoveType> m) {
	Piece p;
	moves = m;
	bs.deactivateSquare();
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

    public ArrayList<MoveType> getMoves() {
	return moves;
    }


    public String getImageName() {
	return image;
    }

    public String getSpecialMoves() {
	StringBuilder s = new StringBuilder();
	if(mp.isJoker()) s.append("SJ\n");
	if(mp.isKing()) s.append("SK\n");
	if(mp.isRook()) s.append("SR\n");
	if(mp.isPawn()) s.append("SP\n");
	return s.toString();
    }

}


