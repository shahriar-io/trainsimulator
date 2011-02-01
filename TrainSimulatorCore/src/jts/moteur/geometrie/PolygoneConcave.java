package jts.moteur.geometrie;

import java.util.ArrayList;
import java.util.List;

/**Cette classe représente un polygone concave : on peut trouver deux points dans le polygone
 * tel que le segment qui les relie n'est pas dans le polygone.
 * 
 * @author Yannick BISIAUX
 *
 */
public class PolygoneConcave extends Polygone {

	private PolygoneConvexe enveloppeConvexe;
	private List<PolygoneConvexe> creux;
	
	protected PolygoneConcave(List<Point> sommets, List<Vecteur> vecteurs){
		super(sommets, vecteurs);
		creux = new ArrayList<PolygoneConvexe>();
	}
	
	/**Permet de décomposer ce polygone pour faire apparaître un polygone convexe enveloppe
	 * et une liste de polygones convexes "creux" correspondant à ce qu'il faut en retirer
	 * pour obtenir ce polygone concave.
	 * 
	 */
	public void analyser(){
		List<Point> sommetsEnveloppe = new ArrayList<Point>();
		int dernierIndexCorrect = -1;
		
		double dAngle;
		dAngle = BasicGeo.liPi(vecteurs.get(0).getAngle() - vecteurs.get(vecteurs.size()-1).getAngle());
		if(dAngle>0){
			sommetsEnveloppe.add(sommets.get(0));
			dernierIndexCorrect = 0;
		}
		for(int i=0; i<sommets.size()-1; i++){
			dAngle = BasicGeo.liPi(vecteurs.get(i+1).getAngle() - vecteurs.get(i).getAngle());
			if(dAngle>0){
				sommetsEnveloppe.add(sommets.get(i+1));
				if(dernierIndexCorrect != i){
					List<Point> sommetsCreux = new ArrayList<Point>();
					for(int j=i+1; j>=dernierIndexCorrect; j--){
						sommetsCreux.add(sommets.get(j));
					}
					List<Vecteur> vecteursCreux = Polygone.creerVecteursAssocies(sommetsCreux);
					PolygoneConvexe polyConv = new PolygoneConvexe(sommetsCreux, vecteursCreux);
					this.creux.add(polyConv);
				}
				dernierIndexCorrect = i+1;
			} else {
				if(i == sommets.size()-2){
					List<Point> sommetsCreux = new ArrayList<Point>();
					sommetsCreux.add(sommets.get(0));
					for(int j=i; j>=dernierIndexCorrect; j--){
						sommetsCreux.add(sommets.get(j));
					}
					List<Vecteur> vecteursCreux = Polygone.creerVecteursAssocies(sommetsCreux);
					PolygoneConvexe polyConv = new PolygoneConvexe(sommetsCreux, vecteursCreux);
					this.creux.add(polyConv);
				}
			}
		}
		
		List<Vecteur> vecteursEnveloppe = Polygone.creerVecteursAssocies(sommetsEnveloppe);
		enveloppeConvexe = new PolygoneConvexe(sommetsEnveloppe, vecteursEnveloppe);
	}

	/**Indique si le point p est dans le polygone : s'il est dans le polygone enveloppe
	 * mais pas dans les "creux".
	 * 
	 * @param p
	 * @return
	 */
	public boolean isInside(Point p) {
		boolean isInCreux = false;
		for(PolygoneConvexe poly : creux){
			if(poly.isInside(p)){
				isInCreux = true;
				break;
			}
		}
		
		return enveloppeConvexe.isInside(p)&&(!isInCreux);
	}
}
