package metachess.count;

import java.awt.*;
import javax.swing.*;

public class Test {


    public static void main(String[] args) {

	JFrame f = new JFrame("test");

	f.add(new CountPanel());
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.pack();
	f.setVisible(true);

    }



}