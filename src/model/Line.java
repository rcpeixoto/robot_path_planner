package model;

public class Line {
	
	private Coords[] endPoints;
	private int distance;
	
	
	public Line(Coords init, Coords end) {
		this.endPoints = new Coords[2];
		this.endPoints[0] = init;
		this.endPoints[1] = end;
		this.distance = (int) Math.sqrt(Math.pow(end.getX() - init.getX(), 2) 
				+ Math.pow(end.getY() - init.getY(), 2));
		
	}
	
	public Coords contains(Line line) {
		Coords start = line.getEndoints()[0];
		Coords sample = this.endPoints[0];
		Coords sample2 = this.endPoints[1];
		if(sample.getX() == start.getX()) {
			
			System.out.println("sample : " +sample);
			System.out.println("start : " +start);
			System.out.println("end : " +line.getEndoints()[1]);
			return sample;
		}else if(sample2.getX() == start.getX()) {
			return sample2;
		}else {
			return null;
		}
		
		
	}
	
	public Coords isIntersected(Line line) {
		Coords xy1 = line.endPoints[0];
		Coords xy2 = line.endPoints[1];
		Coords xy3 = this.endPoints[0];
		Coords xy4 = this.endPoints[1];
		
		return null;
	}

	
	public Coords getAdjacent(Coords endPoint) {
		if(this.endPoints[0].equals(endPoint)) {
			return this.endPoints[1];
		}else if(this.endPoints[1].equals(endPoint)) {
			return this.endPoints[0];
		}else {
			return null;
		}
	}
	
	
	public int getDistance() {
		return this.distance;
	}
	
	
	public Coords[] getEndoints() {
		return this.endPoints;
	}
	
}
