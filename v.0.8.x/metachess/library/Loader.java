package metachess.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import metachess.boards.AbstractBoard;
import metachess.boards.Area;
import metachess.game.MoveType;
import metachess.game.Piece;

/** Class of the Resource Loader (exclusively by static methods)
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public class Loader {

    private static Loader instance = new Loader();

    /** Load a specified piece
     * @param pieceName the name of the piece
     */
    public static void loadPiece(String pieceName) {

	try {
	    Piece piece = new Piece();
	    piece.setName(pieceName);
	    String file = Resource.PIECES.getPath(false)+pieceName+".mcp";
	    BufferedReader br = new BufferedReader(new InputStreamReader(instance.getClass().getResourceAsStream(file)));
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
	    System.out.println(e);
	}	

    }

    /** Load a specified setup in a given board
     * @param abstractBoard the board
     * @param file the name of the setup's file
     */
    public static void loadSetup(AbstractBoard abstractBoard, String file) {
	PieceImages.load();

	try {

	     int pos = 0;
	     file = Resource.SETUPS.getPath(false)+file+".mcs";
	     BufferedReader br = new BufferedReader(new InputStreamReader(instance.getClass().getResourceAsStream(file)));

	     // ----------------- //
	     // Loading variables //
	     // ----------------- //

	     String line = br.readLine();
	     boolean area = line.indexOf("{AREA}") != -1;
	     while(line.indexOf("{BEGIN}") == -1
		   && !area
		   && line != null) {
		 int i = line.indexOf('#');
		 if(i != -1)
		     line = line.substring(0,i);
		 line = line.replaceAll("\\s","");
		 i = line.indexOf('=');
		 if(i != -1) {
		     String var = line.substring(0,i);
		     String value = line.substring(i+1, line.length());
		     if(var.equals("width"))
			 abstractBoard.setCols(Integer.parseInt(value));
		     else if(var.equals("height"))
			 abstractBoard.setRows(Integer.parseInt(value));
		 }
		 line = br.readLine();
		 area = line.indexOf("{AREA}") != -1;
	     }

	     // ------------- //
	     // Loading areas //
	     // ------------- //


	     if(area) {
		 line = br.readLine();
		 int i;
		 while(line != null && line.indexOf("{BEGIN}") == -1) {
		     i = line.indexOf("area");
		     if(i != -1)
			 abstractBoard.add(new Area(line.substring(i+4, line.length()), abstractBoard));
		     line = br.readLine();
		 } 

	     }

	     abstractBoard.endInit();

	     // ------------- //
	     // Loading board //
	     // ------------- //

	     StreamTokenizer st = new StreamTokenizer(br);
	     st.wordChars('_', '_'+1);
	     st.eolIsSignificant(true);
	     int next = st.nextToken();
	     Piece p = null;
	     while(next != StreamTokenizer.TT_EOF) {
		 if(next == StreamTokenizer.TT_NUMBER)
		     pos += (int)st.nval;
		 else if(next == StreamTokenizer.TT_WORD) {
		     String word = st.sval;
		     int len = word.length();
		     if(len > 1) {
			 char start = word.charAt(0);
			 int w = abstractBoard.getCols();
			 if(start == 'R') {
			     int n = Integer.parseInt(word.substring(1, len));
			     for(int i = 0 ; i < n ; i ++)
				 abstractBoard.setPiece(pos%w, pos++/w, p);
			 } else if(start == 'D') {
			     int n = Integer.parseInt(word.substring(1, len));
			     for(int i = 0; i < n ; i ++)
				 abstractBoard.removeSquare(pos%w, pos++/w);
			 } else {
			     String name = word.substring(1,word.length()).toLowerCase();
			     if(!Pieces.hasPiece(name))
				 loadPiece(name);
			     p = Pieces.getPiece(name, start == 'W');
			     abstractBoard.setPiece(pos%w, pos++/w, p);
			 }
		     
		     } else {
			 // exception...
		     }
		 }
		next = st.nextToken();
	     }

	    br.close();
	    //b.updateAll();

	    /*
	      // Test all the pieces for an area in variable "a"
	    if(a != null)
		for(metachess.boards.AbstractSquare s : abstractBoard)
		    if(s.hasPiece())
			System.out.println(s.getPiece().getName()+ (s.getPiece().isWhite()?'W':'B')
	    				   +" : "+a.containsSquare(s));
	    */
	} catch(IOException e) {
	    e.printStackTrace();
	}

    }



}


