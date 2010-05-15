package metachess.ai;

import java.util.Vector;

import metachess.logger.Move;

public class BestMoveSequence {
	
	private Vector<Move> sequence;
	private float score;
	
	public BestMoveSequence(Move move, float score) {
		sequence = new Vector<Move>();
		sequence.add(move);
		this.score = score;
	}
	
	public BestMoveSequence(Move move, BestMoveSequence subsequence) {
		sequence = new Vector<Move>();
		sequence.add(move);
		sequence.addAll(subsequence.getSequence());
		score = subsequence.getScore();
	}

	public Vector<Move> getSequence() {
		return sequence;
	}
	
	public Move getFirstMove() {
		return sequence.get(0);
	}

	public float getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Move move : sequence) sb.append(move+" ; ");
		sb.delete(sb.length()-3, sb.length());
		return sb.toString();
	}

}
