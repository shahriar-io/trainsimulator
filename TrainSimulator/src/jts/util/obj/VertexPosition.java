package jts.util.obj;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.Point;

public class VertexPosition {

	private double x;
	private double y;
	private double z;
	
	private List<Facette> facettes;
	
	public VertexPosition(){
		this(0, 0, 0);
	}

	public VertexPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.facettes = new ArrayList<Facette>();
	}
	
	public VertexPosition(Point p){
		this(p.getY(), p.getZ(), p.getX());
	}
			
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public List<Facette> getFacettes() {
		return facettes;
	}

	public void addFacettes(Facette facette) {
		this.facettes.add(facette);
	}

	public void writeObj(BufferedWriter writer) throws IOException{
		writer.write("v " + Vertex.DF.format(x).replaceAll(",", ".") + " " + Vertex.DF.format(y).replaceAll(",", ".") + " " + Vertex.DF.format(z).replaceAll(",", "."));
		writer.newLine();
	}
	
	public boolean equals(VertexPosition vp){
		if(Math.abs(vp.x - x)>Vertex.SEUIL){
			return false;
		} else if(Math.abs(vp.y - y)>Vertex.SEUIL){
			return false;
		} else if(Math.abs(vp.z - z)>Vertex.SEUIL){
			return false;
		} else {
			return true;
		}
	}
}
