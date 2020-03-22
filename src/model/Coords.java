package model;

public class Coords {

	private int x;
	private int y;
	private Double fScore = 0d;
	
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
	
	public void setFscore(Double fscore) {
		this.fScore = fscore;
	}
	
	public Double getFscore() {
		return this.fScore;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coords) {
			Coords aux = (Coords) obj;
			return (aux.getX() > this.x -2 && aux.getX() < this.x + 2) &&( aux.getY() > this.y - 2 && aux.getY() < this.y + 2);
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
