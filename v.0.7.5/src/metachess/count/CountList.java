package metachess.count;

import java.util.HashMap;
import java.util.Set;

/** Class which contains the number of pieces lost depending on their name and color
 * @author Agbeladem (7DD)
 * @version 0.7.9
 */
public class CountList {

    private HashMap<String, Integer> whiteCount;
    private HashMap<String, Integer> blackCount;

    /** Create a list of the lost pieces count */
    public CountList() {
	whiteCount = new HashMap<String, Integer>();
	blackCount = new HashMap<String, Integer>();
    }

    private HashMap<String, Integer> getMap(boolean isWhite) {
	return isWhite? whiteCount: blackCount;
    }

    /** Get a set of all the pieces lost for a color
     * @param isWhite whether the color is white
     */
    public Set<String> getSet(boolean isWhite) {
	return getMap(isWhite).keySet();
    }
 
    /** Get the number of pieces lost depending on its name and color
     * @param pieceName the name of the piece
     * @param isWhite whether the piece's color is white
     */
    public Integer get(String pieceName, boolean isWhite) {
	return getMap(isWhite).get(pieceName);
    }

    /** Add a piece to this count list when it has been taken
     * @param pieceName the name of the piece
     * @param isWhite whether the piece's color is white
     */
    public void add(String pieceName, boolean isWhite) {
	HashMap<String, Integer> h = getMap(isWhite);
	int value = h.containsKey(pieceName)? h.get(pieceName).intValue() + 1 : 1;
	h.put(pieceName, new Integer(value));
    }

    /** Clear this count list */
    public void clear() {
	whiteCount.clear();
	blackCount.clear();
    }

}

