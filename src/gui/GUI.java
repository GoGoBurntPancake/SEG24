package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableColumn;

import controller.Controller;
import plotter.Plotter;//Wrong architecture!

public class GUI {

	private Controller controller;
	
	public GUI(Controller controller){
		this.controller = controller;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				display();
			}
		});
	}
		
	private void display(){
		
		JFrame frame = new JFrame();
		frame.setTitle("Ad Auction Dashboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel dataPane = new DataPanel(this.controller);
<<<<<<< HEAD:src/gui/GUI.java
		JPanel chartsPane = new ChartsPanel(this.controller);
//		JPanel metricsPane = new MetricsPanel(this.controller);
		OptionPanel optionPane = new OptionPanel(this.controller);
		optionPane.init();
=======
>>>>>>> 087c3d93a274ea6f37e5234d91d5af61195beb2e:src/gui/TestGUI.java

		JPanel chartsPane = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 0;
		ChartsPanel chartPanel = new ChartsPanel(this.controller);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartsPane.add(new ChartsPanel(this.controller), cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		chartsPane.add(new MetricsPanel(this.controller), cons);
		
		tabbedPane.addTab("Data", dataPane);
		tabbedPane.addTab("Charts", chartsPane);
<<<<<<< HEAD:src/gui/GUI.java
//		tabbedPane.addTab("Metrics", metricsPane);
		tabbedPane.addTab("Options", optionPane);
=======
>>>>>>> 087c3d93a274ea6f37e5234d91d5af61195beb2e:src/gui/TestGUI.java
		
		frame.setContentPane(tabbedPane);
		frame.setResizable(true);
	
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
}

class DataPanel extends JPanel{
	
	JButton submitButton;
	JButton clickButton;
	JButton impressionButton;
	JButton serverButton;
	JTextField clickField;
	JTextField impressionField;
	JTextField serverField;
	JTextField errorField;
	private JFileChooser fc;
	private GridBagConstraints gbc;
	private Controller controller;
	
	DataPanel(Controller controller){
		
		this.controller = controller;
		this.clickButton = new JButton("Choose Click Log");
		this.impressionButton = new JButton("Choose Impression Log");
		this.serverButton = new JButton("Choose Server Log");
		this.clickField = new JTextField(50);
		this.impressionField = new JTextField(50);
		this.serverField = new JTextField(50);
		this.errorField = new JTextField(50);
		this.submitButton = new JButton("Submit");
		
		this.clickField.setEditable(false);
		this.impressionField.setEditable(false);
		this.serverField.setEditable(false);
		this.errorField.setEditable(false);
		
		this.setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.fc = new JFileChooser();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.add(this.clickButton, gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		this.add(this.clickField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.add(this.impressionButton, gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		this.add(this.impressionField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(this.serverButton, gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		this.add(this.serverField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(this.submitButton, gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		this.add(this.errorField, gbc);
		
		FileFinder ff = new FileFinder();
		//ff.setNameToSearch(fc.getCurrentDirectory().getAbsolutePath());
		ff.searchDirectory(fc.getCurrentDirectory(), "impression_log.csv", "click_log.csv", "server_log.csv");
		
		//int impfound = 0, clickfound = 0, serverfound = 0;
		
		for (String matched : ff.foundFiles){
			System.out.println("Found: " + matched);
		
			if (matched.endsWith("impression_log.csv")){
				//impfound++;
				this.impressionField.setText(matched);
			} else if (matched.endsWith("click_log.csv")){
				//clickfound++;
				this.clickField.setText(matched);
			} else if (matched.endsWith("server_log.csv")){
				//serverfound++;
				this.serverField.setText(matched);
			}
		}		
		
		
		this.clickButton.addActionListener(new DataListener(this.controller, this));
		this.impressionButton.addActionListener(new DataListener(this.controller, this));
		this.serverButton.addActionListener(new DataListener(this.controller, this));
		this.submitButton.addActionListener(new DataListener(this.controller, this));
		
	}
	
}

class MetricsPanel extends JPanel{
	
	private Controller controller;
	private JButton calculateButton;
	private JTable metricTable, costTable;
	private JLabel[] impressionNumber, clickNumber, uniqueNumber, bounceNumber,
					 conversionNumber, ctr, cpa, cpc, cpm, bounceRate, totalCost;
	
	final int PER_HOUR = 1, PER_DAY = 2, NO_OF_IMPRESSIONS = 0, NO_OF_CLICKS = 1,
			NO_OF_UNIQUES = 2, NO_OF_BOUNCES = 3, NO_OF_CONVERSIONS = 4, CTR = 5,
			CPA = 6, CPC = 7, CPM = 8, BOUNCE_RATE = 9;
	
	MetricsPanel(Controller controller){
		this.controller = controller;
		this.calculateButton = new JButton("Calculate Metrics");
		
		GridLayout gl = new GridLayout(0, 2);
		this.setLayout(gl);
		
		Object[] metricColumnNames = {
				"Metrics",
				"Per Hour",
				"Per Day"
		};
		
		Object[] costColumnNames = {
				"Total Campaign Cost of",
				"Amount (GBP)"
		};
		
		Object[][] metricData = {
				{ 	"Number of Impressions:", "0", "0"	},
				{ 	"Number of Clicks:", "0", "0"	},
				{ 	"Number of Uniques:", "0", "0"	},
				{ 	"Number of Bounces:", "0", "0"	},
				{ 	"Number of Conversions:", "0", "0"	},
				{ 	"Click-through rate (%):", "0", "0"	},
				{ 	"Cost-per-aquisition (GB Pence):", "0", "0"	},
				{ 	"Cost-per-click (GB Pence):", "0", "0"	},
				{ 	"Cost-per-thousand-impressions (GB Pence):", "0", "0"	},
				{ 	"Bounce Rate (%):", "0", "0"	}
		};
		
		Object[][] costData = {
				{	"Impressions", "0"	},
				{	"Clicks",	"0"			}
		};
<<<<<<< HEAD:src/gui/GUI.java
/*
		MetricTableModel tableModel = new MetricTableModel(data, columnNames);
		metricTable = new JTable(tableModel);
=======
		
		MetricTableModel metricModel = new MetricTableModel(metricData, metricColumnNames);
		metricTable = new JTable(metricModel);
		
		MetricTableModel costModel = new MetricTableModel(costData, costColumnNames);
		costTable = new JTable(costModel);
>>>>>>> 087c3d93a274ea6f37e5234d91d5af61195beb2e:src/gui/TestGUI.java
		
		TableColumn column = null;
		
		column = metricTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(300);
		column = metricTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(75);
		column = metricTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(75);
		
		column = costTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
		column = costTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(150);
		
		Dimension dim = new Dimension(550, 182);
		JScrollPane sp = new JScrollPane(metricTable);
		sp.setSize(dim);
		sp.setMaximumSize(dim);
		sp.setMinimumSize(dim);
		sp.setPreferredSize(dim);
		
		dim = new Dimension(320, 54);
		JScrollPane costPane = new JScrollPane(costTable);
		costPane.setSize(dim);
		costPane.setMaximumSize(dim);
		costPane.setMinimumSize(dim);
		costPane.setPreferredSize(dim);
		
		this.add(sp);
	
		JPanel rightPanel = new JPanel();
		GridBagLayout rl = new GridBagLayout();
		rightPanel.setLayout(rl);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 2;
		rightPanel.add(costPane, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.insets = new Insets(0, 0, 0, 10);
		rightPanel.add(new OptionPanel(this.controller), cons);
		
		cons.gridx = 1;
		cons.gridy = 1;
		cons.anchor = GridBagConstraints.LAST_LINE_END;
		rightPanel.add(calculateButton, cons);		
		rightPanel.revalidate();
		
		this.add(rightPanel);
		this.calculateButton.addActionListener(new MetricListener(this.controller, this));
<<<<<<< HEAD:src/gui/GUI.java
		*/
=======

		this.revalidate();
>>>>>>> 087c3d93a274ea6f37e5234d91d5af61195beb2e:src/gui/TestGUI.java
	}
	
	public void displayMetrics(String[] metrics) {
		
		metricTable.setValueAt(metrics[0], NO_OF_IMPRESSIONS, PER_HOUR);
		metricTable.setValueAt(metrics[1], NO_OF_IMPRESSIONS, PER_DAY);
		metricTable.setValueAt(metrics[2], NO_OF_CLICKS, PER_HOUR);
		metricTable.setValueAt(metrics[3], NO_OF_CLICKS, PER_DAY);
		metricTable.setValueAt(metrics[4], NO_OF_UNIQUES, PER_HOUR);
		metricTable.setValueAt(metrics[5], NO_OF_UNIQUES, PER_DAY);
		metricTable.setValueAt(metrics[6], NO_OF_BOUNCES, PER_HOUR);
		metricTable.setValueAt(metrics[7], NO_OF_BOUNCES, PER_DAY);
		metricTable.setValueAt(metrics[8], NO_OF_CONVERSIONS, PER_HOUR);
		metricTable.setValueAt(metrics[9], NO_OF_CONVERSIONS, PER_DAY);
		metricTable.setValueAt(metrics[12], CTR, PER_HOUR);
		metricTable.setValueAt(metrics[13], CTR, PER_DAY);
		metricTable.setValueAt(metrics[14], CPA, PER_HOUR);
		metricTable.setValueAt(metrics[15], CPA, PER_DAY);
		metricTable.setValueAt(metrics[16], CPC, PER_HOUR);
		metricTable.setValueAt(metrics[17], CPC, PER_DAY);
		metricTable.setValueAt(metrics[18], CPM, PER_HOUR);
		metricTable.setValueAt(metrics[19], CPM, PER_DAY);
		metricTable.setValueAt(metrics[20], BOUNCE_RATE, PER_HOUR);
		metricTable.setValueAt(metrics[21], BOUNCE_RATE, PER_DAY);
		costTable.setValueAt(metrics[10], 0, 1);
		costTable.setValueAt(metrics[11], 1, 1);
		
	}
	
}

class DataListener implements ActionListener {

	private Controller controller;
	private JFileChooser fc;
	private DataPanel dp;
	
	public DataListener(Controller controller, DataPanel dp) {
		this.controller = controller;
		this.dp = dp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(dp.clickButton)){
			
			fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(dp);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				String filePath = fc.getSelectedFile().getAbsolutePath();
				dp.clickField.setText(filePath);
			}
		} else if (e.getSource().equals(dp.impressionButton)){
			
			fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(dp);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				String filePath = fc.getSelectedFile().getAbsolutePath();
				dp.impressionField.setText(filePath);
			}
		} else if (e.getSource().equals(dp.serverButton)){
			fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(dp);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				String filePath = fc.getSelectedFile().getAbsolutePath();
				dp.serverField.setText(filePath);
			}
		} else if (e.getSource().equals(dp.submitButton)){
			if (dp.impressionField.getText().equals("") || dp.clickField.getText().equals("") || dp.serverField.getText().equals("")){
				dp.errorField.setText("All three logs must be submitted");
			} else{
				dp.errorField.setText("");
				controller.setFileLocation(dp.impressionField.getText(), dp.clickField.getText(), dp.serverField.getText());
			}
		}
	}
}

class MetricListener implements ActionListener {

	private Controller controller;
	private MetricsPanel mp;
	
	public MetricListener(Controller controller, MetricsPanel mp) {
		this.controller = controller;
		this.mp = mp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] metrics = controller.calculateMetrics();
		mp.displayMetrics(metrics);
	}	
}

class ChartsPanel extends JPanel
{
	private String[] list = {"Click to Access Charts", "Number of Impressions", "Number of Clicks",
			"Number of Uniques", "Number of Bounces", "Number of Conversions", 
			/*"Total Cost",*/ "CTR", "CPA", "CPC", "CPM", "Bounce Rate"};
	
	ChartsPanel(Controller controller) 
	{
		Plotter plotter = new Plotter();	
		this.setLayout(new GridBagLayout());	
		GridBagConstraints cons = new GridBagConstraints();
		
		final JComboBox<String> l = new JComboBox<String>();
		for(int i = 0 ; i < list.length ; i++)
		{
			l.addItem(list[i]);
		}		
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.PAGE_START;
		this.add(l, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridheight = 3;
		JPanel chartDisplayPanel = new JPanel();
		chartDisplayPanel.setPreferredSize(new Dimension(500, 250));
		this.add(chartDisplayPanel, cons);
		
		chartDisplayPanel.setLayout(new GridLayout());
		
		l.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{		
				if (l.getItemAt(0).equals("Click to Access Charts"))
					l.removeItemAt(0);
					
				
				String x = (String) l.getSelectedItem();
				chartDisplayPanel.removeAll();
				switch (x)
				{	
		            case "Number of Impressions":	
		            	chartDisplayPanel.add(plotter.nImpression(controller.getImpressionNumber(60), 60));
		            	break;
		            	
		            case "Number of Clicks":
		            	chartDisplayPanel.add(plotter.nClicks(controller.getClicks(60), 60));
		            	break;
		            	
		            case "Number of Uniques":	
		            	chartDisplayPanel.add(plotter.nUniques(controller.getUniques(60), 60));
		            	break;
		            	
		            case "Number of Bounces":
		            	chartDisplayPanel.add(plotter.nBounces(controller.getBounces(60), 60));
		            	break;
		            	
		            case "Number of Conversions":
		            	chartDisplayPanel.add(plotter.nConversions(controller.getConversions(60), 60));
		            	break;
		            	
		            /*case "Total Cost":
		            	ChartsPanel.this.add(plotter.nBounces(controller.getBounces(60), 60) , BorderLayout.CENTER);
		            	ChartsPanel.this.validate();
		            	ChartsPanel.this.repaint();
		            	break;*/
		            	
		            case "CTR":
		            	chartDisplayPanel.add(plotter.nCTR(controller.getCTR(60), 60));
		            	break;
		            	
		            case "CPA":
		            	chartDisplayPanel.add(plotter.nCPA(controller.getCPA(60), 60));
		            	break;
		            	
		            case "CPC":
		            	chartDisplayPanel.add(plotter.nCPC(controller.getCPC(60), 60));
		            	break;
		            	
		            case "CPM":
		            	chartDisplayPanel.add(plotter.nCPM(controller.getCPM(60), 60));
		            	break;
		            	
		            case "Bounce Rate":
		            	chartDisplayPanel.add(plotter.bounceRate(controller.getBounceRate(60), 60));
		            	break;
				}
				chartDisplayPanel.revalidate();
            	chartDisplayPanel.repaint();
            	ChartsPanel.this.revalidate();
            	ChartsPanel.this.repaint();
			}
		});		
	}
}

class SubmissionListener implements ActionListener {
	
	private JComboBox numberOptions, rateOptions;
	private Controller controller;
	
	public SubmissionListener(JComboBox<String> numberOptions, JComboBox<String> rateOptions, Controller controller) {
		this.numberOptions = numberOptions;
		this.rateOptions = rateOptions;
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.setBouncePreferences((String) numberOptions.getSelectedItem(),
										(String) rateOptions.getSelectedItem());
			
	}
	
}

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