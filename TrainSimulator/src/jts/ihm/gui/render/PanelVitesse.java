package jts.ihm.gui.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**Ceci est le panel représentant le compteur de vitesse.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelVitesse extends JPanel {
	
	private double vitesseKmh;

	public PanelVitesse(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
	}
	
	public void setVitesse(double kmh){ this.vitesseKmh = kmh; }
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.drawString(Integer.toString((int)vitesseKmh), 20, 20);
	}
}
