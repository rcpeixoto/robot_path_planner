package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import interfaces.Observer;
import interfaces.Subject;

public class MapConfiguration implements Subject{
	
	private ArrayList<Observer> observers;
	private Map<Obstacle, ArrayList<Line>> lines;
	private Set<Coords> coords;
	private Coords[] init_end;
	
	public MapConfiguration() {
		this.observers = new ArrayList<>();
		this.lines = new HashMap<>();
		this.init_end = new Coords[2];
		this.coords = new HashSet();
	}
	
	public void setObstacleFiles(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<Line> lines = null;
			String line;
			Obstacle obs = null;
			while((line = br.readLine()) != null) {
				if(line.equals("HEADER")) {
					continue;
				}else if (line.equals("START")){
					line = br.readLine();
					int x = Integer.parseInt(line.split(";")[0].trim());
					int y = Integer.parseInt(line.split(";")[1].trim());
					this.init_end[0] = new Coords(x, y);
				}else if(line.equals("END")){
					line = br.readLine();
					int x = Integer.parseInt(line.split(";")[0].trim());
					int y = Integer.parseInt(line.split(";")[1].trim());
					this.init_end[1] = new Coords(x, y);
				}else if(line.startsWith("(")) {
					lines = new ArrayList<>();
					obs = new Obstacle();
					this.lines.put(obs, lines);
				}else {
					String[] coords = new String[2];
					coords[0] = line.split("-")[0].trim();
					coords[1] = line.split("-")[1].trim();
					int x1 = Integer.parseInt(coords[0].split(";")[0].trim());
					int y1 = Integer.parseInt(coords[0].split(";")[1].trim());
					Coords xy1 = new Coords(x1, y1);
					int x2 = Integer.parseInt(coords[1].split(";")[0].trim());
					int y2 = Integer.parseInt(coords[1].split(";")[1].trim());
					Coords xy2 = new Coords(x2, y2);
					this.coords.add(xy1);
					this.coords.add(xy2);
					Line aux = new Line(xy1, xy2);
					obs.addLine(aux);
					lines.add(aux);
				}
			}
			
			lines = new ArrayList<>();
			lines.add(new Line(this.init_end[0], this.init_end[1]));
			
			this.updateAll(this.lines);
			Map<String, ArrayList<Line>> aux = new HashMap<>();
			aux.put("END/START", lines);
			this.updateAll(aux);
			
			br.close();
		}catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File Not Found!");;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public <T> void calculatePath() {
		
		//Calls method to create trapezoidal shapes in the configuration space
		Map<String, ArrayList<Line>> verticalLines = this.findTrapezoidals();
		List<Line> vertical = verticalLines.get("VerticalLines");
		//Sort all vertical lines based on x-axis coordinates
		ArrayList<Line> path = new ArrayList<Line>();	
		Collections.sort(vertical, (o1, o2) -> {
			Line line1 = (Line) o1;
			Line line2 = (Line) o2;
			Coords x1 = line1.getEndoints()[0];
			Coords x2 = line2.getEndoints()[0];
			
			if(x1.getX() < x2.getX()) {
				return -1;
			}else if(x1.getX() == x2.getX()){
				return 0;
			}else {
				return 1;
			}
		});
		
		//Creates all midpoints of the vertical lines to create
		//the paths
		ArrayList<Coords> midPoints = new ArrayList<>();
		for(Line line: vertical)			
			midPoints.add(new Coords(line.getEndoints()[0].getX(),
					(line.getEndoints()[0].getY() + line.getEndoints()[1].getY())/2));
		

		//create all the lines connecting the midpoints
		for(Coords coord: midPoints) 
			for(Coords coord2: midPoints) 
				if(!coord.equals(coord2) 
						&& coord.getX() < coord2.getX()) {
					path.add(new Line(coord, coord2));
					path.add(new Line(this.init_end[0], coord2));
					path.add(new Line(this.init_end[1], coord2));
					path.add(new Line(this.init_end[0], coord));
					path.add(new Line(this.init_end[1], coord));
				}
		
		//Filter all connecting lines that intersect
		//Obstacles
		ArrayList<Line> filter = new ArrayList<>();	
		for(Obstacle obs: this.lines.keySet()) 
			for(Line line: path) 
				if(obs.isInside(line)) 
					filter.add(line);
		
		//Filter the lines that cross other vertical lines
		//which the intersection point are different than
		//the boundaries of the line
		for(Line auxLine : vertical) {
			for(Line line : path) {
				Coords inter = auxLine.isIntersected(line);
				if(inter != null) {
					Coords xy1 = line.getEndoints()[0];
					Coords xy2 = line.getEndoints()[1];
					if(!inter.equals(xy1) && !inter.equals(xy2)) 
						filter.add(line);					
				}
			}
		}
		
		path.removeAll(filter);
		
		//call the A* algorithm to find shortest path
		
		ArrayList<Line> shortestPath = this.aStartSearch(this.init_end[0], this.init_end[1], path);
		
		Map<String, ArrayList<Line>> totalPath = new HashMap<>();
		Map<String, ArrayList<Line>> totalPath2 = new HashMap<>();
		totalPath.put("Path", path);
		totalPath2.put("ShortestPath", shortestPath);
		
		this.updateAll(totalPath);
		this.updateAll(totalPath2);
		this.updateAll(verticalLines);
	}
	
	private ArrayList<Line> aStartSearch(Coords start, Coords goal, ArrayList<Line> edges) {
		PriorityQueue<Coords> openSet = new PriorityQueue<>((e1, e2) -> {

			Coords x1 = (Coords) e1;
			Coords x2 = (Coords) e2;
			
			if(x1.getFscore() < x2.getFscore()) {
				return -1;
			}else if(x1.getFscore() > x2.getFscore()){
				return 1;
			}else {
				return 0;
			}
		});
		
		start.setFscore(0d);
		openSet.add(start);
		
		//Start all maps
		Map<Coords, Coords> cameFrom = new HashMap<>();		
		Map<Coords, Double> fScore = new HashMap<>();
		Map<Coords, Double> gScore = new HashMap<>();
		
		fScore.put(start, (double) start.distance(goal));
		gScore.put(start,  0d);
		
		//Populate the g(n) and f(n) functions with default values
		//For all nodes, except the start, the default value is infinity
		//and the g(n) for the start node is 0, and the f(n) default
		//for the start node is the cartesian distance from start to goal.
		for(Line line: edges) {
			Coords aux = line.getEndoints()[0];
			Coords aux2 = line.getEndoints()[1];
			if(!fScore.containsKey(aux)) fScore.put(aux, Double.MAX_VALUE);
			if(!fScore.containsKey(aux2)) fScore.put(aux2, Double.MAX_VALUE);
			if(!gScore.containsKey(aux)) gScore.put(aux, Double.MAX_VALUE);
			if(!gScore.containsKey(aux2)) gScore.put(aux2, Double.MAX_VALUE);
		}
		
		while(!openSet.isEmpty()) {
			//Gets the node with the lowest f(n) available
			Coords current = openSet.poll();
			
			if(current.equals(goal)) return this.reconstructPath(start, goal, cameFrom);
			
			
			for(Line line: edges) {				
				if(line.getAdjacent(current) != null) {
					//verifies for neighbour and update the f(n) of each
					Coords neighbour = line.getAdjacent(current);
					Double tentative_gScore = gScore.get(current) + current.distance(neighbour);
					//Verifies whether the f(n) is lesser then the value already stored
					if( tentative_gScore < gScore.get(neighbour)) {
						//Append the the path and update the f(n) value
						cameFrom.put(neighbour, current);
						Double fn = tentative_gScore + goal.distance(neighbour);
						gScore.put(neighbour, tentative_gScore);
						fScore.put(neighbour, fn);
						if(!openSet.contains(neighbour)) {
							neighbour.setFscore(fn);
							openSet.add(neighbour);
						}
					}					
				}
			}
		}
		return null;
	}
	
	private ArrayList<Line> reconstructPath(Coords start, Coords goal, Map<Coords, Coords> cameFrom){
		ArrayList<Line> path = new ArrayList<>();
		Coords current = goal;
		
		while(!current.equals(start)) {
			Coords prior = cameFrom.get(current);
			path.add(new Line(current, prior));
			current = prior;
		}
		
		
		return path;
	}
	
	
	
	
	private Map<String, ArrayList<Line>> findTrapezoidals(){
		
		Map<String, ArrayList<Line>> verticalLines = new HashMap<>();
		ArrayList<Line> lines = new ArrayList();
		verticalLines.put("VerticalLines", lines);
		
		//Creates all vertical lines from the vertex
		//of all obstacles presented in the configuration
		//space
		for(Coords coord: this.coords) {
			Line up = new Line(coord, new Coords(coord.getX(), 0));
			Line down = new Line(coord, new Coords(coord.getX(), 650));
			
			Coords upInter = null;
			Coords downInter = null;
			
			double upDistance = Double.MAX_VALUE;
			double downDistance = Double.MAX_VALUE;
			//Gets the lines intersecting other obstacle
			//or touching the limits of the configuration
			//from the vertex of each obstacle's vertex
			for(Obstacle obs: this.lines.keySet()) {	
				if( obs.intersection(up, coord) != null && 
						coord.distance(obs.intersection(up, coord)) < upDistance) {
					upInter = obs.intersection(up, coord);
					upDistance = coord.distance(obs.intersection(up, coord));
				}
				if(obs.intersection(down, coord) != null && 
						coord.distance(obs.intersection(down, coord)) < downDistance) {
					downInter = obs.intersection(down, coord);
					downDistance = coord.distance(obs.intersection(down, coord));
				}
			}
			Line upLine = (upInter == null)? up: new Line(coord, upInter);
			Line downLine = (downInter == null)? down: new Line(coord, downInter);
			
			//Lastly, filter all lines that are inside obstacle
			for(Obstacle obs: this.lines.keySet()) {
				if(obs.contains(coord)) {
					if(!(obs.isInside(upLine)) ) 
						if(!lines.contains(upLine)) lines.add(upLine);	
					if(!(obs.isInside(downLine))) 
						if(!lines.contains(downLine)) lines.add(downLine);			
				}
			}	
		}
		

		return verticalLines;
	}
	
	@Override
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}

	@Override
	public void removeObserver(Observer obs) {
		this.observers.remove(obs);
	}

	@Override
	public void updateAll(Object data) {
		for(Observer obs: this.observers) {
			obs.update(data);
		}
	}
	
	public void reset() {
		this.lines = new HashMap<>();
		this.init_end = new Coords[2];
		this.coords = new HashSet();
	}
	
}
