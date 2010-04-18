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
		//aib.generateProgeny(3, 10); // faster
		aib.generateProgeny(5, 5); // stronger
		Move m = aib.getBestMove();
		System.out.println("Sequence : "+aib.getChild(0).getBestSequence());
		ab.playMove(m, true);
	}
}
