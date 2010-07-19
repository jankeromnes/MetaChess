package metachess.ai;

import metachess.boards.AIBoardTree;
import metachess.boards.ChessBoard;

public class AIThread extends Thread {
	private ChessBoard cb;
	private int treeDepth;
	public AIThread(ChessBoard board, int AILevel) {
		cb = board;
		treeDepth = AILevel;
	}
	@Override
	public void run() {
		
		long start = System.currentTimeMillis();
		AIBoardTree tree = new AIBoardTree(cb, treeDepth);
		long stop = System.currentTimeMillis();
		
		BestMoveSequence bms = tree.getBestMoveSequence();
		
		System.out.println("\nsequence ....... "+bms);
		System.out.println("depth .......... "+treeDepth);
		System.out.println("complexity ..... "+tree.getComplexity());
		System.out.println("timing(ms) ..... "+(stop-start));
		
		cb.playAIMove(bms.getFirstMove());
	}
}
