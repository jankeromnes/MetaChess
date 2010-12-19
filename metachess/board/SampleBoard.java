package metachess.board;

import metachess.model.PointBehaviour;

/** Class of a sample board <br/>.
 * This sample is used by the piece builder box
 * @author Agbeladem
 * @version 0.8.3
 */
public class SampleBoard extends PlayableBoard {

    private static final long serialVersionUID = 1L;

    /** Create a sample board */
    public SampleBoard() {
	super();
	init("sample");
    }

    @Override
    public void playSquare(PointBehaviour b) {};


    
}


