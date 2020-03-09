package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.GridController;




public class MainScreen {
	
	private JFrame mainFrame;
	private JPanel panel;
	private Grid grid;
	private JButton confirm;
	private JButton cancel;
	private GridController gridController;
	
	public MainScreen(){
		this.gridController = new GridController();
		initComponents();
		initFunctions();
	}
	
	private void initFunctions() {
		this.confirm.addActionListener( event -> {
			int size = this.grid.getIterations() + 2;
			int[][] cellsMatrix = new int[size][size];
			Cells[][] allCells = this.grid.getGrid();
			
			//Populate the borders
			for(int i = 0; i < size; ++i) {
				for(int j = 0; j < size; ++j) {
					if(i == 0 || j == 0 || i == size - 1 || j == size - 1) {
						cellsMatrix[j][i] = 1;
					}
				}
			}
			//Populate the remaining matrix
			for(int i = 0; i < size -2; ++i) {
				for(int j = 0; j < size -2; ++j) {
					if (allCells[i][j].isBegin()){	
						cellsMatrix[j+1][i+1] = 2;
					}else if (allCells[i][j].isDestination()) {
						cellsMatrix[j+1][i+1] = 3;
					}else {
						cellsMatrix[j+1][i+1] = allCells[i][j].isSelected2();
					}
				}
			}
			this.gridController.calculateShortestRoute(cellsMatrix);
		});
		
		this.cancel.addActionListener( event -> {
			this.grid.resetCounter();
			Cells[][] allCells = this.grid.getGrid();
			for(Cells[] cells: allCells) {
				for(Cells cell: cells) {
					cell.drawExited();
					cell.unselect();
					cell.unBegin();
					cell.unDestination();
				}
			}
			
		});
	}
	
	
	private void initComponents() {
		JPanel lowerBar = new JPanel();
		
		//Set Initial frame
		this.mainFrame = new JFrame("Path Planner");
		this.mainFrame.setSize(1010, 1000);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
		this.mainFrame.setResizable(false);
		
		//Set Initial Panel
		this.panel = new JPanel();
		this.grid = new Grid(this.gridController);
		
		//Set Buttons
		
		this.confirm = new JButton("Confirm");
		this.cancel = new JButton("Cancel");
		
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
		lowerBar.setLayout(new GridLayout(1, 5));
		lowerBar.add(new JPanel());
		lowerBar.add(this.confirm);
		lowerBar.add(new JPanel());
		lowerBar.add(this.cancel);
		lowerBar.add(new JPanel());
		
	}
	
	
}
