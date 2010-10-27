package metachess.game;

/** Class of a specific move available for a piece
 * Do not confuse with Move which refers to a played move
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class MoveType {

    private char type;
    private char range;

    private char dirchar;
    private int dir;

    private int step;
    private int offset;
    
    private float price;
    private boolean priceCalculated;
	
    private static boolean[] connectA = {true, true, true, true, true, true, true, true};
    private static boolean[] connectR = { false, true, false, true, true, false, true, false};
    private static boolean[] connectV = { false, true, false, false, false, false, true, false };
    private static boolean[] connectH = { false, false, false, true, true, false, false, false };
    private static boolean[] connectD = { true, false, true, false, false, true, false, true};
    private static boolean[] connectS = { true, false, false, false, false, false, false, true };
    private static boolean[] connectB = { false, false, true, false, false, true, false, false };

    /** Given a combo direction character, returns the boolean matrix of all the available directions of this combo ; essentially used by the PieceBuilder to create new custom moves
     * @param c the combo direction character
     * @return the matrix
     */
    public static boolean[] getDirectionConnectivityMatrixFromComboDirection(char c) {
	boolean[] connect = {false, false, false, false, false, false, false, false};
	if(c == 'A') connect = connectA;
	else if(c == 'R') connect = connectR;
	else if(c == 'V') connect = connectV;
	else if(c == 'H') connect = connectH;
	else if(c == 'D') connect = connectD;
	else if(c == 'S') connect = connectS;
	else if(c == 'B') connect = connectB;

	return connect;
	

    }

    /** Create a new sort of move
     * @param type the type of the move (Attack, Walk, Both, Jump, Pawnline)
     * @param dir the direction (usually a non-combo direction from '0' to '8')
     * @param range the range of the move
     */
    public MoveType(char type, char dir, char range, int step, int offset) {

	this.type = type;
	this.dir = dir - '0';
	this.range = range;
	this.step = step;
	this.offset = offset;

	dirchar = dir;
	priceCalculated = false;

    }


    // GETTERS
    

    /** The value of the MoveType <br/> The goal of this method is to approximate the
     * usual values given to classical chess pieces
     * @return its value as a float
     */
    public float getPrice() {
	assert(!isComboDirection()): "Not a valid combo direction";
    	if(!priceCalculated){
	    double rangeModifier = 0;
	    double typeModifier = 0;
	    double dirModifier = 0;
		
	    if(isWalkType()) typeModifier += 1;
	    if(isAttackType()) typeModifier += 2;
		
	    dirModifier = isDiagonalType() ? 2.35 : 3.7;
		
	    if(range == 'H') rangeModifier = 8;
	    else if(range == 'N') rangeModifier = 10.3;
	    else {
		double range = this.range - '0';
		if(range == 1) rangeModifier = 4.;
		else if(range == 2) rangeModifier = 6.;
		else if(range < 6) rangeModifier = 7.;
		else if(range < 8) rangeModifier = 8.;
		else rangeModifier = 9.;
	    }
		
	    price = (float)(typeModifier * rangeModifier * dirModifier);
	    if(isPawnType()) price /= 2;
    	}
	return price;
    }

    /** Get the move's type character
     * @return the move's type character
     */
    public char getType() { return type; }

    /** Get the move's range character
     * @return the move's range character
     */
    public char getRange() { return range; }

    /** Get the move's step value
     * @return the move's step value
     */
    public int getStep() { return step; }
  
    /** Get the move's offset value
     * @return the move's offset value
     */
    public int getOffset() { return offset; }
    
    /** Tells whether the move is a combo of several simple moves
     * @return true if it is
     */
    public boolean isComboDirection() {
	return dirchar < '0' || dirchar > '8';
    }

    /** Get the format used for the piece file (MCP format)
     * @return the format as a string
     */
    public String getMCPFormat() {
	return String.valueOf(type)+dirchar+range+step+offset;

    }

    // FOR COMBO DIRECTIONS

    /** Tells whether the combo direction includes / moves
     * @return true if it does
     */
    public boolean isSlash() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'S' || dirchar == 'D' || dirchar == 'A'; 
    }

    /** Tells whether the combo direction includes \ moves
     * @return true if it does
     */
    public boolean isBackSlash() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'B' || dirchar == 'D' || dirchar == 'A';
    }

    /** Tells whether the combo direction includes horizontal moves
     * @return true if it does
     */
    public boolean isHorizontally() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'H' || dirchar == 'R' || dirchar == 'A';
    }

    /** Tells whether the combo direction includes vertical moves
     * @return true if it does
     */
    public boolean isVertically() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'V' || dirchar == 'R' || dirchar == 'A';
    }


    // FOR BASIC DIRECTIONS

    /** Tells whether the move is a move in square
     * @return true if it is
     */
    public boolean isSquare() {
	return dir == 4;
    }

    /** For a given range, tells whether it is in range for the move
     * @return true if it is
     */
    public boolean isInRange(int r) {
	assert(!isComboDirection()): "Not a valid MoveType";
	return range == 'H' || range == 'N' || (r <= range - '0');
    }
    
    /** Tells whether the move allows the piece to walk
     * @return true if it does
     */
    public boolean isWalkType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return type == 'B' || type == 'W' || type == 'J' || type == 'P';
    }

    /** Tells whether the move allows the piece to attack
     * @return true if it does
     */
    public boolean isAttackType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return type == 'B' || type == 'A' || type == 'J';
    }

    /** Tells whether the move is a hopper-leaps kind of move
     * @return true if it is
     */
    public boolean isHopperType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return range == 'H';
    }

    /** Tells whether the move is a walk-type move from the pawnline
     * @return true if it is
     */
    public boolean isPawnType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return type == 'P';
    }

    /** Tells whether the move is in diagonal
     * @return true if it is
     */
    public boolean isDiagonalType() {
	if(isComboDirection())
	    return isBackSlash() || isSlash();
	else
	    return dir%2 == 0;
    }


    // FOR MOVEMENT ON THE BOARD

    /** Get the X coordinate of the vector associated with this move
     * @return the X Coord as an integer
     */
    public int getXDiff() {

	assert(!isComboDirection()): "Not a valid MoveType";

	int ret;

	if(type == 'J') {

	    switch(dir) {
	    case 1:
	    case 5:
		ret = 2;
		break;
	    case 8:
	    case 2:
		ret = 1;
		break;
	    case 6:
	    case 0:
		ret = -1;
		break;
	    case 7:
	    case 3:
		ret = -2;
		break;
	    default:
		ret = 0;
	    }

	} else
	    ret = dir%3 - 1;

	return ret;

    }

    /** Get the Y coordinate of the vector associated with this move
     * @return the Y Coord as an integer
     */
    public int getYDiff() {

	assert(!isComboDirection()): "Not a valid MoveType";

	int ret;
	
	if(type == 'J') {
	    switch(dir) {
	    case 6:
	    case 8:
		ret = 2;
		break;
	    case 5:
	    case 7:
		ret = 1;
		break;
	    case 3:
	    case 1:
		ret = -1;
		break;
	    case 0:
	    case 2:
		ret = -2;
		break;
	    default:
		ret = 0;
	    }
	} else
	    ret = dir/3 - 1;

	return ret;

    }

    /** Compare the move with another move to know if they're the same
     * @param o the move to compare
     * @return true if they have the same properties
     */
    @Override
	public boolean equals(Object o) {
	MoveType m = (MoveType)o;
	return type == m.type && range == m.range && dirchar == m.dirchar;
    }

    @Override
	public String toString() { 
	return String.valueOf(type)+'-'+dirchar+'-'+range+'-'+step+'-'+offset;
    }


}


