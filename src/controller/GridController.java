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
