package metachess.boards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import metachess.game.Piece;
import metachess.library.Colour;

public class Square extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private final AbstractBoard board;
    private AbstractSquare as;
    private int dim; // Piece-Image dimensions (square side's length)

    public Square(AbstractSquare a, AbstractBoard b) {
    	super();
    	as = a;
    	board = b;
    	dim = 60;
    	addActionListener(new SquareListener());
    }

    public void update() {
    	Piece piece = as.getPiece();
    	if(piece != null && piece.getImage() != null)
    	    setIcon(piece.getImage(dim));
    	else setIcon(null);

    	if(as.isGreen()) setBackground(Colour.GREEN.getColor());
    	else setBackground(as.getColor().getColor());

    	// setText(hasPiece()? null : "("+i+','+j+')');
    	setText(as.hasPiece()? null : as.getName());
    }


    private class SquareListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		board.playSquare(as.getColumn(), as.getRow());
    	}
    }
    
    public void setDim(int i) { dim = i; }


}
