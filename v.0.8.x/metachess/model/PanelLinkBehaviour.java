package metachess.model;

import java.util.ArrayList;

import metachess.game.Move;
import metachess.game.SavedGame;

/** Interface for all the links to the panel components
 * @author Agbeladem
 * @version 0.8.6
 */
public interface PanelLinkBehaviour {

    /** Load a saved game
     * @param sg the saved game model
     */
    public void loadGame(SavedGame sg);

    /** Get all the moves played in the game
     * @return the list of the moves as an ArrayList
     */ 
    public ArrayList<Move> getMoves();

    /** Clear the count panel and the logger
     * @param clear whether the logger should be cleared
     */
    public void clear(boolean clear);

    /** Add a move to the logger
     * @param m the move
     */
    public void addMove(Move m);

    /** Undo last move */
    public void undo();

    /** Redo last undone move */
    public void redo();

    /** Add a piece to the count list when it's been taken
     * @param pieceName the name of the taken piece
     * @param isWhite whether the piece's color is white
     */
    public void count(String pieceName, boolean isWhite);
    
    /** Update 
     * @param white whether the current player is white
     */
    public void setCurrentPlayer(boolean white);

}

