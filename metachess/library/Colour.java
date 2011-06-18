package metachess.library;

import java.awt.Color;

public enum Colour {
    
    BLACK(0, 0, 0, 255),
	WHITE(255, 255, 255, 255),
	BLACK_BG(153, 102, 0, 255),
	WHITE_BG(255, 255, 153, 255),
	GREEN(173, 255, 173, 255),
	BLUE(160, 190, 232, 255),
	DARK_BLUE(100, 130, 232, 255),
	GREY(220, 220, 220, 255), 
	DARK_GREY(80, 80, 80, 255), 
	SALMON(220,195,185, 255);
    private Color color;

    private Colour(int r, int g, int b, int a) {
	color = new Color(r,g,b,a);
    }

    public Color getColor() {
	return color;
    }

}