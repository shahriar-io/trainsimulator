package jts.ihm.gui.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

/**Cette classe indique la position de la poignée de commande combiné.
 * 
 * @author Yannick BISIAUX
 *
 */
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
		
		double dx = 0.05*this.getPreferredSize().getWidth();
		
		double xt = Math.max(0, commande)*(this.getPreferredSize().getWidth()-2*dx);
		double yt1 = 0.95*this.getPreferredSize().getHeight();
		double yt2 = 0.55*this.getPreferredSize().getHeight();
		
		/*g2d.drawLine(xt, yt1, xt+dx, yt2);
		g2d.drawLine(xt+2*dx, yt1, xt+dx, yt2);
		g2d.drawLine(xt, yt1, xt+2*dx, yt1);*/
		g2d.draw(new Line2D.Double(xt, yt1, xt+dx, yt2));
		g2d.draw(new Line2D.Double(xt+2*dx, yt1, xt+dx, yt2));
		g2d.draw(new Line2D.Double(xt, yt1, xt+2*dx, yt1));
		
		double xf = (1+Math.min(0, commande))*(this.getPreferredSize().getWidth()-2*dx);
		double yf1 = 0.05*this.getPreferredSize().getHeight();
		double yf2 = 0.45*this.getPreferredSize().getHeight();
		
		/*g2d.drawLine(xf, yf1, xf+dx, yf2);
		g2d.drawLine(xf+2*dx, yf1, xf+dx, yf2);
		g2d.drawLine(xf, yf1, xf+2*dx, yf1);*/
		g2d.draw(new Line2D.Double(xf, yf1, xf+dx, yf2));
		g2d.draw(new Line2D.Double(xf+2*dx, yf1, xf+dx, yf2));
		g2d.draw(new Line2D.Double(xf, yf1, xf+2*dx, yf1));
	}
}
