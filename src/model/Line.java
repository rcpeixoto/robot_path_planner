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
	
	public boolean isPoint(Line line) {
		Coords[] coord = line.getEndoints();
		return (coord[0].equals(this.endPoints[0])
				^ coord[0].equals(this.endPoints[1])
				^ coord[1].equals(this.endPoints[0])
				^ coord[1].equals(this.endPoints[1])); 
	}

	public Coords isIntersected(Line line) {
		
		double x1 = line.getEndoints()[0].getX();
		double x2 = line.getEndoints()[1].getX();
		
		double y1 = line.getEndoints()[0].getY();
		double y2 = line.getEndoints()[1].getY();
		
		double x3 = this.endPoints[0].getX();
		double x4 = this.endPoints[1].getX();
		
		double y3 = this.endPoints[0].getY();
		double y4 = this.endPoints[1].getY();
		
		double div = x2 - x1;
		double div2 = x4 - x3;
		
		double a = 0f;
		double b = 0f;
		double a1 = 0f;
		double b1 = 0f;
		double y = 0f;
		double x = 0f;
		Coords result = null;
		
		if(div == 0 && div2 != 0) {
			
			a = (y4 - y3)/div2;
			b = y4 - a*x4;
			
			x = x1;
			y = x1*a + b;
			result = new Coords((int) x, (int) y);
			
			
			boolean inside = ((y >= y1 -1 && y <= y2 +1) || (y <= y1+1 && y >= y2-1));
			return (this.isInside(result) && inside)? result:null;
			
		}else if(div != 0 && div2 == 0) {
			
			a = (y2 - y1)/div;
			b = y2 - a*x2;
			
			x = x3;
			y = x3*a + b;
			result = new Coords((int) x, (int) y);
			
			
			boolean inside = ((y >= y3 -1 && y <= y4 +1) || (y <= y3+1 && y >= y4-1));
			return (line.isInside(result) && inside)? result:null;
		
		}else if(div == 0 && div2 == 0){	
			boolean inside = ((x1 == x4) && ((y1 <= y3 && y1 >= y4) || (y1 >= y3&& y1 <= y4)));		
			return inside? new Coords((int) x1,(int) y1): null;
		}else {
			
			a = (y2 - y1)/div;
			b = y2 - a*x2;
			
			a1 = (y4 - y3)/div2;
			b1 = y4 - a1*x4;
		
			x = (b1 - b)/(a - a1);
			y = a*x + b;
			
			result = new Coords((int)x ,(int) y);
			
			boolean inside = this.isInside(result) && line.isInside(result);

			return inside?result:null;
		}
		
		
	}

	
	public boolean isInside(Coords coord) {
		
		double x = coord.getX();
		double y = coord.getY();
		
		double x1 = this.endPoints[0].getX();
		double x2 = this.endPoints[1].getX();
		
		double y1 = this.endPoints[0].getY();
		double y2 = this.endPoints[1].getY();
	
		if(x1 > x2) {
			if(y1 > y2) {
				if((x <= x1 +1 && x >= x2-1) && (y <= y1+1  && y >= y2-1 )) {
					return true;
				}
			}else {
				if((x <= x1+1 && x >= x2-1 ) && (y >= y1-1  && y <= y2+1 )) {
					return true;
				}
			}
		}else {
			if(y1 > y2) {
				if((x >= x1-1  && x <= x2+1 ) && (y <= y1+1 && y >= y2-1)) {
					return true;
				}
			}else {
				if((x >= x1-1  && x <= x2+1 ) && (y >= y1-1 && y <= y2+1)) {
					return true;
				}
			}
		}
		
		
		return false;
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
	
	@Override
	public int hashCode() {
		return this.endPoints[0].hashCode() + this.endPoints[1].hashCode();
	}
	
	@Override
	public boolean equals(Object data) {
		if(data instanceof Line) {
			Line line = (Line) data;
			Coords xy1 = line.getEndoints()[0];
			Coords xy2 = line.getEndoints()[1];
			if(xy1.equals(this.endPoints[0]) && xy2.equals(this.endPoints[1])) {
				return true;
			}else if(xy1.equals(this.endPoints[1]) && xy2.equals(this.endPoints[0])) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.endPoints[0].toString() + " " + this.endPoints[1].toString();
	}
	
	
}
