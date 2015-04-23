package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import plotter.Plotter;
import controller.Controller;

class ChartsPanel extends JPanel
{
	private String[] list = {"Click to Access Charts", "Number of Impressions", "Number of Clicks",
			"Number of Uniques", "Number of Bounces", "Number of Conversions", 
			"Total Impression Cost", "CTR", "CPA", "CPC", "CPM", "Bounce Rate", "Click Cost"};
	
	private JComboBox<String> timeIntervalMenu;
	final JComboBox<String> listOfCharts = new JComboBox<String>();
	JPanel chartDisplayPanel;
	Plotter plotter;
	Controller controller;
	
	private int getInterval()
	{
		String chosenInterval = (String) timeIntervalMenu.getSelectedItem();
		int chosenIntervalSeconds;
		if (chosenInterval.equals("1 hour")) {
			chosenIntervalSeconds = 3600;
		} else if (chosenInterval.equals("6 hours")) {
			chosenIntervalSeconds = 21600;
		} else {
			chosenIntervalSeconds = 86400;
		}
		return chosenIntervalSeconds;
	}
	
	ChartsPanel(Controller controller) 
	{
		plotter = new Plotter();
		this.controller = controller;
		this.setLayout(new GridBagLayout());	
		GridBagConstraints cons = new GridBagConstraints();
		
		JLabel intervalLabel = new JLabel("Time Interval:");
		
		String[] intervalSelection = {"1 hour", "6 hours", "24 hours"};
		timeIntervalMenu = new JComboBox<String>(intervalSelection);
		

		for(int i = 0 ; i < list.length ; i++)
		{
			listOfCharts.addItem(list[i]);
		}		
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0, 5, 0, 5);
		this.add(listOfCharts, cons);
		
		cons.gridx = 1;
		this.add(intervalLabel, cons);
		
		cons.gridx = 2;
		this.add(timeIntervalMenu, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 3;
		cons.gridheight = 3;
		chartDisplayPanel = new JPanel();
		chartDisplayPanel.setPreferredSize(new Dimension(500, 250));
		this.add(chartDisplayPanel, cons);
		
		chartDisplayPanel.setLayout(new GridLayout());
		
		listOfCharts.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{		
				if (listOfCharts.getItemAt(0).equals("Click to Access Charts"))
					listOfCharts.removeItemAt(0);
					
				updateChart();
			}
		});
		
		
		timeIntervalMenu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{		
				updateChart();
			}
		});
	}
	
	public void updateChart(){
		
		String x = (String) listOfCharts.getSelectedItem();
		chartDisplayPanel.removeAll();
		
		switch (x)
		{	
			case "Click to Access Charts":
				break;
				
            case "Number of Impressions":	
            	chartDisplayPanel.add(plotter.nImpression(controller.getImpressionNumber(getInterval()), getInterval()));
            	break;
            	
            case "Number of Clicks":
            	chartDisplayPanel.add(plotter.nClicks(controller.getClicks(getInterval()), getInterval()));
            	break;
            	
            case "Number of Uniques":	
            	chartDisplayPanel.add(plotter.nUniques(controller.getUniques(getInterval()), getInterval()));
            	break;
            	
            case "Number of Bounces":
            	chartDisplayPanel.add(plotter.nBounces(controller.getBounces(getInterval()), getInterval()));
            	break;
            	
            case "Number of Conversions":
            	chartDisplayPanel.add(plotter.nConversions(controller.getConversions(getInterval()), getInterval()));
            	break;
            	
            case "Total Impression Cost":
            	chartDisplayPanel.add(plotter.totalCost(controller.getTotalImpressionCost(getInterval()), getInterval()));
            	break; 
            	
            case "CTR":
            	chartDisplayPanel.add(plotter.nCTR(controller.getCTR(getInterval()), getInterval()));
            	break;
            	
            case "CPA":
            	chartDisplayPanel.add(plotter.nCPA(controller.getCPA(getInterval()), getInterval()));
            	break;
            	
            case "CPC":
            	chartDisplayPanel.add(plotter.nCPC(controller.getCPC(getInterval()), getInterval()));
            	break;
            	
            case "CPM":
            	chartDisplayPanel.add(plotter.nCPM(controller.getCPM(getInterval()), getInterval()));
            	break;
            	
            case "Bounce Rate":
            	chartDisplayPanel.add(plotter.bounceRate(controller.getBounceRate(getInterval()), getInterval()));
            	break;
            	
            case "Click Cost":
            	chartDisplayPanel.add(plotter.clickCost(controller.getClickCost()));
            	break;
		}
		
		chartDisplayPanel.revalidate();
    	chartDisplayPanel.repaint();
    	ChartsPanel.this.revalidate();
    	ChartsPanel.this.repaint();
		
	}
}
