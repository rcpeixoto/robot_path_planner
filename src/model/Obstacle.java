package model;

import java.util.ArrayList;

public class Obstacle {
	
	private ArrayList<Line> lines;
	
	public boolean contains(Coords coord) {
		for(Line line: lines) 
			for(Coords coordAux: line.getEndoints()) 
				if(coordAux.equals(coord))
					return true;
		return false;
	}
	
	public Obstacle() {
		this.lines = new ArrayList<>();
	}
	
	public void addLine(Line line) {
		lines.add(line);
	}
	
	public Coords intersection(Line line, Coords start) {
		ArrayList<Coords> inter = new ArrayList<>();
		double distance = Double.MAX_VALUE;
		Coords intersection = null;
	
		for(Line auxLine: this.lines) 
			if(auxLine.isIntersected(line) != null && auxLine.getAdjacent(start) == null) 
				inter.add(auxLine.isIntersected(line));
		
		for(Coords coord: inter) 
			if(Math.sqrt(Math.pow(coord.getY() - start.getY(), 2)) < distance) {
				distance = Math.sqrt(Math.pow(coord.getY() - start.getY(), 2));
				intersection = coord;
			}
		
		return intersection;
	}
	
	public boolean isInside(Line line) {
		int counter = 0;
		 for(Line auxLine: this.lines)
			 if(!auxLine.isPoint(line)) 
				 if(auxLine.isIntersected(line) != null) 
					 counter++;
		 return (counter > 0);
	}
	
	public int maxX() {
		int x = 0;
		for(Line line: this.lines) {
			if(line.getEndoints()[0].getX() > x) {
				x = line.getEndoints()[0].getX();
			}
			if(line.getEndoints()[1].getX() > x) {
				x = line.getEndoints()[1].getX();
			}
		}
		return x;
	}
	
	public int minX() {
		int x = Integer.MAX_VALUE;
		for(Line line: this.lines) {
			if(line.getEndoints()[0].getX() < x) {
				x = line.getEndoints()[0].getX();
			}
			if(line.getEndoints()[1].getX() < x) {
				x = line.getEndoints()[1].getX();
			}
		}
		return x;
	}
	public int maxY() {
		int y = 0;
		for(Line line: this.lines) {
			if(line.getEndoints()[0].getY() > y) {
				y = line.getEndoints()[0].getY();
			}
			if(line.getEndoints()[1].getY() > y) {
				y = line.getEndoints()[1].getY();
			}
		}
		return y;
	}
	
	public int minY() {
		int y = Integer.MAX_VALUE;
		for(Line line: this.lines) {
			if(line.getEndoints()[0].getY() < y) {
				y = line.getEndoints()[0].getY();
			}
			if(line.getEndoints()[1].getY() < y) {
				y = line.getEndoints()[1].getY();
			}
		}
		return y;
	}
	
	public ArrayList<Line> getLines(){
		return this.lines;
	}
	
	
}
