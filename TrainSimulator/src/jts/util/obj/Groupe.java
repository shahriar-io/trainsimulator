package jts.util.obj;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Groupe {
	
	private Objet3D objet;
	private boolean adouci;
	private String nom;
	private String nomMateriel;
	
	protected List<VertexPosition> positions;
	protected List<VertexTexture> textures;
	private List<Facette> facettes;
	
	public Groupe(Objet3D objet, String nom, String nomMateriel){
		this(objet, false, nom, nomMateriel);
	}
	
	public Groupe(Objet3D objet, boolean adouci, String nom, String nomMateriel){
		this.objet = objet;
		this.adouci = adouci;
		this.nom = nom;
		this.nomMateriel = nomMateriel;
		this.positions = new ArrayList<VertexPosition>();
		this.textures = new ArrayList<VertexTexture>();
		this.facettes = new ArrayList<Facette>();
	}
	
	public void addPosition(VertexPosition vp){
		this.positions.add(vp);
		objet.positions.add(vp);
	}

	public void addTexture(VertexTexture vt){
		this.textures.add(vt);
		objet.textures.add(vt);
	}

	public boolean isAdouci() {
		return adouci;
	}

	public void setAdouci(boolean adouci) {
		this.adouci = adouci;
	}

	public void addFacette(Facette facette){
		facette.setGroupe(this);
		this.facettes.add(facette);
	}
	
	public List<Facette> getFacettes(){
		return this.facettes;
	}
	
	public void writeObj(BufferedWriter writer, List<VertexPosition> positions, List<VertexTexture> textures, List<VertexNormale> normales) throws IOException{
		writer.write("g " + nom);
		writer.newLine();
		writer.write("usemtl " + nomMateriel);
		writer.newLine();
		for(Facette facette : facettes){
			facette.writeObj(writer, positions, textures, normales);
		}
	}
}
