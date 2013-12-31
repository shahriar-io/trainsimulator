package jts.moteur.ligne.voie.elements;

import java.util.ArrayList;
import java.util.List;

import jts.moteur.geometrie.AngleEuler;
import jts.moteur.geometrie.BasicGeo;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.voie.points.PointExtremite;

public class BSpline extends CourbeElementaire {
	
	private int ordre;
	private int fragmentation = 1000;
	private List<Point> pointsControles;
	private List<Double> noeuds;
	
	public BSpline(PointExtremite p1, PointExtremite p2, int ordre, List<Point> pointsControles, List<Double> noeuds){
		super(p1, p2, TypeElement.BSPLINE, 0);
		this.ordre = ordre;
		this.pointsControles = pointsControles;
		this.noeuds = noeuds;
	}
	
	public BSpline(PointExtremite p1, PointExtremite p2, int ordre, List<Point> pointsControles, List<Double> noeuds, int fragmentation){
		this(p1, p2, ordre, pointsControles, noeuds);
		this.fragmentation = fragmentation;
	}
	
	public BSpline(PointExtremite p1, PointExtremite p2, int ordre, List<Point> pointsControles){
		super(p1, p2, TypeElement.BSPLINE, 0);
		this.ordre = ordre;
		this.pointsControles = pointsControles;
		this.noeuds = new ArrayList<Double>();
		for(int i=0; i<ordre; i++){
			this.noeuds.add(0.0);
		}
		
		int nbFrag = pointsControles.size()-ordre;
		double increment = 1/(double)nbFrag;
		for(int i=0; i<=nbFrag; i++){
			this.noeuds.add(i*increment);
		}
		
		for(int i=0; i<ordre; i++){
			this.noeuds.add(1.000);
		}
	}

	public void calculerLongueur() {
		super.longueur = 0;
		Point p1 = new Point();
		Point p2 = new Point();
		recupererPoint(p1, 0);
		for(int i=1; i<=fragmentation; i++){
			recupererPoint(p2, i/(double)fragmentation);
			super.longueur += p2.getDistance(p1);
			p1 = p2;
		}
	}

	public void recupererPoint(Point point, double ratio) {
		point.setXYZ(0, 0, 0);
		for(int i=0; i<pointsControles.size(); i++){
			Point pointControle = pointsControles.get(i);
			point.transformer(new Point(
					pointControle.getX()*bspline(i, ordre, ratio, noeuds),
					pointControle.getY()*bspline(i, ordre, ratio, noeuds),
					pointControle.getZ()*bspline(i, ordre, ratio, noeuds)));
		}
	}
	
	public void recupererAngle(AngleEuler angle, double ratio){
		double dratio = 1/(double)fragmentation;
		Point p1 = new Point();
		Point p2 = new Point();
		double ratio1 = Math.max(ratio-dratio, 0);
		double ratio2 = Math.min(ratio+dratio, 1);
		recupererPoint(p1, ratio1);
		recupererPoint(p2, ratio2);
		angle.setPsi(BasicGeo.getCap(p1, p2));
		angle.setTheta(Math.asin((p2.getZ()-p1.getZ())/p1.getDistance(p2)));
	}
	
	public static double bspline(int i, int ordre, double t, List<Double> noeuds){
		if((t == 1)&&(i==(noeuds.size()-2*ordre))){
			return 1;
		} else {
			if(ordre==0){
			    if((t>=noeuds.get(i))&&(t<noeuds.get(i+1))) {
			        return 1;
			    } else {
			        return 0;
			    }
			} else {
			    double b1 = bspline(i,ordre-1,t,noeuds);
			    double terme1 = 0;
			    if(b1 != 0){
			    	terme1 = (t-noeuds.get(i))/(noeuds.get(i+ordre) - noeuds.get(i))*b1;
			    }
			    
			    double b2 = bspline(i+1,ordre-1,t,noeuds);
			    double terme2 = 0;
			    if(b2 != 0) {
			        terme2 = (noeuds.get(i+1+ordre)-t)/(noeuds.get(i+1+ordre) - noeuds.get(i+1))*b2;
			    }
			    return terme1 + terme2;
			}
		}
	}
}
