package view;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import controller.MapController;
import interfaces.Observer;
import model.Coords;
import model.Line;
import model.Obstacle;

public class MapView extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private Map<Obstacle, ArrayList<Line>> lines;
	private Map<String, ArrayList<Line>> path;
	private Map<String, ArrayList<Line>> startEnd;
	private Map<String, ArrayList<Line>> verticalLines;
	
	MapView(MapController gridController){
		super();
		this.setLayout(null);
		this.lines = new HashMap<>();
		this.startEnd = new HashMap<>();
		this.path = new HashMap<>();
		this.verticalLines = new HashMap<>();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setPaintMode();

		
		g.setColor(Color.BLACK);
		for(ArrayList<Line> array: this.lines.values()) {
			for(Line line: array) {
				Coords[] coords = line.getEndoints();
				g.drawLine(coords[0].getX(), coords[0].getY(), coords[1].getX(), coords[1].getY());
			}
		}

		
		
		
		g.setColor(Color.GREEN);
		for(ArrayList<Line> array: this.verticalLines.values()) {
			for(Line line: array) {
				Coords[] coord = line.getEndoints();	
				g.drawLine(coord[0].getX(), coord[0].getY(), coord[1].getX(), coord[1].getY());
			}
		}
		
		
		g.setColor(Color.RED);
		for(ArrayList<Line> array: this.path.values()) {
			for(Line line: array) {
				Coords[] coord = line.getEndoints();	
				g.drawLine(coord[0].getX(), coord[0].getY(), coord[1].getX(), coord[1].getY());
			}
		}
		

		if(this.startEnd.get("END/START") != null) {
			Coords[] coord = this.startEnd.get("END/START").get(0).getEndoints();
			g.setColor(Color.BLACK);
			g.drawOval(coord[0].getX(), coord[0].getY(), 10, 10);
			g.setColor(Color.RED);
			g.fillOval(coord[0].getX(), coord[0].getY(), 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval(coord[1].getX(), coord[1].getY(), 10, 10);
			g.setColor(Color.GREEN);
			g.fillOval(coord[1].getX(), coord[1].getY(), 10, 10);
			g.setColor(Color.BLACK);
		}
		
	}

	@Override
	public void update(Object data) {
		Graphics g = this.getGraphics();

		
		if(((HashMap<String, ArrayList<Line>>)data).get("Path") != null) {
			this.path.putAll((Map<String, ? extends ArrayList<Line>>) data);
		}

		
		if(((HashMap<String, ArrayList<Line>>)data).get("VerticalLines") != null) {
			this.verticalLines.putAll((Map<String, ? extends ArrayList<Line>>) data);
		}

		
		if(((HashMap<String, ArrayList<Line>>)data).get("END/START") != null) {
			Coords[] coords = ((HashMap<String, ArrayList<Line>>)data).get("END/START").get(0).getEndoints();
			this.startEnd.putAll((Map<? extends String, ? extends ArrayList<Line>>) data);	
		}else{
			this.lines.putAll((Map<? extends Obstacle, ? extends ArrayList<Line>>) data);	
		}
		
		this.paintComponent(g);
	}
	
	public void cleanScreen() {
		Graphics g = this.getGraphics();
		this.lines = new HashMap<>();
		this.startEnd = new HashMap<>();
		this.path = new HashMap<>();
		this.verticalLines = new HashMap<>();
		super.paintComponent(g);
	}
	
}
