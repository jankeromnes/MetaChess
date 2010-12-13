package metachess.panel.status;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metachess.library.Clock;
import metachess.library.Colour;
import metachess.library.PieceImages;
import metachess.loader.PieceImageLoader;
import metachess.model.ClockBehavior;

public class PlayerStatus extends JPanel implements ClockBehavior {

	private static final long serialVersionUID = 1L;
	private boolean white;
	private long uptime;
	private long total;
	private JLabel icon;
	private JLabel time;
	private Clock clock;
	
	public PlayerStatus(boolean white) {
		super();
		
		setPreferredSize(new Dimension(250, 50));
		PieceImageLoader.load();
		
		this.white = white;
		this.uptime = 0;
		this.total = 0;
		this.icon = new JLabel(PieceImages.getScaledImage("pawn", white, 30));
		this.time = new JLabel("00:00");
		
		add(icon);
		add(time);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(white?Colour.WHITE_BG.getColor():Colour.BLACK_BG.getColor());
	}
	
	public void setActivePlayer(boolean isWhite) {
		if (white == isWhite) {
			setBackground(Colour.GREEN.getColor());
			uptime = 0;
			clock = new Clock(this);
			clock.start();
		}
		else {
			setBackground(white ? Colour.WHITE_BG.getColor() : Colour.BLACK_BG.getColor());
			total += uptime;
			if(clock != null && clock.isAlive()) clock.interrupt();
			uptime = 0;
		}
	}

	@Override
	public void setCurrentTimeMillis(long millis) {
		uptime = millis; 
		int sec = (int)((uptime + total)/1000);
		int s = (sec%60);
		int mn = (sec/60)%60;
		time.setText((mn<10?"0"+mn:mn)+":"+(s<10?"0"+s:s));
	}

	public void clear() {
		setBackground(white ? Colour.WHITE_BG.getColor() : Colour.BLACK_BG.getColor());
		if(clock != null && clock.isAlive()) clock.interrupt();
		total = 0;
		uptime = 0;
	}

}
