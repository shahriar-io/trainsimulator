package jts.util.obj;

import java.io.BufferedWriter;
import java.io.IOException;

public class VertexTexture {

	private double u;
	private double v;
	
	public VertexTexture(){
		
	}
	
	public VertexTexture(double u, double v) {
		this.u = u;
		this.v = v;
	}
	
	public void writeObj(BufferedWriter writer) throws IOException{
		writer.write("vt " + Vertex.DF.format(u).replaceAll(",", ".") + " " + Vertex.DF.format(v).replaceAll(",", "."));
		writer.newLine();
	}
	
	public boolean equals(VertexTexture vt){
		if(Math.abs(vt.u - u)>Vertex.SEUIL){
			return false;
		} else if(Math.abs(vt.v - v)>Vertex.SEUIL){
			return false;
		} else {
			return true;
		}
	}
}
