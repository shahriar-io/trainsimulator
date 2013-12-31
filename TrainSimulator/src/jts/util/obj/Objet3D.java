package jts.util.obj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Objet3D {

	private String nomLibrairieMateriel;
	protected List<VertexPosition> positions;
	protected List<VertexTexture> textures;
	private List<VertexNormale> normales;
	private List<Groupe> groupes;

	public Objet3D(String nomLibrairieMateriel){
		this.nomLibrairieMateriel = nomLibrairieMateriel;
		this.positions = new ArrayList<VertexPosition>();
		this.textures = new ArrayList<VertexTexture>();
		this.normales = new ArrayList<VertexNormale>();
		this.groupes = new ArrayList<Groupe>();
	}
	
	public List<VertexPosition> getPositions() {
		return positions;
	}

	public List<VertexTexture> getTextures() {
		return textures;
	}

	public List<VertexNormale> getNormales() {
		return normales;
	}

	public void addPosition(VertexPosition vp){
		this.positions.add(vp);
	}

	public void addTexture(VertexTexture vt){
		this.textures.add(vt);
	}
	

	public void addGroupe(Groupe groupe){
		this.groupes.add(groupe);
	}

	public List<Groupe> getGroupes(){
		return this.groupes;
	}

	public void recalculer(){
		//positions.clear();
		//textures.clear();
		normales.clear();
		for(Groupe groupe : groupes){
			for(Facette facette : groupe.getFacettes()){
				facette.recalculer();
				for(Vertex sommet : facette.getSommets()){
					//this.ajouterPositionUnique(sommet.getPosition());
					//this.ajouterTextureUnique(sommet.getTexture());
					this.ajouterNormaleUnique(sommet.getNormale());
				}
			}
		}
	}

	private void ajouterPositionUnique(VertexPosition position){
		boolean toAdd = true;
		for(VertexPosition vp : positions){
			if(vp.equals(position)){
				toAdd = false;
				break;
			}
		}
		if(toAdd){
			positions.add(position);
		}
	}

	private void ajouterTextureUnique(VertexTexture texture){
		boolean toAdd = true;
		for(VertexTexture vt : textures){
			if(vt.equals(texture)){
				toAdd = false;
				break;
			}
		}
		if(toAdd){
			textures.add(texture);
		}
	}

	private void ajouterNormaleUnique(VertexNormale normale){
		boolean toAdd = true;
		for(VertexNormale vn : normales){
			if(vn.equals(normale)){
				toAdd = false;
				break;
			}
		}
		if(toAdd){
			normales.add(normale);
		}
	}

	public void writeObj(File file){
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);

			this.writeObj(writer);

			writer.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeObj(BufferedWriter writer) throws IOException{
		this.recalculer();
		writer.write("mtllib " + nomLibrairieMateriel);
		writer.newLine();
		for(VertexPosition vp : positions){
			vp.writeObj(writer);
		}
		for(VertexTexture vt : textures){
			vt.writeObj(writer);
		}
		for(VertexNormale vn : normales){
			vn.writeObj(writer);
		}
		for(Groupe groupe : groupes){
			groupe.writeObj(writer, positions, textures, normales);
		}
	}

	public static void main(String[] args){
		Objet3D obj3d = new Objet3D("");
		Groupe groupe = new Groupe(obj3d, false, "branches", "Ballast");
		obj3d.addGroupe(groupe);
		groupe.addPosition(new VertexPosition(-0.2, 4, -0.2));
		groupe.addPosition(new VertexPosition(-0.2, 4, 0.2));
		groupe.addPosition(new VertexPosition(0.2, 4, 0.2));
		groupe.addPosition(new VertexPosition(0.2, 4, -0.2));
		groupe.addPosition(new VertexPosition(-1.0, 3.5, -1.0));
		groupe.addPosition(new VertexPosition(-1.0, 3.5, 1.0));
		groupe.addPosition(new VertexPosition(1.0, 3.5, 1.0));
		groupe.addPosition(new VertexPosition(1.0, 3.5, -1.0));
		groupe.addPosition(new VertexPosition(-1.5, 2.25, -1.5));
		groupe.addPosition(new VertexPosition(-1.5, 2.25, 1.5));
		groupe.addPosition(new VertexPosition(1.5, 2.25, 1.5));
		groupe.addPosition(new VertexPosition(1.5, 2.25, -1.5));
		groupe.addPosition(new VertexPosition(-1.0, 1.0, -1.0));
		groupe.addPosition(new VertexPosition(-1.0, 1.0, 1.0));
		groupe.addPosition(new VertexPosition(1.0, 1.0, 1.0));
		groupe.addPosition(new VertexPosition(1.0, 1.0, -1.0));
		groupe.addPosition(new VertexPosition(0.0, 0.1, 0.0));

		
		groupe.addFacette(new Facette(obj3d.positions, 0, 1, 2, 3));
		groupe.addFacette(new Facette(obj3d.positions, 0, 4, 5, 1));
		groupe.addFacette(new Facette(obj3d.positions, 1, 5, 6, 2));
		groupe.addFacette(new Facette(obj3d.positions, 2, 6, 7, 3));
		groupe.addFacette(new Facette(obj3d.positions, 3, 7, 4, 0));
		groupe.addFacette(new Facette(obj3d.positions, 4, 8, 9, 5));
		groupe.addFacette(new Facette(obj3d.positions, 5, 9, 10, 6));
		groupe.addFacette(new Facette(obj3d.positions, 6, 10, 11, 7));
		groupe.addFacette(new Facette(obj3d.positions, 7, 11, 8, 4));
		groupe.addFacette(new Facette(obj3d.positions, 8, 12, 13, 9));
		groupe.addFacette(new Facette(obj3d.positions, 9, 13, 14, 10));
		groupe.addFacette(new Facette(obj3d.positions, 10, 14, 15, 11));
		groupe.addFacette(new Facette(obj3d.positions, 11, 15, 12, 8));

		obj3d.writeObj(new File("test_arbre.obj"));
	}
}
