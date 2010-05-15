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
		
		System.out.println(bms);
		System.out.println("Depth : "+treeDepth);
		System.out.println("Complexity : "+tree.getComplexity());
		System.out.println("Timing (ms) : "+(stop-start));
		
		// debug move values
		/*StringBuilder sb = new StringBuilder();
		sb.append(bms.getFirstMove().getOldX());
		sb.append(" / ");
		sb.append(bms.getFirstMove().getOldY());
		sb.append(" / ");
		sb.append(bms.getFirstMove().getNewX());
		sb.append(" / ");
		sb.append(bms.getFirstMove().getNewY());
		System.out.println(sb.toString());*/
		
		cb.playAIMove(bms.getFirstMove());
	}
}
