package jts.ihm.gui.render.j3d;

import java.awt.Color;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.J3DGraphics2D;

@SuppressWarnings("serial")
public class JtsCanvas3D extends Canvas3D {
	
	private int largeur;
	private int hauteur;
	
	private double cap;

	public JtsCanvas3D (GraphicsConfiguration graphicsConfiguration, int largeur, int hauteur){
		super(graphicsConfiguration);
		
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		this.setSize(largeur, hauteur);
	}
	
	public void setCap(double cap){
		this.cap = cap*180/Math.PI;
	}
	
	public void postRender() {
		J3DGraphics2D g2d = this.getGraphics2D();
        g2d.setColor(new Color(255, 0, 0, 127));
        
        int largeurMilieu = largeur/2;
        //g2d.drawLine(largeurMilieu-(int)cap, 10, largeurMilieu-(int)cap, 50);
        g2d.flush(false);
    }
}