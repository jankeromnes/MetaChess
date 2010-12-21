package metachess.builder.setupbuilder;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import metachess.board.EditableBoard;
import metachess.board.GraphicalBoard;
import metachess.game.Piece;
import metachess.library.PieceListModel;
import metachess.loader.SetupLoader;

/** Class of the Setup tab in the Builderbox
 * @author Agbeladem (7DD)
 * @version 0.9.0
 */
public class SetupBuilderBox extends JPanel {

    private static final long serialVersionUID = 1L;
    private final EditableBoard board;
    private final GraphicalBoard gb;
    private final ToolSelectPanel tsp;
    private final SetupSavePanel sap;
    private final SetupSettingsPanel ssp;

    private Tool tool;
    private Piece piece;
    private int rows = 8;
    private int cols = 8;

    /** Creation of a Setup Builderbox */
    public SetupBuilderBox() {
	super();
	setLayout(new BorderLayout());

	board = new EditableBoard(this);
	board.init(cols, rows);
	gb = new GraphicalBoard(board);
	gb.setDim(32);
	gb.init();
	tsp = new ToolSelectPanel(this);
	sap = new SetupSavePanel(this);
	ssp = new SetupSettingsPanel(this);

	JPanel center = new JPanel();
	center.setLayout(new BorderLayout());
	center.add(gb, BorderLayout.CENTER);
	center.add(tsp, BorderLayout.SOUTH);

	add(sap, BorderLayout.NORTH);
	add(center, BorderLayout.CENTER);
	add(ssp, BorderLayout.EAST);

    }

    /** (Re)initialize this Setup Builderbox */
    public void init() {
	tsp.init();
    }

    /** Load a given setup in this panel
     * @param name the setup to load
     */
    public void load(String name) {
	SetupLoader.load(board, name);
	gb.init();
	tsp.init();
	cols = gb.getCols();
	rows = gb.getRows();

	ssp.setBoardDimensions(cols, rows);
	getPromotionListModel().load(board.getPromotionList());

	gb.update();
    }

    // GETTERS / SETTERS

    /** Set the dimensions of the editable board
     * @param i the board's number of columns
     * @param j the board's number of rows
     */
    public void setBoardDimensions(int i, int j) {
	cols = i;
	rows = j;
	board.init(i, j);
	gb.init();
	validate();
    }

    /** Set the tool selected by the user
     * @param t the tool
     */
    public void setTool(Tool t) {
	tool = t;
    }

    /** Get the tool selected by the user
     * @return the tool
     */
    public Tool getTool() {
	return tool;
    }

    /** Set the piece selected by the user
     * @param p the piece
     */
    public void setPiece(Piece p) {
	piece = p;
    }

    /** Get the piece selected by the user 
     * @return the piece
     */
    public Piece getPiece() {
	return piece;
    }

    /** Get the editable board's number of columns
     * @return the number of columns
     */
    public int getCols() {
	return cols;
    }

    /** Get the editable board's number of rows
     * @return the number of rows
     */
    public int getRows() {
	return rows;
    }

    /** Get the editable board of this setup builder
     * @return the board
     */
    public EditableBoard getBoard() {
	return board;
    }

    /** Get the graphical board of this setup builder
     * @return the graphical board
     */
    public GraphicalBoard getGraphicalBoard() {
	return gb;
    }

    /** Get the piece list model used for the promotion
     * @return the model used for setting all the pieces into which a pawn can be promoted
     */
    public PieceListModel getPromotionListModel() {
	return ssp.getPromotionListModel();
    }

}


