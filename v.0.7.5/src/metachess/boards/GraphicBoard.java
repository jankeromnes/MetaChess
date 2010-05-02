package metachess.boards;

import java.awt.GridLayout;

import javax.swing.JPanel;

/** Class of graphical boards
 * @author Jan (7DD)
 * @version 0.7.5
 */
public class GraphicBoard extends JPanel {

    private static final long serialVersionUID = 1L;
    private AbstractBoard ab;
    protected Square[][] squares;
    
    public GraphicBoard(AbstractBoard a) {
    	super();
    	ab = a;
 	init();
    }
    
    public void init() {
    	removeAll();
    	setLayout(new GridLayout(ab.getRows(), ab.getCols()));
    	squares = new Square[ab.getCols()][ab.getRows()];
    	for(int j = ab.getRows()-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < ab.getCols() ; i++) {
    	    	squares[i][j] = new Square(ab.getSquare(i, j), ab);
    	    	add(squares[i][j]);
    	    }
    	ab.setGraphicBoard(this);
    	update();
    }

    public void update() {
    	for(int j = ab.getRows()-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < ab.getCols() ; i++)
    	    	squares[i][j].update();
    }

}


