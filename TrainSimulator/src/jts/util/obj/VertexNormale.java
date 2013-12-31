package jts.util.obj;

import java.io.BufferedWriter;
import java.io.IOException;

public class VertexNormale {

	private double x;
	private double y;
	private double z;
	
	public VertexNormale(){
		
	}
	
	public VertexNormale(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		double norme = norme();
		this.x = x/norme;
		this.y = y/norme;
		this.z = z/norme;
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

	public double norme(){
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public void writeObj(BufferedWriter writer) throws IOException{
		writer.write("vn " + Vertex.DF.format(x).replaceAll(",", ".") + " " + Vertex.DF.format(y).replaceAll(",", ".") + " " + Vertex.DF.format(z).replaceAll(",", "."));
		writer.newLine();
	}
	
	public boolean equals(VertexNormale vn){
		if(Math.abs(vn.x - x)>Vertex.SEUIL){
			return false;
		} else if(Math.abs(vn.y - y)>Vertex.SEUIL){
			return false;
		} else if(Math.abs(vn.z - z)>Vertex.SEUIL){
			return false;
		} else {
			return true;
		}
	}
}
