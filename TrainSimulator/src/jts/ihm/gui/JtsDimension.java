package jts.ihm.gui;

import java.awt.Dimension;

public class JtsDimension {
	
	private int largeur;
	private int hauteur;

	public JtsDimension(int largeur, int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public Dimension getAwtDimension(){
		return new Dimension(largeur, hauteur);
	}
	
	public String toString(){
		return largeur + "x" + hauteur;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof JtsDimension){
			JtsDimension dimension = (JtsDimension)obj;
			return (dimension.largeur == largeur && dimension.hauteur == hauteur);
		} else {
			return true;
		}
	}
}
