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

public class MovesSettingPanel extends JPanel {

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

    private final JRadioButton numRangeButton;
    private final JRadioButton maxRangeButton;
    private final JRadioButton hopperRangeButton;
    private final JSlider slider;

    private JComboBox jc;

    public MovesSettingPanel(MovesBox b) {
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
	slider = new JSlider(1, 9, 2);
	slider.setMinorTickSpacing(1);
	slider.setMajorTickSpacing(1);
	slider.setPaintTicks(true);
	slider.setPaintLabels(true);
	slider.setPaintTrack(true);
	slider.setSnapToTicks(true);

	JPanel sliderPanel = new JPanel();
	sliderPanel.add(slider);


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
		    slider.setEnabled(b);
		    if(b)
			setRangeValue(RangeForm.NUMERIC);
		    else if(o == maxRangeButton)
			setRangeValue(RangeForm.MAXIMUM);
		    else
			setRangeValue(RangeForm.HOPPER_STYLE);
		}
	    };
	slider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    setRangeValue(RangeForm.NUMERIC);
		}
	    });
	numRangeButton.addActionListener(ev);
	maxRangeButton.addActionListener(ev);
	hopperRangeButton.addActionListener(ev);


	numRangeButton.setAlignmentX(CENTER_ALIGNMENT);
	maxRangeButton.setAlignmentX(CENTER_ALIGNMENT);
	hopperRangeButton.setAlignmentX(CENTER_ALIGNMENT);
	sliderPanel.setAlignmentX(CENTER_ALIGNMENT);

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	add(Box.createVerticalGlue());
	add(new PanelTitle("Type of move"));
	add(comboPanel);
	add(new PanelTitle("Range of move"));
	add(numRangeButton);
	add(sliderPanel);
	add(maxRangeButton);
	add(hopperRangeButton);
	add(Box.createVerticalGlue());

    }


    public void init() {
	jc.setSelectedIndex(0);
	slider.setEnabled(false);
	maxRangeButton.setSelected(true);
	setRangeValue(RangeForm.MAXIMUM);	
	setTypeValue();
    }


    private void setTypeValue() {
	typeValue = ((MoveForm)jc.getSelectedItem()).getValue();
	parent.setWalking(typeValue != 'J');
    }

    private void setRangeValue(RangeForm r) {
	switch(r) {
	case NUMERIC:
	    rangeValue = (char)('0'+slider.getValue());
	    break;
	case MAXIMUM:
	    rangeValue = 'N';
	    break;
	case HOPPER_STYLE:
	    rangeValue = 'H';
	    break;
	}
    }


    public char getTypeValue() { return typeValue; }
    public char getRangeValue() { return rangeValue; }

}



