package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.MapController;

public class MainScreen {
	
	private JFrame mainFrame;
	private JPanel panel;
	private MapView grid;
	private JButton confirm;
	private JButton cancel;
	private JButton load;
	private MapController mapController;
	private JFileChooser fc;
	
	public MainScreen(){
		this.mapController = new MapController();
		initComponents();
		initFunctions();
		this.mapController.setGrid2(grid);
	}
	
	private void initFunctions() {
		this.confirm.addActionListener( event -> {
			this.mapController.calculatePath();
		});
		
		this.cancel.addActionListener( event -> {
			this.grid.cleanScreen();			
		});
		
		this.load.addActionListener( event -> {
			this.fc = new JFileChooser("C:\\Users\\Yggdrasil\\desktop");
			int returnVal = fc.showOpenDialog(this.panel);
			if(returnVal == fc.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				this.mapController.getObstaclesFromFile(file);
			}
		});
	}
	
	
	private void initComponents() {
		JPanel lowerBar = new JPanel();
		
		//Set Initial frame
		this.mainFrame = new JFrame("Path Planner");
		this.mainFrame.setSize(982, 800);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
		this.mainFrame.setResizable(false);
		
		//Set Initial Panel
		this.panel = new JPanel();
		this.grid = new MapView(this.mapController);
		
		//Set Buttons
		
		this.confirm = new JButton("Confirm");
		this.cancel = new JButton("Cancel");
		this.load = new JButton("Load Obstacles");
		
		//Add button and panel to JFrame
		
		this.mainFrame.add(this.panel);
		this.panel.setLayout(new BorderLayout());
		this.panel.setBackground(Color.DARK_GRAY);
		this.grid.setBackground(Color.WHITE);
		
		//Add Elements to the panel
		this.panel.add(this.grid, BorderLayout.CENTER);
		this.panel.add(lowerBar, BorderLayout.SOUTH);
		this.panel.add(new JPanel(), BorderLayout.NORTH);
		this.panel.add(new JPanel(), BorderLayout.EAST);
		this.panel.add(new JPanel(), BorderLayout.WEST);
		
		//Initiate file chooser
		
		//
		
		//Set lower bar and confirmation button
		lowerBar.setLayout(new GridLayout(1, 7));
		lowerBar.add(new JPanel());
		lowerBar.add(this.confirm);
		lowerBar.add(new JPanel());
		lowerBar.add(this.load);
		lowerBar.add(new JPanel());
		lowerBar.add(this.cancel);
		lowerBar.add(new JPanel());
		
	}
	
	
}
