package jts.ihm.gui;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;

import jts.ihm.Ihm;
import jts.ihm.clavier.EcouteurClavier;
import jts.ihm.gui.ctrl.PanelVisuRoute;
import jts.ihm.gui.launch.PanelChoix;
import jts.ihm.gui.render.PanelConduite;
import jts.ihm.gui.render.j3d.InterfaceJ3D;
import jts.ihm.gui.render.j3d.Vue3D;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.CircuitSections;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.train.Locomotive;
import jts.moteur.train.Train;

public class Gay implements InterfaceGraphique {

	private Ihm ihm;
	
	private JFrame fenetreCtrl;
	private PanelVisuRoute visuRoute;
	private PanelConduite render;
	private InterfaceJ3D vue3D;
	
	private JFrame fentreLancement;
	private PanelChoix lancement;
	
	public Gay(Ihm ihm){
		this.ihm = ihm;
	}
	
	public void afficherEcranDemarrage(boolean joystickActif){
		this.lancement = new PanelChoix(this.ihm);
		this.lancement.init(joystickActif);
		this.fentreLancement = new JFrame("Driver Simulator Demarrage");
		this.fentreLancement.add(lancement);
		this.fentreLancement.setLocation(200, 200);
		this.fentreLancement.setVisible(true);
		this.fentreLancement.pack();
		this.fentreLancement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void detruireEcranDemarrage(){
		//this.lancement = null;
		this.fentreLancement.dispose();
	}
	
	public void init(EcouteurClavier clavier){
		this.render = new PanelConduite();
		Canvas3D c3d = this.render.init();
		this.vue3D = new Vue3D(c3d);
		JFrame fenetreRender = new JFrame("Render");
		fenetreRender.add(render);
		fenetreRender.setVisible(true);
		fenetreRender.pack();
		fenetreRender.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetreRender.setFocusable(true);
		fenetreRender.setResizable(false);
		fenetreRender.requestFocus();
		fenetreRender.addKeyListener(clavier);
		
		this.fenetreCtrl = new JFrame("Driver Simulator");
		this.visuRoute = new PanelVisuRoute();
		this.fenetreCtrl.add(visuRoute);
		this.fenetreCtrl.setVisible(true);
		this.fenetreCtrl.pack();
		this.fenetreCtrl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetreCtrl.setLocation(800, 0);
	}
	
	public void chargerTerrain(CircuitSections circuit){
		for (Section section : circuit.getSections()){
			vue3D.dessinerSurface(section.getFrontiere().getSommets());
			if(section.getNomObjet() != null){
				vue3D.chargerObjet(
						(float)section.getPositionAbsolue().getY(),
						(float)section.getPositionAbsolue().getZ(),
						(float)section.getPositionAbsolue().getX(),
						section.getNomObjet());
			}
			for (CourbeElementaire element : section.getElements()){
				vue3D.dessinerLigne(element.getPointsRemarquables());
			}
		}
		//vue3D.creerVoiture1(1.7f, 1.7f, 3f);
		//vue3D.creerVoiture2(1.7f, 1.7f, 3f);
		//vue3D.setHeure(12);
	}

	public void afficherLigne(Ligne ligne) {
		Train trainJoueur = ligne.getCircuit().getTrainJoueur();
		render.getTableauBord().afficherTrain(trainJoueur);
		
		visuRoute.setLigne(ligne);
		visuRoute.repaint();
		
		//vue3D.deplacerCamera(0, 2.0f, -25, (float)Math.PI, 0);
		Locomotive locomotiveJoueur = trainJoueur.getLocomotiveTete();
		Point point = locomotiveJoueur.getObservation();
		vue3D.deplacerCamera((float)point.getY(), (float)point.getZ(), (float)point.getX(), (float)((Math.PI/2 - locomotiveJoueur.getOrientation().getPsi())), 0);
		
		/*Voiture voiture = scene.getVoitures().get(0);
		Point pVoiture = voiture.getPositionAvant();
		Angle3D angleVoiture = voiture.getAngle();
		vue3D.deplacerVoiture1((float)pVoiture.y, (float)pVoiture.z, (float)pVoiture.x, (float)angleVoiture.theta, 0);
		
		Voiture voiture2 = scene.getVoitures().get(1);
		Point pVoiture2 = voiture2.getPositionAvant();
		Angle3D angleVoiture2 = voiture2.getAngle();
		vue3D.deplacerVoiture2((float)pVoiture2.y, (float)pVoiture2.z, (float)pVoiture2.x, (float)angleVoiture2.theta, 0);
		
		render.getTableauBord().afficherVoiture(scene.getVoitureJoueur());*/
	}
}
