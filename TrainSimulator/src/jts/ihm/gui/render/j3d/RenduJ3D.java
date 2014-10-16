package jts.ihm.gui.render.j3d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import jts.Controleur;
import jts.ihm.gui.render.InterfaceMoteur3D;
import jts.moteur.geometrie.Point;
import jts.moteur.ligne.ObjetScene;

import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class RenduJ3D implements InterfaceMoteur3D {
	
	private static String REPERTOIRE_OBJETS = "data/objets/";

	private SimpleUniverse simpleU;
	private BranchGroup parent;
	
	private DirectionalLight soleil;
	private Background ciel;
	
	private Loader loader3ds;
	private Loader loaderObj;
	
	private JtsCanvas3D c3d;
	
	public RenduJ3D(int width, int height){
		//this.c3d = c3d;
		c3d = new JtsCanvas3D(SimpleUniverse.getPreferredConfiguration(), width, height);
	    simpleU = new SimpleUniverse(c3d);
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    createSceneGraph();
	    parent.compile();
	    simpleU.addBranchGraph(parent);
	    
	    View view = simpleU.getViewer().getView();
	    view.setBackClipDistance(100);
	    view.setFieldOfView(0.6*Math.PI);
	    view.setSceneAntialiasingEnable(true);
	    
	    GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
	    template.setSceneAntialiasing(GraphicsConfigTemplate3D.PREFERRED);
	    GraphicsConfiguration config =
	                GraphicsEnvironment.getLocalGraphicsEnvironment().
	                 getDefaultScreenDevice().getBestConfiguration(template);
	   
	    loader3ds = new Loader3DS();
	    loaderObj = new ObjectFile();
	}
	
	public Canvas getCanvas(){
		return this.c3d;
	}
	
	private void createSceneGraph() {
	    parent = new BranchGroup();
	    
	    ciel = new Background(new Color3f(0.9f, 0.9f, 1.0f));
	    ciel.setCapability(Background.ALLOW_COLOR_WRITE);
	    ciel.setApplicationBounds(new BoundingSphere(new Point3d(0, 0, 0), 10000));
	    parent.addChild(ciel);
	    
	    
	    AmbientLight light = new AmbientLight(new Color3f(0.5f, 0.5f, 0.5f));
	    //light.setColor(new Color3f(0.5f, 0.5f, 0.5f));
	    light.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 10000));
	    parent.addChild(light);
	    
	    soleil = new DirectionalLight();
	    soleil.setDirection(new Vector3f(-0.1f, -0.5f, 0.1f));
	    //light.setColor(new Color3f(0.5f, 0.5f, 0.5f));
	    soleil.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 10000));
	    parent.addChild(soleil);
	  }
	
	public void deplacerCamera(float x, float y, float z, float theta, float phi){
		//Log.getInstance().logInfo(Float.toString(theta));
		Transform3D mouvement = new Transform3D();
		Transform3D rotation = new Transform3D();
	    mouvement.setTranslation(new Vector3f (x, y, z));
	    rotation.rotY(theta+Math.PI);
	    mouvement.mul(rotation);
	    rotation.rotZ(phi);
	    mouvement.mul(rotation);
	    simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(mouvement);
	    c3d.setCap(theta);
	}
	
	public void dessinerLigne(List<Point> ligne){
		BranchGroup bg = new BranchGroup();
		int stripVertexCounts[] = {ligne.size()};
		LineStripArray ligneArray = new LineStripArray(ligne.size(), LineArray.COORDINATES | LineArray.COLOR_3, stripVertexCounts);
		for (int i=0; i<ligne.size(); i++){
			ligneArray.setCoordinate(i, new Point3d(ligne.get(i).getY(), ligne.get(i).getZ() + 0.2, ligne.get(i).getX()));
			ligneArray.setColor(i, new Color3f(Color.blue));
		}
		bg.addChild(new Shape3D(ligneArray));
		simpleU.addBranchGraph(bg);
	}
	
	public void chargerObjet(ObjetScene objet){
		String nomObjet = objet.getNomObjet();
		String nomComplet = REPERTOIRE_OBJETS + nomObjet;
		File fichierObjet = new File(nomComplet);
	    
	    Scene sceneLoaded = null;
	    
		try {
			if(nomObjet.endsWith(".3DS")||nomObjet.endsWith(".3ds")){
				sceneLoaded = loader3ds.load(nomComplet);
			} else if(nomObjet.endsWith(".obj")) {
				loaderObj.setBasePath(fichierObjet.getParentFile().getAbsolutePath());
				sceneLoaded = loaderObj.load(new FileReader(fichierObjet.getAbsolutePath()));
			}
		} catch (FileNotFoundException e) {
			Controleur.LOG.error("Fichier non trouvé " + fichierObjet.toString() + " : " + e.getMessage());
		} catch (IncorrectFormatException e) {
			Controleur.LOG.error("Format incorrect " + fichierObjet.toString() + " : " + e.getMessage());
		} catch (ParsingErrorException e) {
			Controleur.LOG.error("Erreur de parse " + fichierObjet.toString() + " : " + e.getMessage());
		}
		
		if(sceneLoaded != null){
			BranchGroup scene = sceneLoaded.getSceneGroup();
			Transform3D translation = new Transform3D();
			Transform3D rotation = new Transform3D();
			translation.setTranslation(toVector(objet.getPoint()));
			rotation.rotY(-(float)objet.getAngle().getPsi());
			translation.mul(rotation);
			TransformGroup tgObjet = new TransformGroup();
			tgObjet.setTransform(translation);
			tgObjet.addChild(scene);
			BranchGroup bgObjet = new BranchGroup();
			bgObjet.addChild(tgObjet);
			bgObjet.compile();
		    simpleU.addBranchGraph(bgObjet);
		}
	}
	
	public void dessinerSurface(List<Point> frontiere){
		BranchGroup bg = new BranchGroup();
		Shape3D surface = new Shape3D();
		
		float[] coords = new float[(frontiere.size()+1)*3];
        int i = 0;
        for (Point p : frontiere){
        	coords[i++]= (float)p.getY();
        	coords[i++]= (float)p.getZ();
        	coords[i++]= (float)p.getX();
        }
        Point p = frontiere.get(0);
        coords[i++]= (float)p.getY();
    	coords[i++]= (float)p.getZ();
    	coords[i++]= (float)p.getX();
		
        int[] stripCount = {frontiere.size()+1};  // ******
        
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
        gi.setCoordinates(coords);
        gi.setStripCounts(stripCount);
        gi.recomputeIndices();
        

        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);
        gi.recomputeIndices();

        Stripifier st = new Stripifier();
        st.stripify(gi);
        gi.recomputeIndices();

        //surface.setAppearance(CreateurVoiture.createMaterialAppearance());
        surface.setGeometry(gi.getGeometryArray());
		
		bg.addChild(surface);
		simpleU.addBranchGraph(bg);
	}

	public void setHeure(float heure) {
		float cos = (float)Math.cos((heure - 12.0f)/12.0*Math.PI);
		float coeff = (cos + 1)/2.0f;
		ciel.setColor(0.9f*coeff, 0.9f*coeff, 1.0f*coeff);
	}
	
	private static Vector3f toVector(Point p){
		return new Vector3f ((float)p.getY(),
				(float)p.getZ(),
				(float)p.getX());
	}
}
