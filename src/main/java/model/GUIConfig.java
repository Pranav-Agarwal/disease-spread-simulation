package model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import java.awt.Font;

public class GUIConfig implements ActionListener{

	protected JFrame frame;                          //top level container of the app
	protected HashMap<String,Component> components;  //HashMap of components that provides an easy interface to take input from them	
	
	public GUIConfig() {
		
		components = new HashMap<String,Component>();
		initGUI();
		showUI();
	}	
	
    public void showUI() {
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	frame.setVisible(true); // The UI is built, so display it;
            }
        });
    	
    }
    
    public void initGUI() {
    	
		/* Initialize the Configuration Frame */
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Configuration");
		frame.setSize(new Dimension(300, 421));
		frame.getContentPane().setLayout(null);
		
		JPanel speedPanel = new JPanel();
		speedPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		speedPanel.setBounds(10,11,274,121);
		frame.getContentPane().add(speedPanel);
		speedPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Speed");
		lblNewLabel.setBounds(29, 11, 46, 14);
		speedPanel.add(lblNewLabel);
		
		JSlider speedSlider = new JSlider();
		speedSlider.setPaintTicks(true);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setSnapToTicks(true);
		speedSlider.setValue(2);
		speedSlider.setMinimum(1);
		speedSlider.setMaximum(4);
		speedSlider.setBounds(29, 36, 200, 26);
		speedSlider.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent event) {
	    	  Simulator.speed = (5-speedSlider.getValue());
	      }
		});
		speedPanel.add(speedSlider);
		components.put("speedSlider", speedSlider);
		
		JToggleButton playButton = new JToggleButton("Play");
		playButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		playButton.setBounds(29, 87, 200, 23);
		speedPanel.add(playButton);
		playButton.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (playButton.isSelected()) {
		        	playButton.setText("Pause");
		        	Simulator.playing = true;
		        }
		        else  {
		        	playButton.setText("Play");
		        	Simulator.playing = false;
		        } 
		    }
		});
		
		JToggleButton officeLockdownToggle = new JToggleButton("Lockdown Offices");
		officeLockdownToggle.setBounds(20, 143, 264, 23);
		frame.getContentPane().add(officeLockdownToggle);
		officeLockdownToggle.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (officeLockdownToggle.isSelected())
		        	Map.instance.lockdownBuildings(Map.offices);
		        else  
		        	Map.instance.liftLockdownBuildings(Map.offices);  
		    }
		});
		
		JToggleButton publicLockdownToggle = new JToggleButton("Lockdown Public Places");
		publicLockdownToggle.setBounds(20, 177, 264, 23);
		frame.getContentPane().add(publicLockdownToggle);
		publicLockdownToggle.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (publicLockdownToggle.isSelected())
		        	Map.instance.lockdownBuildings(Map.public_places);
		        else  
		        	Map.instance.liftLockdownBuildings(Map.public_places);  
		    }
		});
		
		JToggleButton publicEventToggle = new JToggleButton("Active Public Event");
		publicEventToggle.setBounds(20, 211, 264, 23);
		frame.getContentPane().add(publicEventToggle);
		publicEventToggle.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (publicEventToggle.isSelected())
		        	Map.instance.createPublicEvent();
		        else  
		        	Map.instance.stopPublicEvent();  
		    }
		});
		
		JToggleButton quarantineToggle = new JToggleButton("Quarantine on +ve test");
		quarantineToggle.setBounds(20, 245, 264, 23);
		frame.getContentPane().add(quarantineToggle);
		quarantineToggle.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (quarantineToggle.isSelected()) {
		        	simulationConfig.quarantineOnTest = true;
		        	Map.instance.enforceQuarantine();
		        }        	
		        else
		        	simulationConfig.quarantineOnTest = false; 
		    }
		});
		
		JToggleButton contactTraceToggle = new JToggleButton("Contact trace on +ve test");
		contactTraceToggle.setBounds(20, 279, 264, 23);
		frame.getContentPane().add(contactTraceToggle);
		contactTraceToggle.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (contactTraceToggle.isSelected())
		        	simulationConfig.contactTracing = true; 
		        else  
		        	simulationConfig.contactTracing = false;   
		    }
		});
		
		JToggleButton maskToggle = new JToggleButton("Enforce masks");
		maskToggle.setBounds(20, 313, 264, 23);
		frame.getContentPane().add(maskToggle);
		
		JToggleButton testOfficeLockdownToggle = new JToggleButton("Lockdown office on +ve test");
		testOfficeLockdownToggle.setBounds(20, 347, 264, 23);
		frame.getContentPane().add(testOfficeLockdownToggle);
		testOfficeLockdownToggle.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent eve) {  
		        if (testOfficeLockdownToggle.isSelected())
		        	simulationConfig.lockdownOnTest = true;
		        else  
		        	simulationConfig.lockdownOnTest = false;  
		    }
		});
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getActionCommand().equalsIgnoreCase("Lockdown")){
			if(((JComboBox)components.get("lockdownTypeComboBox")).getSelectedIndex()==0) Map.instance.lockdownBuildings(Map.offices);
			else  Map.instance.lockdownBuildings(Map.public_places);
		}
		
		if( e.getActionCommand().equalsIgnoreCase("Open")){
			if(((JComboBox)components.get("lockdownTypeComboBox")).getSelectedIndex()==0) Map.instance.liftLockdownBuildings(Map.offices);
			else  Map.instance.liftLockdownBuildings(Map.public_places);
		}
	}
}