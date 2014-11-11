package jts.moteur.ligne;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.io.ParcelleLoader;
import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;
import jts.moteur.geometrie.CoordonneesGps;

/**Cette classe représente une ligne ferroviaire parcourable dans la simulation.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Ligne {

	//Gestion des aspects informatiques
	/**Nom du dossier de la ligne*/
	private String nomDossier;
	/**Chemin pour accéder au dossier de la ligne*/
	private String chemin;
	
	private String name;
	private CoordonneesGps origine;
	private Circuit circuit;
	//private List<ObjetScene> objets;
	private List<Parcelle> parcelles;
	
	public Ligne(String nomDossier){
		//this.objets = new ArrayList<ObjetScene>();
		this.nomDossier = nomDossier;
		this.chemin = "data/lignes/" + nomDossier;
		this.parcelles = new ArrayList<Parcelle>();
	}
	
	public String getName(){ return this.name; }
	
	public void setName(String name){ this.name = name; }
	
	public CoordonneesGps getOrigine() { return origine; }

	public void setOrigine(CoordonneesGps origine) { this.origine = origine; }

	public Circuit getCircuit(){ return this.circuit; }
	
	public void setCircuit(Circuit circuit){ this.circuit = circuit; }
	
	public List<Parcelle> getParcelles(){ return this.parcelles; }
	
	public void addObjet(ObjetScene objet){
		//On cherche à savoir à quelle parcelle appartient l'objet
		int x = (int)Math.floor(objet.getPoint().getX()/1000.0);
		int y = (int)Math.floor(objet.getPoint().getY()/1000.0);
		
		Parcelle parcelle = this.getParcelle(x, y);
		if(parcelle == null){
			parcelle = this.creerParcelle(x, y);
		}
		parcelle.addObjet(objet);
	}
	
	/**Renvoie la parcelle de la ligne aux coordonnées demandées, si elle existe.
	 * 
	 * @param x <b>int</b> la coordonnée Est de la parcelle
	 * @param y <b>int</b> la coordonnée Nord de la parcelle
	 * @return la <code>Parcelle</code> recherchée (peut-être <b>null</b>)
	 */
	public Parcelle getParcelle(int x, int y){
		Parcelle parcelleCherchee = null;
		
		for(Parcelle parcelle : parcelles){
			if((parcelle.getX() == x) && (parcelle.getY() == y)){
				parcelleCherchee = parcelle;
			}
		}
		
		return parcelleCherchee;
	}
	
	/**Crée une nouvelle parcelle vide aux coordonnées demandées.
	 * 
	 * @param x <b>int</b> la coordonnée Est de la parcelle
	 * @param y <b>int</b> la coordonnée Nord de la parcelle
	 */
	public Parcelle creerParcelle(int x, int y){
		Parcelle parcelle = new Parcelle(x, y);
		this.parcelles.add(parcelle);
		return parcelle;
	}
	
	/**Pré-charge toutes les parcelles de l'itinéraire.
	 * 
	 */
	public void preloadParcelles(){
		File dossierParcelles = new File(this.chemin + "/parcelles");
		for(File fichier : dossierParcelles.listFiles()){
			//On suppose que les seuls fichiers .xml du dossier décriront des parcelles
			if(fichier.getName().endsWith(".xml")){
				Parcelle parcelle = ParcelleLoader.load(fichier);
				this.parcelles.add(parcelle);
				parcelle.loadTerrain(this.chemin + "/parcelles");
			}
		}
	}
	
	public void save(String indent, BufferedWriter writer) throws IOException {
		writer.write("<?xml version=\"1.0\"?>");
		writer.newLine();
		this.saveXml().write(indent, writer);
	}
	
	public void save() throws IOException {
		FileWriter fw = new FileWriter(chemin + "/" + nomDossier + ".xml");
		BufferedWriter buffer = new BufferedWriter(fw);
		buffer.write("<?xml version=\"1.0\"?>");
		buffer.newLine();
		this.saveXml().write("", buffer);
		buffer.close();
		fw.close();
	}
	
	public ElementXml saveXml(){
		ElementXml element = new ElementXml("Ligne");
		element.addAttribut(new AttributXml("name", name));
		element.addElement(origine.save());
		element.addElement(circuit.save());
		ElementXml objetsElement = new ElementXml("Objets");
		element.addElement(objetsElement);
		/*for(ObjetScene objet : objets){
			objetsElement.addElement(objet.save());
		}*/
		
		return element;
	}
	
	public void saveParcelles() throws IOException{
		for(Parcelle parcelle : parcelles){
			parcelle.save(chemin);
			parcelle.saveTerrain(chemin, true);
		}
	}
}
