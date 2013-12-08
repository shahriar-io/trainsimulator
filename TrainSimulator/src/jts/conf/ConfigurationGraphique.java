package jts.conf;

import java.io.BufferedWriter;
import java.io.IOException;

import jts.ihm.gui.JtsDimension;

import org.w3c.dom.Element;

public class ConfigurationGraphique {
	
	private JtsDimension dimension;

	public ConfigurationGraphique(){
		
	}
	
	public JtsDimension getDimension() { return dimension; }

	public void setDimension(JtsDimension dimension) { this.dimension = dimension; }
	
	public void load(Element element) {
		int largeur = Integer.parseInt(element.getAttribute("largeur"));
		int hauteur = Integer.parseInt(element.getAttribute("hauteur"));
		dimension = new JtsDimension(largeur, hauteur);
	}

	public void save(BufferedWriter buffer, String indent) throws IOException {
		buffer.write(indent + "<Graphique largeur=\"" + dimension.getLargeur() + "\" hauteur=\"" + dimension.getHauteur() + "\"/>");
		buffer.newLine();
	}
}
