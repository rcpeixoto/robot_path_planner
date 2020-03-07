package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class MainScreen {
	
	private JFrame mainFrame;
	private JPanel panel;
	private JPanel grid;
	private JButton confirm;
	
	
	public MainScreen(){
		initComponents();
		initFunctions();
		initGrid();
	}
	
	private void initGrid() {
		
		
		
		
	}
	
	private void initFunctions() {
		this.confirm.addActionListener( event -> {
			
		
			
		});
	}
	
	
	private void initComponents() {
		JPanel lowerBar = new JPanel();
		
		//Set Initial frame
		this.mainFrame = new JFrame("Path Planner");
		this.mainFrame.setSize(1000, 800);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
		this.mainFrame.setResizable(false);
		
		//Set Initial Panel
		this.panel = new JPanel();
		this.grid = new JPanel();
		
		//Set Confirm Button
		
		this.confirm = new JButton("Confirm");
		
		
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
		
		
		//Set lower bar and confirmation button
		lowerBar.setLayout(new GridLayout(1, 3));
		lowerBar.add(new JPanel());
		lowerBar.add(this.confirm);
		lowerBar.add(new JPanel());
		
	}
	
	
}
