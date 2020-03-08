package view;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import controller.GridController;

public class Grid extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Cells[][] gridCells;
	private final int intervals;
	private final int iterations;
	private final GridController gridController;
	
	Grid(){
		super();
		this.setLayout(null);
		this.intervals = 100;
		this.iterations = 10;
		this.gridCells = new Cells[this.iterations][this.iterations];
		this.gridController = new GridController();
	
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int position = this.intervals;
		for(int i=0; i < this.iterations; ++i) {
			g.drawLine(position, 0, position, this.getHeight());
			g.drawLine(0, position, this.getWidth(), position);
			position += this.intervals;
		}
		this.populateCells();
	}
	
	private void populateCells() {
		for(int i=0; i < this.iterations; ++i) {
			for(int j=0; j < this.iterations; ++j) {
				
				//Cell configuration
				Cells cell = new Cells(100*j, 100*i);
				cell.setBounds(100*i + 1, 100*j + 1, 99, 99);
				cell.setBackground(Color.WHITE);
				cell.addMouseListener(this.gridController);
				//Adding to the matrix
				this.add(cell);
				this.gridCells[i][j] = (Cells) cell;
			}
		}
	}
}
