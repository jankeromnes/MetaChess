package metachess.ai;

import metachess.boards.AIBoard;
import metachess.boards.AbstractBoard;
import metachess.logger.Move;

public class AIThread extends Thread {
	private AbstractBoard ab;
	public AIThread(AbstractBoard board) {
		ab = board;
	}
	public void run() {
		AIBoard aib = new AIBoard(ab);
		aib.generateProgeny(5, 7); // parameters are depth and breadth
		Move m = aib.getBestMove();
		System.out.println("Sequence : "+aib.getChild(0).getBestSequence());
		ab.playMove(m, true);
	}
}
