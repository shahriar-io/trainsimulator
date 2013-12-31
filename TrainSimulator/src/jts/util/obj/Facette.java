package jts.util.obj;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class Facette {

	private Groupe groupe;
	private Vertex[] sommets;
	private VertexNormale normale;
	
	public Facette(){
		this.sommets = new Vertex[3];
	}
	
	public Facette(Vertex v1, Vertex v2, Vertex v3){
		this.sommets = new Vertex[3];
		this.sommets[0] = v1;
		this.sommets[1] = v2;
		this.sommets[2] = v3;
		v1.getPosition().addFacettes(this);
		v2.getPosition().addFacettes(this);
		v3.getPosition().addFacettes(this);
		this.calculerNormale();
	}
	
	public Facette(Vertex v1, Vertex v2, Vertex v3, Vertex v4){
		this.sommets = new Vertex[4];
		this.sommets[0] = v1;
		this.sommets[1] = v2;
		this.sommets[2] = v3;
		this.sommets[3] = v4;
		v1.getPosition().addFacettes(this);
		v2.getPosition().addFacettes(this);
		v3.getPosition().addFacettes(this);
		v4.getPosition().addFacettes(this);
		this.calculerNormale();
	}
	
	public Facette(VertexPosition vp1, VertexPosition vp2, VertexPosition vp3){
		this(new Vertex(vp1), new Vertex(vp2), new Vertex(vp3));
	}
	
	public Facette(VertexPosition vp1, VertexPosition vp2, VertexPosition vp3, VertexPosition vp4){
		this(new Vertex(vp1), new Vertex(vp2), new Vertex(vp3), new Vertex(vp4));
	}
	
	public Facette(List<VertexPosition> positions, int n1, int n2, int n3){
		this(positions.get(n1), positions.get(n2), positions.get(n3));
	}
	
	public Facette(List<VertexPosition> positions, int n1, int n2, int n3, int n4){
		this(positions.get(n1), positions.get(n2), positions.get(n3), positions.get(n4));
	}
	
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	
	public Vertex[] getSommets(){
		return sommets;
	}
	
	/*public VertexNormale getNormale() {
		return normale;
	}*/
	
	public void calculerNormale(){
		VertexPosition vp1 = sommets[0].getPosition();
		VertexPosition vp2 = sommets[1].getPosition();
		VertexPosition vp3 = sommets[2].getPosition();
		double x1 = vp2.getX() - vp1.getX();
		double y1 = vp2.getY() - vp1.getY();
		double z1 = vp2.getZ() - vp1.getZ();
		double x2 = vp3.getX() - vp1.getX();
		double y2 = vp3.getY() - vp1.getY();
		double z2 = vp3.getZ() - vp1.getZ();
		normale = new VertexNormale(y1*z2-y2*z1,
				z1*x2-z2*x1,
				x1*y2-x2*y1);
	}
	
	public void recalculer(){
		for (Vertex sommet : sommets){
			if(groupe.isAdouci()){
				double x = 0;
				double y = 0;
				double z = 0;
				for(Facette facette : sommet.getPosition().getFacettes()){
					x += facette.normale.getX();
					y += facette.normale.getY();
					z += facette.normale.getZ();
					sommet.setNormale(new VertexNormale(x, y, z));
				}
			} else {
				sommet.setNormale(normale);
			}
		}
	}

	public void writeObj(BufferedWriter writer, List<VertexPosition> positions, List<VertexTexture> textures, List<VertexNormale> normales) throws IOException{
		writer.write("f");
		
		for(Vertex sommet : sommets){
			int indexVp = positions.indexOf(sommet.getPosition())+1;
			int indexVt = textures.indexOf(sommet.getTexture())+1;
			int indexVn = chercheIndice(sommet.getNormale(), normales)+1;
			writer.write(" " + indexVp + "/");
			if(indexVt!=0){
				writer.write(Integer.toString(indexVt));
			}
			writer.write("/" + indexVn);
		}
		/*int indexVp1 = positions.indexOf(sommets[0].getPosition())+1;
		int indexVp2 = positions.indexOf(sommets[1].getPosition())+1;
		int indexVp3 = positions.indexOf(sommets[2].getPosition())+1;
		int indexVt1 = textures.indexOf(sommets[0].getTexture())+1;
		int indexVt2 = textures.indexOf(sommets[1].getTexture())+1;
		int indexVt3 = textures.indexOf(sommets[2].getTexture())+1;
		int indexVn1 = chercheIndice(sommets[0].getNormale(), normales)+1;
		int indexVn2 = chercheIndice(sommets[1].getNormale(), normales)+1;
		int indexVn3 = chercheIndice(sommets[2].getNormale(), normales)+1;*/
		
		/*writer.write(indexVp1 + "/");
		writer.write("/" + indexVn1 + " ");
		writer.write(indexVp2 + "/");
		writer.write("/" + indexVn2 + " ");
		writer.write(indexVp3 + "/");
		writer.write("/" + indexVn3);*/
		
		writer.newLine();
	}
	
	public int chercheIndice(VertexNormale vn, List<VertexNormale> normales){
		int indice = -1;
		for(int i=0; i<normales.size(); i++){
			if(vn.equals(normales.get(i))){
				indice = i;
				break;
			}
		}
		return indice;
	}
}
