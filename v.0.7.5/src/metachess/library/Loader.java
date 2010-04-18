package metachess.library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import metachess.boards.AbstractBoard;
import metachess.game.MoveType;
import metachess.game.Piece;

public class Loader {

    private static Loader instance = new Loader();

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
			}
			
		    } else {

			if(word.length() == 3) {

			    char type = word.charAt(0);
			    char dir = word.charAt(1);
			    char range = word.charAt(2);
			    piece.addMoveType(new MoveType(type, dir, range));

			} else
			    System.out.println("Bad MoveType Format : "+word);

		    }

		}
		next = st.nextToken();

	    }

	    br.close();
	    Pieces.addPiece(piece);

	} catch(IOException e) {System.out.println(e);}	

    }


    public static void loadSetup(AbstractBoard abstractBoard, String file) {

	PiecesImages.load();

	try {

	     int pos = 0;
	     file = Resource.SETUPS.getPath(false)+file+".mcs";
	     BufferedReader br = new BufferedReader(new InputStreamReader(instance.getClass().getResourceAsStream(file)));

	     String line = br.readLine();
	     while(line.indexOf("{BEGIN}")==-1 && line != null) {
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
	     }
	     
	     abstractBoard.endInit();

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
		       //int h = b.getRows();
			 if(start == 'R') {
			     int n = Integer.parseInt(word.substring(1,len));
			     for(int i = 0 ; i < n ; i ++)
				 abstractBoard.setPiece(pos%w, pos++/w, p);
			 } else {
			     String name = word.substring(1,word.length()).toLowerCase();
			     if(!Pieces.hasPiece(name))
				 loadPiece(name);
			     p = Pieces.getPiece(name, start == 'W');
			     abstractBoard.setPiece(pos%w, pos++/w, p);
			}
		     
		     }
		 }
		next = st.nextToken();
	     }

	    br.close();
	    //b.updateAll();

	} catch(IOException e) { System.out.println(e); }

    }



}


