package metachess.builder.piecebuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import metachess.builder.PanelTitle;

/** Class of the Settings Pannel in the Moves Builderbox
 * @author Agbeladem (7DD)
 * @version 0.8.2
 */
public class MovesSettingsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    enum MoveForm {
	WALK("Walk", 'W'),
        ATTACK("Attack", 'A'),
	BOTH("Both Walk and Attack", 'B'),
	JUMP("Jump like a knight", 'J'),
	PAWNLINE("Walk from the pawns line", 'P');
	
	MoveForm(String name, char value) {
	    this.name = name;
	    this.value = value;
	}

	private final String name;
	private final char value;
	@Override
	public String toString() { return name; }
	public char getValue() { return value; }
    }

    enum RangeForm {
	NUMERIC, MAXIMUM, HOPPER_STYLE;
    }


    private MovesBox parent;

    private char rangeValue;
    private char typeValue;
    private int stepValue;
    private int offsetValue;

    private final JRadioButton numRangeButton;
    private final JRadioButton maxRangeButton;
    private final JRadioButton hopperRangeButton;
    private final JSlider numSlider;
    private final JSlider offsetSlider;
    private final JSlider stepSlider;

    private JComboBox jc;

    /** Creation of Moves Settings Panel
     * @param b the Moves Builderbox to which this panel belongs
     */
    public MovesSettingsPanel(MovesBox b) {
	super();
	parent = b;
	

	jc = new JComboBox(MoveForm.values());
	jc.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    setTypeValue();
		}

	    });
      	JPanel comboPanel = new JPanel();
    	comboPanel.add(jc);
	setTypeValue();

	numSlider = new JSlider(1, 9, 2);
	numSlider.setMinorTickSpacing(1);
	numSlider.setMajorTickSpacing(1);
	numSlider.setPaintTicks(true);
	numSlider.setPaintLabels(true);
	numSlider.setPaintTrack(true);
	numSlider.setSnapToTicks(true);

	stepValue = 1;
	stepSlider = new JSlider(1, 9, 1);
	stepSlider.setMinorTickSpacing(1);
	stepSlider.setMajorTickSpacing(1);
	stepSlider.setPaintTicks(true);
	stepSlider.setPaintLabels(true);
	stepSlider.setPaintTrack(true);
	stepSlider.setSnapToTicks(true);

	offsetValue = 0;
	offsetSlider = new JSlider(0, 8, 1);
	offsetSlider.setMinorTickSpacing(1);
	offsetSlider.setMajorTickSpacing(1);
	offsetSlider.setPaintTicks(true);
	offsetSlider.setPaintLabels(true);
	offsetSlider.setPaintTrack(true);
	offsetSlider.setSnapToTicks(true);

	
	JPanel numSliderPanel = new JPanel();
	numSliderPanel.add(numSlider);
	JPanel stepSliderPanel = new JPanel();
	stepSliderPanel.add(stepSlider);
	JPanel offsetSliderPanel = new JPanel();
	offsetSliderPanel.add(offsetSlider);


	ButtonGroup bg = new ButtonGroup();
	numRangeButton = new JRadioButton("Numeric Range :");
	maxRangeButton = new JRadioButton("Maximum Range");
	hopperRangeButton = new JRadioButton("Grasshopper-style");
	bg.add(numRangeButton);
	bg.add(maxRangeButton);
	bg.add(hopperRangeButton);
	ActionListener ev = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Object o = e.getSource();
		    boolean b = o == numRangeButton;
		    numSlider.setEnabled(b);
		    if(b)
			setRangeValue(RangeForm.NUMERIC);
		    else if(o == maxRangeButton)
			setRangeValue(RangeForm.MAXIMUM);
		    else
			setRangeValue(RangeForm.HOPPER_STYLE);
		}
	    };

	numSlider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    setRangeValue(RangeForm.NUMERIC);
		}
	    });
	stepSlider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    stepValue = stepSlider.getValue();
		}
	    });
	offsetSlider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    offsetValue = offsetSlider.getValue();
		}
	    });


	numRangeButton.addActionListener(ev);
	maxRangeButton.addActionListener(ev);
	hopperRangeButton.addActionListener(ev);


	numRangeButton.setAlignmentX(CENTER_ALIGNMENT);
	maxRangeButton.setAlignmentX(CENTER_ALIGNMENT);
	hopperRangeButton.setAlignmentX(CENTER_ALIGNMENT);
	numSliderPanel.setAlignmentX(CENTER_ALIGNMENT);
	stepSliderPanel.setAlignmentX(CENTER_ALIGNMENT);
	offsetSliderPanel.setAlignmentX(CENTER_ALIGNMENT);

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	add(Box.createVerticalGlue());
	add(new PanelTitle("Type of move"));
	add(comboPanel);
	add(new PanelTitle("Range of move"));
	add(numRangeButton);
	add(numSliderPanel);
	add(maxRangeButton);
	add(hopperRangeButton);
	add(new PanelTitle("Step for the move"));
	add(stepSliderPanel);
	add(new PanelTitle("Offset for the move"));
	add(offsetSliderPanel);
	add(Box.createVerticalGlue());

    }

    /** (Re)initialize this Settings Panel */
    public void init() {
	jc.setSelectedIndex(0);
	numSlider.setEnabled(false);
	maxRangeButton.setSelected(true);
	setRangeValue(RangeForm.MAXIMUM);	
	setTypeValue();
	stepValue = stepSlider.getValue();
	offsetValue = offsetSlider.getValue();
    }


    private void setTypeValue() {
	typeValue = ((MoveForm)jc.getSelectedItem()).getValue();
	parent.setWalking(typeValue != 'J');
    }

    private void setRangeValue(RangeForm r) {
	switch(r) {
	case NUMERIC:
	    rangeValue = (char)('0'+numSlider.getValue());
	    break;
	case MAXIMUM:
	    rangeValue = 'N';
	    break;
	case HOPPER_STYLE:
	    rangeValue = 'H';
	    break;
	}
    }


    /** Get the type of move that has been selected in this panel
     * @return the type of move, as an MCP character
     */
    public char getTypeValue() {
	return typeValue;
    }

    /** Get the range of move that has been selected in this panel
     * @return the range of move, as an MCP character
     */
    public char getRangeValue() {
	return rangeValue;
    }

    /** Get the step chosen for this move in this panel
     * @return the step of this move
     */
    public int getStepValue() {
	return stepValue;
    }

    /** Get the offset chosen for this move in this panel
     * @return the offset of this move
     */
    public int getOffsetValue() {
	return offsetValue;
    }

}



