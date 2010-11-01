package metachess.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import metachess.game.MoveType;
import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.library.Resource;

/** Singleton of the Piece Loader
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class PieceLoader implements Loader {

    private static PieceLoader instance = new PieceLoader();

    @Override
     public void loadResource(String pieceName) {

	try {
	    Piece piece = new Piece();
	    piece.setName(pieceName);
	    String file = Resource.PIECES.getPath()+pieceName+".mcp";
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    StreamTokenizer st = new StreamTokenizer(br);
	    st.eolIsSignificant(true);
	    int next = st.nextToken();

	    while(next != StreamTokenizer.TT_EOF) {
		if(next == StreamTokenizer.TT_WORD) {
		    String word = st.sval;
		    if(word.charAt(0) == 'S') {
			switch(word.charAt(1)) {
			case 'J':
			    piece.setJoker(true);
			    break;
			case 'P':
			    piece.setPawn(true);
			    break;
			case 'K':
			    piece.setKing(true);
			    break;
			case 'R':
			    piece.setRook(true);
			    break;
			default:
			    System.out.println("Bad Special MoveType Format :"+word);
			}
			
		    } else {

			if(word.length() == 5) {
			    char type = word.charAt(0);
			    char dir = word.charAt(1);
			    char range = word.charAt(2);
			    int step = word.charAt(3)-'0';
			    int offset = word.charAt(4)-'0';
			    piece.addMoveType(new MoveType(type, dir, range, step, offset));

			} else
			    System.out.println("Bad MoveType Format : "+word);

		    }

		}
		next = st.nextToken();

	    }

	    br.close();
	    Pieces.addPiece(piece);

	} catch(IOException e) {
	    e.printStackTrace();
	}	

    }

    /** Load a specified piece
     * @param pieceName the name of the piece
     */
    public static void load(String pieceName) {
	instance.loadResource(pieceName);
    }

}


