package jts.ihm;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jts.InterfaceControleur;
import jts.ihm.gui.EcouteurFermeture;

/**Fenêtre d'initialisation du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class InitFrame extends JFrame {
	
	private int progression;
	
	public InitFrame(InterfaceControleur controleur){
		super("Java Train Simulator");
		this.add(new InitPanel());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addWindowListener(new EcouteurFermeture(controleur));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void setProgression(int progression){
		this.progression = progression;
		this.repaint();
	}
	
	
	public class InitPanel extends JPanel{
		public InitPanel(){
			super();
			this.setLayout(null);
			this.setPreferredSize(new Dimension(640, 480));
			
			JLabel labelJTS = new JLabel("JTS");
			labelJTS.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 240));
			labelJTS.setBounds(0, 80, 640, 400);
			labelJTS.setHorizontalAlignment(JLabel.CENTER);
			this.add(labelJTS);
		}
		
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHints(rh); 
			
			if (progression>=20){
				g2d.fillOval(225, 130, 20, 20);
			}
			if (progression>=40){
				g2d.fillOval(310, 105, 30, 30);
			}
			if (progression>=60){
				g2d.fillOval(395, 90, 40, 40);
			}
			if (progression>=80){
				g2d.fillOval(480, 80, 50, 50);
			}
			if (progression>=100){
				g2d.fillOval(565, 72, 60, 60);
			}
		}
	}
}
