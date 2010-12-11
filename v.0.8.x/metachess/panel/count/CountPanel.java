package metachess.panel.count;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/** Class of the Panel that contains both count views
 * @author Agbeladem (7DD)
 * @version 0.7.9
 */
public class CountPanel extends JScrollPane {

    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private final CountView white;
    private final CountView black;
    private CountList list;


    /** Create a panel of the count views */
    public CountPanel() {
	super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);

	setPreferredSize(new Dimension(250, 300));

	list = new CountList();

        white = new CountView(true, list);
	black = new CountView(false, list);
	panel = new JPanel();
	panel.add(white);
	panel.add(black);
	panel.setLayout(new GridLayout(1, 2));

	viewport.add(panel);

    }

    /** Clear this count list */
    public void clear() {
	list.clear();
	black.update();
	white.update();
	repaint();
    }


    /** Add a piece to the count list when it has been taken
     * @param pieceName the name of the taken piece
     * @param isWhite whether the piece's color is white
     */
    public void add(String pieceName, boolean isWhite) {
	list.add(pieceName, isWhite);
	(isWhite?white:black).update();
    }


    /*
    public void paint(Graphics g) {
	super.paint(g);

	Dimension d = getSize();
	int width = (int)d.getWidth()/2;
	int height = (int)d.getHeight()-3; // That "-3" part must be because of the borders

	d = new Dimension(width, height);

	white.setSize(d);
	black.setSize(d);

	panel.setPreferredSize(new Dimension(width*2+3, height));

    } 
    */

}

