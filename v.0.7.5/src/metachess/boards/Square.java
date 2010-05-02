package metachess.boards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import metachess.game.Piece;
import metachess.library.Colour;

public class Square extends JButton {
   
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

	if(as.isNull())
	    setBackground(Colour.WHITE.getColor());
	else {
	    if(as.hasPiece()) // && as.getPiece().getImage() != null)
		setIcon(as.getPiece().getImage(dim));
	    else setIcon(null);
	    setBackground((as.isGreen()? Colour.GREEN : as.getColor()).getColor());
	}
    	// setText(as.hasPiece()? null : as.getName());
    }


    private class SquareListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
	    board.playSquare(as.getColumn(), as.getRow());
    	}
    }
    
    public void setDim(int i) { dim = i; }


    public String toString() {
	return "square of "+as;
    }

}
