package metachess.boards;

import java.awt.GridLayout;

import javax.swing.JPanel;

import metachess.squares.GraphicalSquare;

/** Class of graphical boards
 * @author Jan (7DD) [0.7.5], Agbeladem (7DD) [0.8.0]
 * @version 0.8.0
 */
public class GraphicalBoard extends JPanel {

    private static final long serialVersionUID = 1L;
    private AbstractBoard ab;

    protected GraphicalSquare[][] graphicalSquares;
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
    
    /** Set the dimension of the sides of all the squares in this graphical board
     * @param i the new length of the side
     */
    public void setDim(int i) {
	dim = i;
    }

    /** (Re)initizalize this graphical board */
    public void init() {
    	removeAll();
	width = ab.getCols();
	height = ab.getRows();
    	setLayout(new GridLayout(height, width));
    	graphicalSquares = new GraphicalSquare[width][height];
    	for(int j = height-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < width ; i++)
		initSquare(i, j);
    	ab.setGraphicBoard(this);
	/* The update was useful only when a new setup was loaded
	   Check Square.paint(g), Game constructor and Game.newGame(),
	   SetupBuilderBox.setBoardDimension(i, j) and SetupBuilderBox.load(setup)
	      to see how it's now handled..
	         Agbeladem,
		   July 19        on v.0.8.0,
		   November 14    on v.0.8.5
	*/
	//    	update();
    }

    private void initSquare(int i, int j) {
	graphicalSquares[i][j] = new GraphicalSquare(ab.getSquare(i, j), this);
	graphicalSquares[i][j].setDim(dim);
	add(graphicalSquares[i][j]);
    }

    /** Get the abstract board on which this graphical board is based
     * @return the abstract board
     */
    public AbstractBoard getAbstractBoard() {
	return ab;
    }

    
    public GraphicalSquare getSquare(int i, int j) {
	assert squareExists(i ,j);
	return graphicalSquares[i][j];
    }

    public boolean squareExists(int i, int j) {
	return i >= 0 && i < width
	    && j >= 0 && j < height;
    }
    

    /** Update all the graphical squares of this graphical board */
    public void update() {
    	for(int j = ab.getRows()-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < ab.getCols() ; i++)
    	    	graphicalSquares[i][j].update();
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


