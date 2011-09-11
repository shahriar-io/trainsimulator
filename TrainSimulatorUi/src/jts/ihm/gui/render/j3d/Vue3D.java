package jts.ihm.gui.render.j3d;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import jts.moteur.geometrie.Point;

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


public class Vue3D /*extends Applet */implements InterfaceJ3D {
	
	private static String REPERTOIRE_OBJETS = "data/objets/";

	private SimpleUniverse simpleU;
	private BranchGroup parent;
	//private BranchGroup voiture1;
	private TransformGroup voiture1tg;
	//private BranchGroup voiture2;
	private TransformGroup voiture2tg;
	
	private DirectionalLight soleil;
	private Background ciel;
	
	public Vue3D(Canvas3D c3d){
		//setLayout(new BorderLayout());
		//GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		//Canvas3D canvas3D = new Canvas3D(config);
	    //this.add(canvas3D, BorderLayout.CENTER);
	    
	    //if (c3d != null){
	    	simpleU = new SimpleUniverse(c3d);
	    /*} else {
	    	simpleU = new SimpleUniverse(canvas3D);
	    }*/
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    createSceneGraph();
	    parent.compile();
	    simpleU.addBranchGraph(parent);
	    
	    View view = simpleU.getViewer().getView();
	    view.setBackClipDistance(100);
	    view.setFieldOfView(0.6*Math.PI);

	}
	
	private void createSceneGraph() {
	    parent = new BranchGroup();
	    
	    ciel = new Background(new Color3f(0.9f, 0.9f, 1.0f));
	    ciel.setCapability(Background.ALLOW_COLOR_WRITE);
	    ciel.setApplicationBounds(new BoundingSphere(new Point3d(0, 0, 0), 80));
	    parent.addChild(ciel);
	    
	    /*parent.addChild(new ColorCube(0.4));*/
	    
	    /*TransformGroup tg = new TransformGroup();
	    Transform3D translation;
	    translation = new Transform3D();
	    translation.setTranslation(new Vector3f (0, 0, 20));
	    tg.setTransform(translation);
	    Sphere sphere = new Sphere(1.2f);
	    sphere.getAppearance().setColoringAttributes(new ColoringAttributes(0.1f, 0.8f, 0.8f, ColoringAttributes.NICEST));
	    tg.addChild(sphere);
	    parent.addChild(tg);*/
	    
	    /*TransformGroup tg2 = new TransformGroup();
	    Transform3D translation2;
	    translation2 = new Transform3D();
	    translation2.setTranslation(new Vector3f (0, 0, -20));
	    tg2.setTransform(translation2);
	    Box box = new Box();
	    tg2.addChild(box);
	    parent.addChild(tg2);*/
	    
	    
	    AmbientLight light = new AmbientLight(new Color3f(0.5f, 0.5f, 0.5f));
	    //light.setColor(new Color3f(0.5f, 0.5f, 0.5f));
	    light.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 80));
	    parent.addChild(light);
	    
	    soleil = new DirectionalLight();
	    soleil.setDirection(new Vector3f(0.1f, 0.1f, -0.5f));
	    //light.setColor(new Color3f(0.5f, 0.5f, 0.5f));
	    soleil.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 80));
	    parent.addChild(soleil);
	  }
	
	public void deplacerCamera(float x, float y, float z, float theta, float phi){
		//Log.getInstance().logInfo(Float.toString(theta));
		Transform3D mouvement = new Transform3D();
		Transform3D rotation = new Transform3D();
	    mouvement.setTranslation(new Vector3f (x, y, z));
	    rotation.rotY(theta);
	    mouvement.mul(rotation);
	    simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(mouvement);
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
	
	public void chargerObjet(float x, float y, float z, String nomObjet){
		String nomComplet = REPERTOIRE_OBJETS + nomObjet;
	    Loader loader3ds = new Loader3DS();
	    ObjectFile of = new ObjectFile();
	    Scene sceneLoaded = null;
	    
		try {
			if(nomObjet.endsWith(".3DS")){
				sceneLoaded = loader3ds.load(nomComplet);
			} else if(nomObjet.endsWith(".obj")) {
				sceneLoaded = of.load(nomComplet);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IncorrectFormatException e) {
			e.printStackTrace();
		} catch (ParsingErrorException e) {
			e.printStackTrace();
		}
		
		if(sceneLoaded != null){
			BranchGroup scene = sceneLoaded.getSceneGroup();
			Transform3D translation = new Transform3D();
			translation.setTranslation(new Vector3f (x, y, z));
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

        surface.setAppearance(CreateurVoiture.createMaterialAppearance());
        surface.setGeometry(gi.getGeometryArray());
		
		bg.addChild(surface);
		simpleU.addBranchGraph(bg);
		//System.out.println(frontiere.size() +" pts traces");
	}

	public void setHeure(float heure) {
		float cos = (float)Math.cos((heure - 12.0f)/12.0*Math.PI);
		float coeff = (cos + 1)/2.0f;
		ciel.setColor(0.9f*coeff, 0.9f*coeff, 1.0f*coeff);
	}
}
