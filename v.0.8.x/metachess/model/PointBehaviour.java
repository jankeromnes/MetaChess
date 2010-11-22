package metachess.model;

import metachess.game.Coords;

/** Interface for all the objects that possess coordinates
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public interface PointBehaviour {

    /** Get the coordinates of this object
     * @return the coordinates
     */
    public Coords getCoords();
    
    /** Get the first coordinate of this object
     * @return the column (X Coord)
     */
    public int getColumn();

   /** Get the second coordinate of this object
     * @return the row (Y Coord)
     */
    public int getRow();

}



