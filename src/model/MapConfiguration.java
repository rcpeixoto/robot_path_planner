package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		coords = new HashSet();
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
	
	public void calculatePath() {
		Map<String, ArrayList<Line>> verticalLines = new HashMap<>();
		ArrayList<Line> lines = new ArrayList();
		verticalLines.put("Vertical Lines", lines);
		
		for(Coords coord: this.coords) {
			Line up = new Line(coord, new Coords(coord.getX(), 0));
			Line down = new Line(coord, new Coords(coord.getX(), 500));
			
			Coords upInter = null;
			Coords downInter = null;
			
			for(Obstacle obs: this.lines.keySet()) {
				if(!obs.contains(coord)) {
					upInter = obs.intersection(up, coord);
					downInter = obs.intersection(down, coord);
				}
			
			}
			lines.add(upInter == null? up: new Line(coord, upInter));
			lines.add(downInter == null? down: new Line(coord, downInter));			
		}	
		/*
		for(Line line: lines) {
			for(Coords coord: line.getEndoints()) {
				System.out.println(coord);
			}
			System.out.println("----------------");
		}
		*/
		this.updateAll(verticalLines);
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
	
	
}
