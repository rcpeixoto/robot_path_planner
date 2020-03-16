package model;

public class Coords {

	private int x;
	private int y;
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coords) {
			Coords aux = (Coords) obj;
			return aux.getX() == this.x && aux.getY() == this.y;
		}else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return (new String("x: " + this.x + "| y: " + this.y)).hashCode();
		
	}
	
	@Override
	public String toString() {
		return ("x: " + this.x + "| y: " + this.y);
	}
	
	public int distance(Coords coord) {
		return (int) Math.sqrt(Math.pow(this.x - coord.getX(), 2) + Math.pow(this.y - coord.getY(), 2));
	}
	
}
