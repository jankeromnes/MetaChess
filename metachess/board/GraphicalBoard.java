package metachess.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;


import metachess.game.Coords;
import metachess.library.Colour;
import metachess.square.GraphicalSquare;

/** Class of graphical boards
 * @author Jan (7DD) [0.7.5], Agbeladem (7DD) [0.8.0]
 * @version 0.9.1
 */
public class GraphicalBoard extends JPanel {

    private static final long serialVersionUID = 1L;
    private AbstractBoard ab;

    protected GraphicalSquare[][] graphicalSquares;
    protected JPanel board;
    protected int width;
    protected int height;
    private int dim = 60;

    /** Create a Graphical Board
     * @param a the abstract board on which this board is based
     */
    public GraphicalBoard(AbstractBoard a) {
    	super();
	setLayout(new BorderLayout());
    	ab = a;
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


	Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, Colour.DARK_GREY.getColor());
	Dimension vdim = new Dimension(15, 100);
	JLabel lab;

	// VERTICAL PANELS

	JPanel Wgrid = new JPanel();
	JPanel Egrid = new JPanel();
	GridLayout Vlayout = new GridLayout(height, 1);
	Wgrid.setLayout(Vlayout);
	Egrid.setLayout(Vlayout);

	for(int j = 0 ; j < height ; j++) {
	    String s = String.valueOf(new Coords(0, j).getRowChar());

	    lab = new JLabel(s);
	    lab.setBackground(Colour.WHITE.getColor());
	    lab.setHorizontalAlignment(JLabel.CENTER);
	    lab.setBorder(border);
	    lab.setPreferredSize(vdim);
	    Wgrid.add(lab);

	    lab = new JLabel(s);
	    lab.setBackground(Colour.WHITE.getColor());
	    lab.setHorizontalAlignment(JLabel.CENTER);
	    lab.setBorder(border);
	    lab.setPreferredSize(vdim);
	    Egrid.add(lab);

	}


	// HORIZONTAL PANELS

	JPanel Ngrid = new JPanel();
	JPanel Sgrid = new JPanel();
	GridLayout Clayout = new GridLayout(1, width);
	Ngrid.setLayout(Clayout);
	Sgrid.setLayout(Clayout);

	for(int i = 0 ; i < width ; i++) {
	    String s = String.valueOf(new Coords(i, 0).getColumnChar());

	    lab = new JLabel(s);
	    lab.setBackground(Colour.WHITE.getColor());
	    lab.setHorizontalAlignment(JLabel.CENTER);
	    lab.setBorder(border);
	    Ngrid.add(lab);

	    lab = new JLabel(s);
	    lab.setBackground(Colour.WHITE.getColor());
	    lab.setHorizontalAlignment(JLabel.CENTER);
	    lab.setBorder(border);
	    Sgrid.add(lab);

	}


	// BOARD

	board = new JPanel();
    	board.setLayout(new GridLayout(height, width));
    	graphicalSquares = new GraphicalSquare[width][height];
    	for(int j = height-1 ; j >= 0 ; j--)
    	    for(int i = 0 ; i < width ; i++) {
		graphicalSquares[i][j] = new GraphicalSquare(ab.getSquare(i, j), this);
		graphicalSquares[i][j].setDim(dim);
		board.add(graphicalSquares[i][j]);
	    }
    	ab.setGraphicBoard(this);



	/* Create vertical panels with an invisible strut at both ends 
	   to match the board's columns  */
	int w = (int)(vdim.getWidth());
	JPanel north = new JPanel();
	north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
	north.add(Box.createHorizontalStrut(w));
	north.add(Ngrid);
	north.add(Box.createHorizontalStrut(w));
	JPanel south = new JPanel();
	south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
	south.add(Box.createHorizontalStrut(w));
	south.add(Sgrid);
	south.add(Box.createHorizontalStrut(w));

	add(board, BorderLayout.CENTER);
	add(south, BorderLayout.SOUTH);
	add(north, BorderLayout.NORTH);
	add(Wgrid, BorderLayout.WEST);
	add(Egrid, BorderLayout.EAST);

	/* The update was useful only when a new setup was loaded
	   Check GraphicalSquare.paint(g), Game constructor and Game.newGame(),
	   SetupBuilderBox.setBoardDimension(i, j) and SetupBuilderBox.load(setup)
	      to see how it's now handled..
	         Agbeladem,
		   July 19        on v.0.8.0,
		   November 14    on v.0.8.5
	*/
	//    	update();
    }

    /** Get the abstract board on which this graphical board is based
     * @return the abstract board
     */
    public AbstractBoard getAbstractBoard() {
	return ab;
    }

    /** Get a given abstract square in this board
     * @param i the column (X Coord) of the square
     * @param j the column (Y Coord) of the square
     * @return the wanted square
     */
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
    	    for(int i = 0 ; i < ab.getCols() ; i++) {
    	    	graphicalSquares[i][j].update();
	    }
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


    /** Test this graphical board in a separate window */
    public void test() {
	java.awt.Frame f = new javax.swing.JFrame("Graphical Board test");
	f.setSize(new Dimension(500, 500));
	init();
	f.add(this);
	f.pack();
	f.setVisible(true);
    }

}


