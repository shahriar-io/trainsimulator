package jts.moteur.ligne;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jts.Controleur;
import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;

public class Parcelle {
	
	private final static DecimalFormat DF = new DecimalFormat("0000");
	
	private int x;
	private int y;
	private List<ObjetScene> objets;
	private Terrain terrain;

	public Parcelle(int x, int y){
		this.x = x;
		this.y = y;
		this.objets = new ArrayList<ObjetScene>();
		this.terrain = new Terrain();
		this.terrain.init(1);
	}
	
	public int getX(){ return this.x; }
	
	public int getY(){ return this.y; }
	
	public List<ObjetScene> getObjets(){ return this.objets; }
	
	public void addObjet(ObjetScene objet){ this.objets.add(objet); }
	
	public void loadObjets(){
		
	}
	
	public void clearObjets(){
		this.objets.clear();
	}
	
	public Terrain getTerrain(){ return this.terrain; }
	
	public void loadTerrain(String dossier){
		File fichierTerrain = new File(dossier + "/" + getNom() + ".ter");
		if(fichierTerrain.exists()){
			this.terrain = new Terrain();
			try {
				this.terrain.load(fichierTerrain);
			} catch (IOException e) {
				Controleur.LOG.error("Impossible de charger le terrain en " + x + "/" + y + " : " + e.getMessage());
			}
		}
	}
	
	public void clearTerrain(){
		terrain = null;
	}
	
	public String getNom(){
		String nom = "";
		
		if(x>=0){
			nom += "P";
		} else {
			nom += "M";
		}
		nom += DF.format(Math.abs(x));
		
		if(y>=0){
			nom += "P";
		} else {
			nom += "M";
		}
		nom += DF.format(Math.abs(y));
		
		return nom;
	}
	
	public boolean equals(Object o){
		boolean same = false;
		
		if(o instanceof Parcelle){
			Parcelle p = (Parcelle)o;
			if(p.x == x && p.y == y){
				same = true;
			}
		}
		
		return same;
	}
	
	public void save(String dossier) throws IOException {
		FileWriter fw = new FileWriter(dossier + "/parcelles/" + this.getNom() + ".xml");
		BufferedWriter buffer = new BufferedWriter(fw);
		buffer.write("<?xml version=\"1.0\"?>");
		buffer.newLine();
		this.saveXml().write("", buffer);
		buffer.close();
		fw.close();
	}
	
	public void saveTerrain(String dossier, boolean brut) throws IOException{
		this.terrain.save(new File(dossier + "/parcelles/" + this.getNom() + ".ter"), brut);
	}
	
	public ElementXml saveXml(){
		ElementXml element = new ElementXml("Parcelle");
		element.addAttribut(new AttributXml("x", Integer.toString(x)));
		element.addAttribut(new AttributXml("y", Integer.toString(y)));
		ElementXml objetsElement = new ElementXml("Objets");
		element.addElement(objetsElement);
		for(ObjetScene objet : objets){
			objetsElement.addElement(objet.save());
		}
		
		return element;
	}
}
