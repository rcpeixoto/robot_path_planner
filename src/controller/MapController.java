package controller;


import java.io.File;
import model.MapConfiguration;
import view.MapView;


public class MapController{
	
	private MapConfiguration map;
	private MapView mapView;
	
	public MapController() {
		this.map = new MapConfiguration();
	}
	
	public void calculatePath() {
		this.map.calculatePath();
	}
	
	
	public void setGrid2(MapView mapView) {
		this.mapView= mapView;
		this.map.addObserver(mapView);
	}
	
	public void getObstaclesFromFile(File file) {
		this.map.setObstacleFiles(file);
	}
	
	public void reset() {
		map.reset();
	}
	
	
}
