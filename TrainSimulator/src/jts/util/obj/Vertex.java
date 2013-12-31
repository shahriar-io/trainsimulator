package jts.util.obj;

import java.text.DecimalFormat;

public class Vertex {
	
	public static DecimalFormat DF = new DecimalFormat("0.0#####");
	public static double SEUIL = 0.001;

	private VertexPosition position;
	private VertexTexture texture;
	private VertexNormale normale;
	
	public Vertex(){
		
	}
	
	public Vertex(VertexPosition position) {
		this.position = position;
		this.texture = new VertexTexture();
		this.normale = new VertexNormale();
	}

	public Vertex(VertexPosition position, VertexTexture texture, VertexNormale normale) {
		this.position = position;
		this.texture = texture;
		this.normale = normale;
	}

	public VertexPosition getPosition() {
		return position;
	}

	public void setPosition(VertexPosition position) {
		this.position = position;
	}

	public VertexTexture getTexture() {
		return texture;
	}

	public void setTexture(VertexTexture texture) {
		this.texture = texture;
	}

	public VertexNormale getNormale() {
		return normale;
	}

	public void setNormale(VertexNormale normale) {
		this.normale = normale;
	}
}
