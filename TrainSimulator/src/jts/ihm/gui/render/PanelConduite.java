package jts.ihm.gui.render;

import java.awt.Dimension;

import javax.swing.JPanel;

import jts.ihm.gui.render.j3d.JtsCanvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

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
	private JtsCanvas3D c3d;

	public PanelConduite(){
		super();
	}
	
	public JtsCanvas3D init(){
		this.setLayout(null);
		this.setFocusable(true);
		this.requestFocus();
		
		c3d = new JtsCanvas3D(SimpleUniverse.getPreferredConfiguration(), PANEL_WIDTH, PANEL_HEIGHT/2);
        //c3d.setSize(PANEL_WIDTH, PANEL_HEIGHT/2);
        
        panelCanvas = new JPanel();
        panelCanvas.add(c3d);
        panelCanvas.setOpaque(false);
        panelCanvas.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT/2));
        panelCanvas.setFocusable(false);
        panelCanvas.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT/2);
        this.add(panelCanvas);
        
        tableauBord = new PanelTableauBord();
        tableauBord.init(PANEL_WIDTH, PANEL_HEIGHT/2);
        tableauBord.setFocusable(false);
        tableauBord.setBounds(0, PANEL_HEIGHT/2, PANEL_WIDTH, PANEL_HEIGHT/2);
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
        
        return c3d;
	}
	
	public PanelTableauBord getTableauBord(){
		return tableauBord;
	}
}
