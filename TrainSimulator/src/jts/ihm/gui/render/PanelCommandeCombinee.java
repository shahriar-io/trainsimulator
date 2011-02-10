package jts.ihm.gui.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelCommandeCombinee extends JPanel {

	private double commande;

	public PanelCommandeCombinee(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
	}
	
	public void setCommande(double commande){ this.commande = commande; }
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.drawString(Integer.toString((int)(commande*100)), 20, 20);
	}
}
