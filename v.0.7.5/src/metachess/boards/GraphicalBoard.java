package metachess.boards;

import java.awt.GridLayout;

import javax.swing.JPanel;

/** Class of graphical boards
 * @author Jan (7DD) [0.7.5], Agbeladem (7DD) [0.8.0]
 * @version 0.8.0
 */
public class GraphicalBoard extends JPanel {

    private static final long serialVersionUID = 1L;
    private AbstractBoard ab;

    protected Square[][] squares;
    protected int width;
    protected int height;
    private int dim = 60;

    /** Create a Graphical Board
     * @param a the abstract board on which this board is based
     */
    public GraphicalBoard(AbstractBoard a) {
    	super();
    	ab = a;
 	init();
    }
    
    public void setDim(int i) {
	dim = i;
    }

    public void init() {
    	removeAll();
	width = ab.getCols();
	height = ab.getRows();
    	setLayout(new GridLayout(height, width));
    	squares = new Square[width][height];
    	for(int j = height-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < width ; i++)
		initSquare(i, j);
    	ab.setGraphicBoard(this);
    	update();
    }

    public void initSquare(int i, int j) {
	squares[i][j] = new Square(ab.getSquare(i, j), ab);
	squares[i][j].setDim(dim);
	add(squares[i][j]);
    }

    public Square getSquare(int i, int j) {
	assert squareExists(i ,j);
	return squares[i][j];
    }

    public boolean squareExists(int i, int j) {
	return i >= 0 && i < width
	    && j >= 0 && j < height;
    }

    public void update() {
    	for(int j = ab.getRows()-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < ab.getCols() ; i++)
    	    	squares[i][j].update();
    }

    /** Get the number of columns of the board
     * @return the number of columns
     */
    public int getCols() {
	return width;
    }

    /** Get the number of rows of the board
     * @return the number of rows
     */
    public int getRows() {
	return height;
    }

}


