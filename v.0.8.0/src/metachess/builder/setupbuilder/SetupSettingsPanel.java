package metachess.builder.setupbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

import metachess.builder.PanelTitle;

/** Class of the Settings Panel in the Setup Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */
public class SetupSettingsPanel extends JPanel {

  
    private static final long serialVersionUID = 1L;
    private final SetupBuilderBox parent;
    private final JButton apply;
    private final JSlider widthSlider;
    private final JSlider heightSlider;

    /** Creation of a Setup Settings Panel 
     * @param box the Setup Builderbox to which this panel belongs
     */
    public SetupSettingsPanel(SetupBuilderBox box) {
	super();

	parent = box;



	widthSlider = new JSlider(1, 16, 8);
	widthSlider.setMinorTickSpacing(1);
	widthSlider.setMajorTickSpacing(5);
	widthSlider.setPaintTicks(true);
	widthSlider.setPaintLabels(true);
	widthSlider.setPaintTrack(true);
	widthSlider.setSnapToTicks(true);



	heightSlider = new JSlider(1, 16, 8);
	heightSlider.setMinorTickSpacing(1);
	heightSlider.setMajorTickSpacing(5);
	heightSlider.setPaintTicks(true);
	heightSlider.setPaintLabels(true);
	heightSlider.setPaintTrack(true);
	heightSlider.setSnapToTicks(true);



	apply = new JButton("Apply Changes");
	apply.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    parent.setBoardDimensions(widthSlider.getValue(), heightSlider.getValue());
		}
	    });

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	add(new PanelTitle("Board's width"));
	add(widthSlider);
	add(new PanelTitle("Board's height"));
	add(heightSlider);

	add(apply);

    }

}