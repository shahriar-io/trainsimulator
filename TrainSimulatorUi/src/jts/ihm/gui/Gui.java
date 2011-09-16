package jts.ihm.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.media.j3d.Canvas3D;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jts.ihm.InterfaceHommeMachine;
import jts.ihm.gui.ctrl.PanelVisuRoute;
import jts.ihm.gui.render.PanelConduite;
import jts.ihm.gui.render.j3d.InterfaceJ3D;
import jts.ihm.gui.render.j3d.Vue3D;
import jts.log.Log;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.ObjetScene;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.train.Locomotive;
import jts.moteur.train.Train;

/**Cette classe gère l'interface graphique du logiciel.
 * 
 * @author Yannick BISIAUX
 *
 */
public class Gui implements InterfaceGraphique {
	
	public static Dimension PANEL_DIMENSION = new Dimension(800, 600);
	public static Font POLICE_TITRE = new Font("Agency FB", 3, 24);
	
	private InterfaceHommeMachine ihm;
	
	private JFrame fenetre;
	private JPanel panelCourant;
	private PanelConduite render;
	private InterfaceJ3D vue3D;
	private JFrame fenetreCtrl;
	private PanelVisuRoute visuRoute;
	
	public Gui(InterfaceHommeMachine ihm){
		this.ihm = ihm;
	}
	
	public InterfaceHommeMachine getIhm(){ return this.ihm; }
	
	public void init() {
		panelCourant = new PanelDemarrage(this);
		try {
			((PanelDemarrage)panelCourant).chargerArriereFond();
		} catch (IOException e) {
			Log.getInstance().logInfo("Impossible de lire l'image d'arrière fond de démarrage");
		}
		fenetre = new JFrame("Java Train Simulator");
		fenetre.add(panelCourant);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setResizable(false);
		fenetre.addWindowListener(new EcouteurFermeture(ihm.getControleur()));
		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenetre.setVisible(true);
	}
	
	public void afficherEcranDemarrage() {
		PanelDemarrage panelDemarrage = new PanelDemarrage(this);
		try {
			panelDemarrage.chargerArriereFond();
		} catch (IOException e) {
			Log.getInstance().logInfo("Impossible de lire l'image d'arrière fond de démarrage");
		}
		fenetre.getContentPane().removeAll();
		fenetre.add(panelDemarrage);
		this.relocaliserFenetre();
	}
	
	public void afficherEcranChoixScenario() {
		PanelChoixScenario panelChoixScenario = new PanelChoixScenario(this);
		try {
			panelChoixScenario.chargerArriereFond();
		} catch (IOException e) {
			Log.getInstance().logInfo("Impossible de lire l'image d'arrière fond de démarrage");
		}
		fenetre.getContentPane().removeAll();
		fenetre.add(panelChoixScenario);
		this.relocaliserFenetre();
	}
	
	public void afficherEcranReglages() {
		PanelReglages panelReglages = new PanelReglages(this);
		try {
			panelReglages.chargerArriereFond();
		} catch (IOException e) {
			Log.getInstance().logInfo("Impossible de lire l'image d'arrière fond de règlages");
		}
		fenetre.getContentPane().removeAll();
		fenetre.add(panelReglages);
		this.relocaliserFenetre();
	}
	
	public void afficherEcranJeu(){
		PanelConduite panelConduite = new PanelConduite();
		fenetre.getContentPane().removeAll();
		fenetre.add(panelConduite);
		
		this.render = panelConduite;
		Canvas3D c3d = this.render.init();
		this.vue3D = new Vue3D(c3d);
		fenetre.setFocusable(true);
		fenetre.requestFocus();
		//fenetre.addKeyListener(clavier);
		this.relocaliserFenetre();
		
		this.fenetreCtrl = new JFrame("Driver Simulator");
		this.visuRoute = new PanelVisuRoute();
		this.fenetreCtrl.add(visuRoute);
		this.fenetreCtrl.setVisible(true);
		this.fenetreCtrl.pack();
		this.fenetreCtrl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetreCtrl.setLocation(800, 0);
	}
	
	public void chargerTerrain(Ligne ligne){
		for (Section section : ligne.getCircuit().getSections()){
			vue3D.dessinerSurface(section.getFrontiere().getSommets());
			/*if(section.getNomObjet() != null){
				vue3D.chargerObjet(
						(float)section.getPositionAbsolue().getY(),
						(float)section.getPositionAbsolue().getZ(),
						(float)section.getPositionAbsolue().getX(),
						section.getNomObjet());
			}*/
			for (CourbeElementaire element : section.getElements()){
				vue3D.dessinerLigne(element.getPointsRemarquables());
			}
		}
		
		for(ObjetScene objet : ligne.getObjets()){
			vue3D.chargerObjet(
					(float)objet.getPoint().getY(),
					(float)objet.getPoint().getZ(),
					(float)objet.getPoint().getX(),
					objet.getNomObjet());
		}
	}

	public void afficherLigne(Ligne ligne) {
		Train trainJoueur = ligne.getCircuit().getTrainJoueur();
		render.getTableauBord().afficherTrain(trainJoueur);
		
		visuRoute.setLigne(ligne);
		visuRoute.repaint();
		
		Locomotive locomotiveJoueur = trainJoueur.getLocomotiveTete();
		Point point = locomotiveJoueur.getObservation();
		vue3D.deplacerCamera((float)point.getY(), (float)point.getZ(), (float)point.getX(), (float)((Math.PI/2 - locomotiveJoueur.getOrientation().getPsi())), 0);
	}
	
	private void relocaliserFenetre(){
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}
	
	public static JLabel creerArriereFondTransparent(int x, int y, int largeur, int hauteur){
		BufferedImage imageFondJoystick = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
		int valeurRGB = 200;
		int rgb = valeurRGB;
		rgb += valeurRGB<<8;
		rgb += valeurRGB<<16;
		rgb += valeurRGB<<24;
		for(int i=0; i<largeur; i++){
			for(int j=0; j<hauteur; j++){
				imageFondJoystick.setRGB(i, j, rgb);
			}
		}
		JLabel arriereFond = new JLabel(new ImageIcon(imageFondJoystick));
		arriereFond.setBounds(x, y, largeur, hauteur);
		return arriereFond;
	}
}
