package jts.ihm.gui.render;

import java.awt.Dimension;

import javax.swing.JPanel;

import jts.conf.ConfigurationGraphique;
import jts.ihm.clavier.EcouteurClavier;
import jts.ihm.gui.JtsDimension;
import jts.ihm.gui.render.j3d.RenduJ3D;
import jts.ihm.gui.render.jme.RenduJME;

/**Ceci est le panel de jeu vue intérieur, composé de la vue extérieur et d'un tableau de bord.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelConduite extends JPanel {
	
	//private static final int confGraphique.getDimension().getLargeur()  = 1280;
    //private static final int confGraphique.getDimension().getHauteur() = 960;
	
	private JPanel panelCanvas;
	private PanelTableauBord tableauBord;
	private PanelAiguillage panelAiguillage;
	//private JtsCanvas3D c3d;

	public PanelConduite(){
		super();
	}
	
	public InterfaceMoteur3D init(ConfigurationGraphique confGraphique, EcouteurClavier ecouteur){
		this.setLayout(null);
		this.setFocusable(true);
		//this.requestFocus();
		
		final InterfaceMoteur3D moteur3d;
		if(false){
			moteur3d = new RenduJ3D(confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2);
		} else {
			moteur3d = new RenduJME(confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2, confGraphique, ecouteur);
			((RenduJME)moteur3d).startCanvas();
		}
		
        panelCanvas = new JPanel();
        panelCanvas.setLayout(null);
        moteur3d.getCanvas().setBounds(0, 0, confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2);
        panelCanvas.add(moteur3d.getCanvas());
        panelCanvas.setOpaque(false);
        panelCanvas.setPreferredSize(new Dimension(confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2));
        panelCanvas.setFocusable(true);
        panelCanvas.setBounds(0, 0, confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2);
        panelCanvas.addKeyListener(ecouteur);
        this.add(panelCanvas);
        
        tableauBord = new PanelTableauBord();
        tableauBord.init(confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2);
        tableauBord.setFocusable(true);
        tableauBord.setBounds(0, confGraphique.getDimension().getHauteur()/2, confGraphique.getDimension().getLargeur(), confGraphique.getDimension().getHauteur()/2);
        tableauBord.addKeyListener(ecouteur);
        //tableauBord.requestFocus();
        this.add(tableauBord);
        
        this.setVisible(true);
        this.setPreferredSize(confGraphique.getDimension().getAwtDimension());
        
        this.panelAiguillage = new PanelAiguillage();
		this.add(panelAiguillage);
		this.panelAiguillage.setBounds(800, 800, 50, 50);
		/*try {
			this.panelAiguillage.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}*/
        
        return moteur3d;
	}
	
	public PanelTableauBord getTableauBord(){
		return tableauBord;
	}
}
