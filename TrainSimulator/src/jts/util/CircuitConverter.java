package jts.util;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.ligne.Circuit;
import jts.moteur.ligne.CircuitSections;
import jts.moteur.ligne.Ligne;
import jts.moteur.ligne.ObjetScene;
import jts.moteur.ligne.voie.Section;
import jts.moteur.ligne.voie.elements.CourbeElementaire;
import jts.moteur.ligne.voie.points.PointExtremite;
import jts.moteur.ligne.voie.points.PointFrontiere;
import jts.moteur.ligne.voie.points.PointPassage;

public class CircuitConverter {

	public static Ligne convert(CircuitSections circuitSections){
		Ligne ligne = new Ligne();
		Circuit circuit = new Circuit();
		
		List<PointExtremite> pointsExtremite = new ArrayList<PointExtremite>();
		List<CourbeElementaire> courbesElementaires = new ArrayList<CourbeElementaire>();
		
		for(Section section : circuitSections.getSections()){
			for(PointExtremite pointExtremite : section.getPointsExtremites()){
				pointsExtremite.add(pointExtremite);
			}
			for(CourbeElementaire courbe : section.getElements()){
				courbesElementaires.add(courbe);
				circuit.addCourbeElementaire(courbe);
			}
			if(section.getNomObjet()!=null){
				ligne.addObjet(new ObjetScene(section.getPositionAbsolue(), section.getAngle(), section.getNomObjet()));
			}
		}
		
		//On parcourt les points extremites
		for(int i=0; i<pointsExtremite.size(); i++){
			PointExtremite pointExtremite = pointsExtremite.get(i);
			if(pointExtremite instanceof PointFrontiere){
				//On recr�e le premier point fronti�re pour le transformer en point de passage
				PointFrontiere pf1 = (PointFrontiere)pointExtremite;
				PointPassage pp = new PointPassage(pf1.getX(), pf1.getY(), pf1.getZ(), pf1.getPhi());
				circuit.addPointPassage(pp);
				//On le remplace
				CourbeElementaire ce1 = pf1.getElementBase();
				ce1.replace(pf1, pp);
				pp.setElement(ce1);
				
				//Si possible on remplace le point fronti�re auquel il �tait connect� par le point de passage
				PointFrontiere pf2 = pf1.getConnexion();
				if(pf2 != null){
					CourbeElementaire ce2 = pf2.getElementBase();
					ce2.replace(pf2, pp);
					pp.setElement(ce2);
					
					//Si on a affaire � un montage tete beche, on inverse le phi
					if(ce1.getP1() == pp && ce2.getP1() == pp){
						ce2.inverserPhi1();
					}
					if(ce1.getP2() == pp && ce2.getP2() == pp){
						ce2.inverserPhi2();
					}
				}
				
				//Facultatifs ?
				//pointsExtremite.remove(pf1);
				pointsExtremite.remove(pf2);
			} else {
				circuit.addPointPassage((PointPassage)pointExtremite);
			}
		}
		
		ligne.setCircuit(circuit);
		return ligne;
	}
}
