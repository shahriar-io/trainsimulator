package jts.moteur.ligne;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jts.io.xml.AttributXml;
import jts.io.xml.ElementXml;

/**Cette classe représente une ligne ferroviaire parcourable dans la simulation.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Ligne {

	private String name;
	private Circuit circuit;
	private List<ObjetScene> objets;
	
	public Ligne(){
		this.objets = new ArrayList<ObjetScene>();
	}
	
	public String getName(){ return this.name; }
	
	public void setName(String name){ this.name = name; }
	
	public Circuit getCircuit(){ return this.circuit; }
	
	public void setCircuit(Circuit circuit){ this.circuit = circuit; }
	
	public List<ObjetScene> getObjets(){ return this.objets; }
	
	public void addObjet(ObjetScene objet){ this.objets.add(objet); }
	
	public void save(String indent, BufferedWriter writer) throws IOException {
		/*writer.write("<?xml version=\"1.0\"?>");
		writer.newLine();
		writer.write("<Ligne name=\"" + name + "\">");
		writer.newLine();
		
		circuit.save("\t", writer);
		
		writer.write("\t<Objets>");
		writer.newLine();
		for(ObjetScene objet : objets){
			objet.save("\t\t", writer);
		}
		writer.write("\t</Objets>");
		writer.newLine();
		
		writer.write("</Ligne>");
		writer.newLine();*/
		writer.write("<?xml version=\"1.0\"?>");
		writer.newLine();
		this.save().write(indent, writer);
	}
	
	public ElementXml save(){
		ElementXml element = new ElementXml("Ligne");
		element.addAttribut(new AttributXml("name", name));
		element.addElement(circuit.save());
		ElementXml objetsElement = new ElementXml("Objets");
		element.addElement(objetsElement);
		for(ObjetScene objet : objets){
			objetsElement.addElement(objet.save());
		}
		
		return element;
	}
}
