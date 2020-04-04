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
	private JButton showPath;
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
			this.mapController.reset();
		});
		
		this.load.addActionListener( event -> {
			this.fc = new JFileChooser("./");
			int returnVal = fc.showOpenDialog(this.panel);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				this.mapController.getObstaclesFromFile(file);
			}
		});
		this.showPath.addActionListener(event -> {
			this.grid.showPath();
			JButton button = (JButton) event.getSource();
			if(button.getText().equals("Shortest Path: off"))
				button.setText("Shortest Path: on");
			else
				button.setText("Shortest Path: off");
		});
	}
	
	
	private void initComponents() {
		JPanel lowerBar = new JPanel();
		
		//Set Initial frame
		this.mainFrame = new JFrame("Path Planner");
		this.mainFrame.setSize(982, 850);
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
		this.showPath = new JButton("Shortest Path: off");
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
		lowerBar.setLayout(new GridLayout(2, 5));
		lowerBar.add(new JPanel());
		lowerBar.add(this.showPath);
		lowerBar.add(new JPanel());
		lowerBar.add(this.confirm);
		lowerBar.add(new JPanel());
		lowerBar.add(new JPanel());
		lowerBar.add(this.load);
		lowerBar.add(new JPanel());
		lowerBar.add(this.cancel);
		lowerBar.add(new JPanel());
		
	}
	
	
}
