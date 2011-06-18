package metachess.model;

/** Interface for all the special move types possible for a piece
 * @author Agbeladem (7DD)
 * @version 0.8.5
 */
public interface PieceBehaviour {

    // SETTERS

    /** Set whether the created piece is a joker
     * @param b true if it is
     */
    public void setJoker(boolean b);

    /** Set whether the created piece is a rook
     * @param b true if it is
     */
    public void setRook(boolean b);

    /** Set whether the created piece is a king
     * @param b true if it is
     */
    public void setKing(boolean b);

     /** Set whether the created piece is a pawn
     * @param b true if it is
     */
    public void setPawn(boolean b);

    // GETTERS

    /** Tell whether the created piece is a joker
     * @return true if it is
     */
    public boolean isJoker();

        /** Tell whether the created piece is a rook (and is thus able to do the castle move)
     * @return true if it is
     */
    public boolean isRook();

    /** Tell whether the created piece is a king
     * @return true if it is
     */
    public boolean isKing();

    /** Tell whether the created piece is a pawn
     * @return true if it is
     */
    public boolean isPawn();

}
