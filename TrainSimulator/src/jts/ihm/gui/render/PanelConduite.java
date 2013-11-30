package jts.ihm.gui.render;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.util.logging.Level;

import javax.swing.JPanel;

import jts.ihm.gui.render.j3d.RenduJ3D;
import jts.ihm.gui.render.jme.RenduJME;

/**Ceci est le panel de jeu vue intérieur, composé de la vue extérieur et d'un tableau de bord.
 * 
 * @author Yannick BISIAUX
 *
 */
@SuppressWarnings("serial")
public class PanelConduite extends JPanel {
	
	private static final int PANEL_WIDTH  = 1280;
    private static final int PANEL_HEIGHT = 960;
	
	private JPanel panelCanvas;
	private PanelTableauBord tableauBord;
	private PanelAiguillage panelAiguillage;
	//private JtsCanvas3D c3d;

	public PanelConduite(){
		super();
	}
	
	public InterfaceMoteur3D init(KeyListener keyListener){
		this.setLayout(null);
		this.setFocusable(true);
		//this.requestFocus();
		
		final InterfaceMoteur3D moteur3d;
		if(false){
			moteur3d = new RenduJ3D(PANEL_WIDTH, PANEL_HEIGHT/2);
		} else {
			moteur3d = new RenduJME(PANEL_WIDTH, PANEL_HEIGHT/2);
			((RenduJME)moteur3d).startCanvas();
			java.util.logging.Logger.getLogger("").setLevel(Level.WARNING);
		}
		
        panelCanvas = new JPanel();
        panelCanvas.setLayout(null);
        moteur3d.getCanvas().setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT/2);
        panelCanvas.add(moteur3d.getCanvas());
        panelCanvas.setOpaque(false);
        panelCanvas.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT/2));
        panelCanvas.setFocusable(true);
        panelCanvas.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT/2);
        panelCanvas.addKeyListener(keyListener);
        this.add(panelCanvas);
        
        tableauBord = new PanelTableauBord();
        tableauBord.init(PANEL_WIDTH, PANEL_HEIGHT/2);
        tableauBord.setFocusable(true);
        tableauBord.setBounds(0, PANEL_HEIGHT/2, PANEL_WIDTH, PANEL_HEIGHT/2);
        tableauBord.addKeyListener(keyListener);
        //tableauBord.requestFocus();
        this.add(tableauBord);
        
        this.setVisible(true);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
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
