package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

class OptionPanel extends JPanel {
	
	private Controller controller;
	
	public OptionPanel(Controller controller) {
		this.controller = controller;
		this.init();
	}
	
	public void init() {
		
		GridBagLayout gl = new GridBagLayout();
		this.setLayout(gl);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		JLabel numberLabel = new JLabel("Get bounce number by:");
		JComboBox<String> bounceNumberOptions = new JComboBox<String>();
		bounceNumberOptions.addItem("Pages");
		bounceNumberOptions.addItem("Time");

		JLabel rateLabel = new JLabel("Get bounce rate by:");
		JComboBox<String> bounceRateOptions = new JComboBox<String>();
		bounceRateOptions.addItem("Pages");
		bounceRateOptions.addItem("Time");
	
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets = new Insets(5, 0, 0, 10);
		this.add(numberLabel, cons);
		
		cons.gridy = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0, 0, 0, 10);
		this.add(bounceNumberOptions, cons);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.insets = new Insets(5, 10, 0, 0);
		this.add(rateLabel, cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(0, 10, 0, 0);
		this.add(bounceRateOptions, cons);
		
		SubmissionListener sub = new SubmissionListener(bounceNumberOptions, bounceRateOptions, controller);
		bounceNumberOptions.addActionListener(sub);
		bounceRateOptions.addActionListener(sub);
		
	}
	
}
