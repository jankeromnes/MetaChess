package metachess.panel.status;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metachess.library.Colour;
import metachess.library.PieceImages;
import metachess.loader.PieceImageLoader;

public class PlayerStatus extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean white;
	
	public PlayerStatus(boolean white) {
		super();
		
		this.white = white;
		
		setPreferredSize(new Dimension(250, 50));
		
		PieceImageLoader.load();
		add(new JLabel(PieceImages.getScaledImage("pawn", white, 30)));
		add(new JLabel("00:00:00"));
		
    	setBackground(white?Colour.WHITE_BG.getColor():Colour.BLACK_BG.getColor());
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	public void setActivePlayer(boolean isWhite) {
		setBackground((white == isWhite)?Colour.GREEN.getColor():(white?Colour.WHITE_BG.getColor():Colour.BLACK_BG.getColor()));
	}

}
