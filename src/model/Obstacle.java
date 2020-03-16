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
		int distance = Integer.MAX_VALUE;
		Coords intersection = null;
		/*
		System.out.println("End : " + line.getEndoints()[0]);
		System.out.println("start : " + line.getEndoints()[1]);
		System.out.println("--------------------");
		*/
		for(Line auxLine: this.lines) {
			if(auxLine.contains(line) != null) {
				inter.add(auxLine.contains(line));
			}//else if(auxLine.isIntersected(line) != null) {
				//inter.add(auxLine.isIntersected(line));
			//}
		}
		
		for(Coords coord: inter) {
			if(coord.distance(start) < distance) {
				distance = coord.distance(start);
				intersection = coord;
			}
		}
		
		return intersection;
	}
	
	public ArrayList<Line> getLines(){
		return this.lines;
	}
	
	
}
