package metachess.boards;



public class SampleBoard extends AbstractBoard {

	private static final long serialVersionUID = 1L;

	public SampleBoard() {
		super();
		init("sample", false);
    }

    protected void initSquare(int i, int j) {
    	super.initSquare(i,j);
    }
    
}


