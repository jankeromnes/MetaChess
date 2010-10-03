package metachess.boards;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import metachess.library.Colour;

/** Class of graphical squares
 * @author Jan (7DD) [v.0.7.5], Agbeladem (7DD) [v.0.8.0]
 * @version 0.8.0
 */
public class Square extends JButton {
   
    private static final long serialVersionUID = 1L;
    private final GraphicalBoard board;
    private AbstractSquare as;
    private int dim; // Piece-Image dimensions (square side's length)

    private class SquareListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
	    board.getAbstractBoard().playSquare(as.getColumn(), as.getRow());
    	}
    }

    /** Create a graphical square
     * @param a the abstract square on which this square is based
     * @param b the abstract board to which this square belongs
     */
    public Square(AbstractSquare a, GraphicalBoard b) {
    	super();
    	as = a;
    	board = b;
    	addActionListener(new SquareListener());
    }

    /** Set the abstract square on which this graphical square is based
     * @param the new abstract square
     */
    public void setAbstractSquare(AbstractSquare s) {
	as = s;
    }

    /** Update how this graphical square looks */
    public void update() {
	setIcon(null); // THIS HAS TO DISAPPEAR !
	if(as.isNull()) {
	    setBackground(Colour.WHITE.getColor());
	    setIcon(null);
	} else {
	    if(as.hasPiece())
		setIcon(as.getPiece().getImage(dim));
	    else setIcon(null);
	    setBackground((as.isGreen()? Colour.GREEN : as.getColor()).getColor());
	}
    	//setText(as.hasPiece()? null : as.getName());

    }

    /** Set the dimension of the side of this square
     * @param i the new length of the side
     */
    public void setDim(int i) {
	dim = i;
	board.setDim(i);
	update();
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);
	Dimension d = getSize();
	int dim = (int)(Math.min(d.getWidth(), d.getHeight()));
	if(dim != this.dim)
	    setDim(dim);
    }


    @Override
	public String toString() {
	return "square of "+as;
    }

}

