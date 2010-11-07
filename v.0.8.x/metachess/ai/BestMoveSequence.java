package metachess.ai;

import java.util.Vector;

import metachess.game.Move;

/** Class of a Best-Moves Sequence, calculated by an AI Board Tree
 * @author Jan (7DD)
 * @version 0.8.0
 */
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
	for(Move move : sequence) sb.append(move.getOldCoords()+" => "+move.getNewCoords()+" | ");
	sb.delete(sb.length()-2, sb.length());
	sb.append("(final score : ");
	sb.append(score);
	sb.append(")");
	return sb.toString();
    }

}
