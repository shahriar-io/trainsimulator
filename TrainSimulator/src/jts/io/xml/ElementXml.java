package jts.io.xml;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElementXml {

	private String nom;
	private List<AttributXml> attributs;
	private List<ElementXml> elements;
	
	public ElementXml(String nom){
		this.nom = nom;
		this.attributs = new ArrayList<AttributXml>();
		this.elements = new ArrayList<ElementXml>();
	}

	public String getNom() { return nom; }

	public void setNom(String nom) { this.nom = nom; }

	public List<AttributXml> getAttributs() { return attributs; }
	
	public void addAttribut(AttributXml attribut){ this.attributs.add(attribut); }

	public List<ElementXml> getElements() { return elements; }
	
	public void addElement(ElementXml element){ this.elements.add(element); }
	
	public void write(String indent, BufferedWriter writer) throws IOException {
		writer.write(indent + "<" + nom);
		for(AttributXml attribut : attributs){
			attribut.write(writer);
		}
		if(elements.isEmpty()){
			writer.write("/>");
		} else {
			writer.write(">");
			writer.newLine();
			for(ElementXml element : elements){
				element.write(indent + "\t", writer);
			}
			writer.write(indent + "</" + nom + ">");
		}
		
		writer.newLine();
	}
}
