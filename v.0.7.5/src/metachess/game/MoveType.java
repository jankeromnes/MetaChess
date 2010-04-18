package metachess.game;

public class MoveType {

    char type;
    char range;

    char dirchar;
    int dir;
    
    float price;
    boolean priceCalculated;

	
    private static boolean[] connectA = {true, true, true, true, true, true, true, true};
    private static boolean[] connectR = { false, true, false, true, true, false, true, false};
    private static boolean[] connectV = { false, true, false, false, false, false, true, false };
    private static boolean[] connectH = { false, false, false, true, true, false, false, false };
    private static boolean[] connectD = { true, false, true, false, false, true, false, true};
    private static boolean[] connectS = { true, false, false, false, false, false, false, true };
    private static boolean[] connectB = { false, false, true, false, false, true, false, false };

    public static boolean[] getDirectionConnectivityFromComboDirection(char c) {
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


    public MoveType(char type, char dir, char range) {

	this.type = type;
	this.dir = (int)(dir - '0');
	this.range = range;

	dirchar = dir;
	priceCalculated = false;

    }


    // GETTERS
    

    /** The value of the MoveType
     * @return its value as an integer
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

    public char getType() { return type; }
    public char getRange() { return range; }

    public boolean isComboDirection() {
	return dirchar < '0' || dirchar > '8';
    }

    public String getMCPFormat() {
	return String.valueOf(type)+dirchar+range;

    }

    public String toString() { 
	return String.valueOf(type)+'-'+dirchar+'-'+range;
    }

    // FOR COMBO DIRECTIONS

    public boolean isSlash() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'S' || dirchar == 'D' || dirchar == 'A'; 
    }
    public boolean isBackSlash() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'B' || dirchar == 'D' || dirchar == 'A';
    }
    public boolean isHorizontally() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'H' || dirchar == 'R' || dirchar == 'A';
    }
    public boolean isVertically() {
	assert(isComboDirection()): "Not a valid combo direction";
	return dirchar == 'V' || dirchar == 'R' || dirchar == 'A';
    }


    // FOR BASIC DIRECTIONS

    public boolean isInRange(int r) {
	assert(!isComboDirection()): "Not a valid MoveType";
	return range == 'H' || range == 'N' || (r <= range - '0');
    }
    
    public boolean isWalkType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return type == 'B' || type == 'W' || type == 'J' || type == 'P';
    }

    public boolean isAttackType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return type == 'B' || type == 'A' || type == 'J';
    }

    public boolean isHopperType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return range == 'H';
    }

    public boolean isPawnType() {
	assert(!isComboDirection()): "Not a valid MoveType";
	return type == 'P';
    }

    public boolean isDiagonalType() {
	if(isComboDirection())
	    return isBackSlash() || isSlash();
	else
	    return dir%2 == 0;
    }


    // FOR MOVEMENT ON THE BOARD

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


    public boolean equals(Object o) {
	MoveType m = (MoveType)o;
	return type == m.type && range == m.range && dirchar == m.dirchar;
    }

 

}


