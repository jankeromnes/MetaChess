package metachess.panel.status;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import metachess.library.Clock;
import metachess.library.Colour;
import metachess.library.PieceImages;
import metachess.loader.PieceImageLoader;
import metachess.model.Synchron;

public class PlayerStatus extends JPanel implements Synchron {

	private static final long serialVersionUID = 1L;
	private boolean white;
	private boolean playing;
	private long start;
	private long total;
	
	private JPanel top;
	private JLabel icon;
	private JLabel time;
	private JProgressBar percent;
	
	public PlayerStatus(boolean isWhite) {
		super();
		
		setPreferredSize(new Dimension(125, 45));
		PieceImageLoader.load();

		white = isWhite;
		clear();
		icon = new JLabel(PieceImages.getScaledImage("pawn", white, 30));
		time = new JLabel("00:00");
		percent = new JProgressBar(0);
		
		
		top = new JPanel();
		top.add(icon);
		top.add(time);
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.setOpaque(false);
		
		add(top);
		add(percent);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void clear() {
		setBackground((white ? Colour.WHITE_BG : Colour.BLACK_BG).getColor());
		playing = false;
		total = 0;
		start = System.currentTimeMillis();
	}
	
	public void setActivePlayer(boolean isWhite) {
		percent.setValue(0);
		if (white == isWhite) {
			start = System.currentTimeMillis();
			playing = true;
			Clock.susbscribe(this);
			setBackground(Colour.GREEN.getColor());
		}
		else {
			playing = false;
			Clock.unsubscribe(this);
			setBackground((white ? Colour.WHITE_BG : Colour.BLACK_BG).getColor());
		}
	}

	@Override
	public void synchronize() {
		total += System.currentTimeMillis() - start;
		start = System.currentTimeMillis();
		int sec = (int)((total)/1000);
		int s = (sec%60);
		int mn = (sec/60)%60;
		time.setText((mn<10?"0"+mn:mn)+":"+(s<10?"0"+s:s));
	}

	public void updateAIPercentage(float percentage) {
		if(playing) percent.setValue((int)percentage);
	}

}
