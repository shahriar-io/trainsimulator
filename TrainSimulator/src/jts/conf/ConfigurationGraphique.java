package jts.conf;

import java.io.BufferedWriter;
import java.io.IOException;

import jts.ihm.gui.JtsDimension;

import org.w3c.dom.Element;

public class ConfigurationGraphique {
	
	private JtsDimension dimension;
	private JmeRenderer renderer;

	public ConfigurationGraphique(){
		
	}
	
	public JtsDimension getDimension() { return dimension; }

	public void setDimension(JtsDimension dimension) { this.dimension = dimension; }
	
	public JmeRenderer getRenderer() { return renderer; }

	public void setRenderer(JmeRenderer renderer) { this.renderer = renderer; }

	public void load(Element element) {
		int largeur = Integer.parseInt(element.getAttribute("largeur"));
		int hauteur = Integer.parseInt(element.getAttribute("hauteur"));
		dimension = new JtsDimension(largeur, hauteur);
		renderer = JmeRenderer.values()[Integer.parseInt(element.getAttribute("renderer"))];
	}

	public void save(BufferedWriter buffer, String indent) throws IOException {
		buffer.write(indent + "<Graphique largeur=\"" + dimension.getLargeur() + "\" hauteur=\"" + dimension.getHauteur() + "\" renderer=\"" + renderer.ordinal() + "\"/>");
		buffer.newLine();
	}
}
