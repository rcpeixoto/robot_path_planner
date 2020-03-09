package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Cells extends Component{
	

	private static final long serialVersionUID = 1L;
	private boolean selected;
	private boolean isBeginnig;
	private boolean isDestination;
	private int x;
	private int y;
	
	public Cells(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.setBounds(x, y, 99, 99);
		this.selected = false;
		this.isBeginnig = false;
		this.isDestination = false;
	}
		
	public int isSelected2() {
		return (this.selected?1:0);
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void drawStart() {
		JPanel father = (JPanel) this.getParent();
		Graphics gra = father.getGraphics();
		int[] xCoor = {this.y + 1, this.y + 1, this.y + 99, this.y +  99};
		int[] yCoor = {this.x + 1 ,this.x + 99, this.x + 99, this.x + 1 };
		gra.setColor(Color.ORANGE);
		gra.drawPolygon(xCoor, yCoor, 4);
		gra.fillPolygon(xCoor, yCoor, 4);
	}
	

	public void drawEnd() {
		JPanel father = (JPanel) this.getParent();
		Graphics gra = father.getGraphics();
		int[] xCoor = {this.y + 1, this.y + 1, this.y + 99, this.y +  99};
		int[] yCoor = {this.x + 1 ,this.x + 99, this.x + 99, this.x + 1 };
		gra.setColor(Color.GREEN);
		gra.drawPolygon(xCoor, yCoor, 4);
		gra.fillPolygon(xCoor, yCoor, 4);
	}
	
	public void drawEntered() {
		JPanel father = (JPanel) this.getParent();
		Graphics gra = father.getGraphics();
		int[] xCoor = {this.y + 1, this.y + 1, this.y + 99, this.y +  99};
		int[] yCoor = {this.x + 1 ,this.x + 99, this.x + 99, this.x + 1 };
		gra.setColor(Color.RED);
		gra.drawPolygon(xCoor, yCoor, 4);
		gra.fillPolygon(xCoor, yCoor, 4);
	}

	public void drawSelected() {
		JPanel father = (JPanel) this.getParent();
		Graphics gra = father.getGraphics();
		int[] xCoor = {this.y + 1, this.y + 1, this.y + 99, this.y +  99};
		int[] yCoor = {this.x + 1 ,this.x + 99, this.x + 99, this.x + 1 };
		gra.setColor(Color.BLUE);
		gra.drawPolygon(xCoor, yCoor, 4);
		gra.fillPolygon(xCoor, yCoor, 4);
		
	}
	
	public void drawExited() {	
		JPanel father = (JPanel) this.getParent();
		Graphics gra = father.getGraphics();
		int[] xCoor = {this.y + 1, this.y + 1, this.y + 99, this.y +  99};
		int[] yCoor = {this.x + 1 ,this.x + 99, this.x + 99, this.x + 1 };
		gra.setColor(Color.WHITE);
		gra.drawPolygon(xCoor, yCoor, 4);
		gra.fillPolygon(xCoor, yCoor, 4);
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void unselect() {
		this.selected = false;
	}
	public void unBegin() {
		this.isBeginnig = false;
	}
	
	public void unDestination() {
		this.isDestination = false; 
	}
	
	public void setBegin() {
		this.isBeginnig = true;
	}
	public void setDestination() {
		this.isDestination = true;
	}
	
	public boolean isBegin() {
		return this.isBeginnig;
	}
	
	public boolean isDestination() {
		return this.isDestination;
	}
}
