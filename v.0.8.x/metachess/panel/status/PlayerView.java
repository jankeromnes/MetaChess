package metachess.panel.status;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metachess.library.Colour;
import metachess.library.PieceImages;
import metachess.loader.PieceImageLoader;

public class PlayerView extends JPanel {
	
	public PlayerView(boolean white) {
		super();
		
		setPreferredSize(new Dimension(250, 50));
		
		PieceImageLoader.load();
		add(new JLabel(PieceImages.getScaledImage("pawn", white, 30)));
		add(new JLabel("00:00:00"));
		
    	if(white) setBackground(Colour.GREEN.getColor());
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

}
