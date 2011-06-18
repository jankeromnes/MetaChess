package metachess.model;

import java.util.ArrayList;

import metachess.game.Move;

/** Interface for all the properties of a game
 * @author Agbeladem (7DD)
 * @version 0.8.6
 */
public interface GameBehaviour {
    
    // MOVES

    /** Add a move to the logger
     * @param m the move
     */
    public void addMove(Move m);

    /** Get all the moves played in this game
     * @return the list of the moves as an ArrayList
     */ 
    public ArrayList<Move> getMoves();

    // SETTERS

    /** Set whether this game is played with atomic rules
     * @param isAtomic whether the game shall be played with atomic rules or not
     */
    public void setAtomic(boolean isAtomic);

    /** Set the setup on which the game is being played
     * @param setup the name of the setup
     */
    public void setSetup(String setup);

    /** Set the Black AI Level
     * @param l the level
     */
    public void setBlackAILevel(int l);

    /** Set the White AI Level
     * @param l the level
     */    
    public void setWhiteAILevel(int l);

    // GETTERS
    
    /** Tell whether this game is played with atomic rules
     * @return true if it is
     */
    public boolean isAtomic();

    /** Tell on what setup the game is based
     * @return the name of the setup
     */
    public String getSetup();
    
    /** Get the level of the Black AI
     * @return the level as an integer
     */
    public int getBlackAILevel();

    /** Get the level of the White AI
     * @return the level as an integer
     */
    public int getWhiteAILevel();

}

