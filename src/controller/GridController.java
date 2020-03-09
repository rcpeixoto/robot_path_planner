package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.Cells;

public class GridController implements MouseListener{
	
	private int counter;

	
	public GridController() {
		this.counter = 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Cells cell = (Cells) e.getSource();
		if(counter == 0 ) {
			cell.drawStart();
			cell.setBegin();
		}else if(counter == 1) {
			cell.drawEnd();
			cell.setDestination();
		}else {
			if(!cell.isSelected() &&!(cell.isBegin() || cell.isDestination())) {
				cell.drawSelected();
				cell.select();
			}
		}
		counter++;
	}
	
	public void resetCounter() {
		this.counter = 0;
	}
	
	
	public void calculateShortestRoute(int[][] cells) {
		
		//Shows the populated grid.
		for(int i = 0; i< 12; ++i) {
			System.out.println(cells[i][0] + "|" + cells[i][1] + "|" + cells[i][2] + 
					"|" + cells[i][3] + "|" + cells[i][4] + "|" + cells[i][5] + "|"
					+ cells[i][6] + "|" + cells[i][7] + "|" + cells[i][8] + "|"
					+ cells[i][9] + "|" + cells[i][10] + "|" + + cells[i][11] + "|");
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
		Cells cell = (Cells) e.getSource();
		if(!cell.isSelected() && !(cell.isBegin() || cell.isDestination())) {
			cell.drawEntered();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Cells cell = (Cells) e.getSource();
		if(!cell.isSelected() && !(cell.isBegin() || cell.isDestination())) {
			cell.drawExited();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e.consume();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		e.consume();
	}


}
