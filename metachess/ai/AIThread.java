package metachess.ai;

import metachess.board.ChessBoard;

/** Class of an Artificial Intelligence Thread
 * @author Jan (7DD)
 * @version 0.8.3
 */
public class AIThread extends Thread {
	
    private ChessBoard cb;
    private int treeDepth;
	
    /** Create an AI Thread to calculate a best-move-sequence from a given position to a given depth
     * @param board the Chess Board with its current position
     * @param AILevel the depth of the recursive study
     */
    public AIThread(ChessBoard board, int AILevel) {
	cb = board;
	treeDepth = AILevel;
    }
	
    /** Launch the Thread and print some info about the best-move-sequence found
     * and the complexity of the search */
    @Override
    public void run() {
		
	long start = System.currentTimeMillis();
	AITree tree = new AITree(cb, treeDepth);
	long stop = System.currentTimeMillis();
			
	BestMoveSequence bms = tree.getBestMoveSequence();
			
	System.out.println("\nsequence ....... "+bms);
	System.out.println("depth .......... "+treeDepth);
	System.out.println("complexity ..... "+tree.getComplexity());
	System.out.println("timing(ms) ..... "+(stop-start));
	
	cb.playAIMove(bms.getFirstMove());
		
    }
}

