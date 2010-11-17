package metachess.panel.count;

import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metachess.library.Colour;
import metachess.library.PieceImages;
import metachess.loader.PieceImageLoader;

/** Class of the pieces count view for one color
 * @author Agbeladem (7DD)
 * @version 0.7.9
 */
public class CountView extends JPanel {

    private HashMap<String, JLabel> comp;
    private CountList list;
    private boolean white;

    /** Create a view of the pieces count
     * @param isWhite whether the color of this count is white
     */
    public CountView(boolean isWhite, CountList list) {

	comp = new HashMap<String, JLabel>();
	this.list = list;
	white = isWhite;

	setBackground((white ? Colour.BLACK_BG : Colour.WHITE_BG).getColor());
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	PieceImageLoader.load();

    }

    /** Update the view ie recreate all its labels ;
     * this is called when the count has changed
     */
    public void update() {
	removeAll();
	comp.clear();
	int w = (int)(getSize().getWidth());
 	for(String s : list.getSet(white)) {
	    JLabel lab = new JLabel(list.get(s, white).toString());
	    lab.setIcon(PieceImages.getScaledImage(s, white, w/3));
	    lab.setAlignmentX(CENTER_ALIGNMENT);
	    lab.setIconTextGap(w/6);
	    comp.put(s,lab);
	    add(lab);
	}
	repaint();
   }


    /* // Not functional painting method, used layout instead
    public void paint(Graphics g) {
	super.paint(g);
	int w = (int)(getSize().getWidth());
	for(String s : comp.keySet()) {
	    JLabel lab = comp.get(s);
	    lab.setAlignmentX(CENTER_ALIGNMENT);
	    lab.setIcon(PieceImages.getScaledImage(s, white, w/3));
	    lab.setIcon(PieceImages.getScaledImage((javax.swing.ImageIcon)lab.getIcon(), w/3));
	    lab.setIconTextGap(w/6);

	    lab.setFont(new Font(Font.SERIF, Font.PLAIN, w/6));
	}
	// setSize(new Dimension(w, StrictMath.max(300, w/2 * comp.size())-1));
    }
    */

}




