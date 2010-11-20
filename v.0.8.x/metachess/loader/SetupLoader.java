package metachess.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import metachess.boards.AbstractBoard;
import metachess.boards.Area;
import metachess.dialog.ErrorDialog;
import metachess.exceptions.FileAccessException;
import metachess.exceptions.FileContentException;
import metachess.exceptions.LoadException;
import metachess.game.Piece;
import metachess.library.Pieces;
import metachess.library.Resource;

/** Singleton of the Setup Loader
 * @author Agbeladem (7DD)
 * @version 0.8.4
 */
public class SetupLoader extends VariableLoader {

    private static SetupLoader instance = new SetupLoader();
    private AbstractBoard board = null;

    @Override
    public void loadResource(String file) throws LoadException {

	assert board != null;
	PieceImageLoader.load();
	String name = Resource.SETUPS.getFullName(file);

	try {

	     int pos = 0;
	     file = Resource.SETUPS.getPath()+name;
	     this.file = file;
	     BufferedReader br = new BufferedReader(new FileReader(file));

	     // ----------------- //
	     // Loading variables //
	     // ----------------- //

	     String line = br.readLine();
	     boolean area = line.indexOf("{AREA}") != -1;
	     while(line.indexOf("{BEGIN}") == -1
		   && !area
		   && line != null) {
		 readVariable(line);
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
			 instance.board.add(new Area(line.substring(i+4, line.length()), instance.board));
		     line = br.readLine();
		 } 

	     }

	     instance.board.endInit();

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
			 int w = instance.board.getCols();
			 if(start == 'R') {
			     int n = Integer.parseInt(word.substring(1, len));
			     for(int i = 0 ; i < n ; i ++)
				 instance.board.setPiece(pos%w, pos++/w, p);
			 } else if(start == 'D') {
			     int n = Integer.parseInt(word.substring(1, len));
			     for(int i = 0; i < n ; i ++)
				 instance.board.removeSquare(pos%w, pos++/w);
			 } else {
			     String pieceName = word.substring(1,word.length()).toLowerCase();
			     if(!Pieces.hasPiece(pieceName))
				 PieceLoader.load(pieceName);
			     p = Pieces.getPiece(pieceName, start == 'W');
			     instance.board.setPiece(pos%w, pos++/w, p);
			 }
		     
		     } else if(next != StreamTokenizer.TT_EOL)
			 throw new FileContentException("Invalid Token value : "+next, name);
		 }
		next = st.nextToken();
	     }

	    br.close();

	    //b.updateAll();
	    /* // For version 1.1 !
	       // Test all the pieces for an area in variable "a"
	    if(a != null)
		for(metachess.boards.AbstractSquare s : instance.board)
		    if(s.hasPiece())
			System.out.println(s.getPiece().getName()+ (s.getPiece().isWhite()?'W':'B')
	    				   +" : "+a.containsSquare(s));
	    */

	} catch(IOException e) {
	    throw new FileAccessException(name);
	}

    }

    protected void setVariable(String var, String value) throws FileContentException {
	if(var.equals("width"))
	    board.setCols(Integer.parseInt(value));
	else if(var.equals("height"))
	    board.setRows(Integer.parseInt(value));
	else throw new FileContentException("Unknown variable \""+var+'"', file);
    }

    /** Load a specified setup in a given board
     * @param abstractBoard the board
     * @param file the name of the setup's file
     */
    public static void load(AbstractBoard abstractBoard, String file) {
	try {
	    instance.board = abstractBoard;
	    instance.loadResource(file);
	} catch(LoadException e) {
	    new ErrorDialog(e);
	}
    }


}

