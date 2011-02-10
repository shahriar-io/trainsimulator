package jts.ihm.gui.render.j3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;

public class CreateurVoiture {

	static float[] createCoordinateData() {
        float[] data = new float[69*3];         // ******
        int i = 0;

        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]= 0.3f; //0
        data[i++]= -0.9f; data[i++]= -0.3f; data[i++]= 0.3f; //1
        data[i++]= -0.8f; data[i++]= -0.1f; data[i++]= 0.3f; //2
        data[i++]= -0.6f; data[i++]= -0.1f; data[i++]= 0.3f; //3
        data[i++]= -0.5f; data[i++]= -0.3f; data[i++]= 0.3f; //4
        data[i++]=  0.2f; data[i++]= -0.3f; data[i++]= 0.3f; //5
        data[i++]=  0.3f; data[i++]= -0.1f; data[i++]= 0.3f; //6
        data[i++]=  0.5f; data[i++]= -0.1f; data[i++]= 0.3f; //7
        data[i++]=  0.6f; data[i++]= -0.3f; data[i++]= 0.3f; //8
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]= 0.3f; //9
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]= 0.3f; //10
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]= 0.3f; //11
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]= 0.3f; //12
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]= 0.3f; //13
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]= 0.3f; //14
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]= 0.3f; //15
        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]= 0.3f; //16
        //System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 0 17
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]=-0.3f; // 1 18
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]=-0.3f; // 2 19
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]=-0.3f; // 3 20
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]=-0.3f; // 4 21
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]=-0.3f; // 5 22
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]=-0.3f; // 6 23
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 7 24
        data[i++]=  0.6f; data[i++]= -0.3f; data[i++]=-0.3f; // 8 25
        data[i++]=  0.5f; data[i++]= -0.1f; data[i++]=-0.3f; // 9 26
        data[i++]=  0.3f; data[i++]= -0.1f; data[i++]=-0.3f; //10 27
        data[i++]=  0.2f; data[i++]= -0.3f; data[i++]=-0.3f; //11 28
        data[i++]= -0.5f; data[i++]= -0.3f; data[i++]=-0.3f; //12 29
        data[i++]= -0.6f; data[i++]= -0.1f; data[i++]=-0.3f; //13 30
        data[i++]= -0.8f; data[i++]= -0.1f; data[i++]=-0.3f; //14 31
        data[i++]= -0.9f; data[i++]= -0.3f; data[i++]=-0.3f; //15 32
        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]=-0.3f; //16 33
        //System.out.println("end polygon; total vertex count: "+i/3);
                                                                  
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 0 34
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]=-0.3f; // 1 35
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]= 0.3f; // 2 36
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]= 0.3f; // 3 37
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 4 38
        //System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]=-0.3f; // 0 39
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]=-0.3f; // 1 40
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]= 0.3f; // 2 41
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]= 0.3f; // 3 42
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]=-0.3f; // 4 43
        //System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]=-0.3f; // 0 44
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]=-0.3f; // 1 45
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]= 0.3f; // 2 46
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]= 0.3f; // 3 47
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]=-0.3f; // 4 48
        //System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]=-0.3f; // 0 49
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]=-0.3f; // 1 50
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]= 0.3f; // 2 51
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]= 0.3f; // 3 52
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]=-0.3f; // 4 53
        //System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]=-0.3f; // 0 54
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]=-0.3f; // 1 55
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]= 0.3f; // 2 56
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]= 0.3f; // 3 57
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]=-0.3f; // 4 58
        //System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]=-0.3f; // 0 59
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]=-0.3f; // 1 60
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]= 0.3f; // 2 61
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]= 0.3f; // 3 62
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]=-0.3f; // 4 63
        //System.out.println("end polygon; total vertex count: "+i/3);
                                                                    
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]=-0.3f; // 0 64
        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 1 65
        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]= 0.3f; // 2 66
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]= 0.3f; // 3 67
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]=-0.3f; // 4 68
        //System.out.println("end polygon; total vertex count: "+i/3);

// ****** This is the data for the hood, roof, trunk, front and rear glass
// ****** remove the comments markers below (slash-star) and (star slash)
// ****** and add the appropriate comment markers above.
// ****** modification of other lines of code is necessary to use this data
// ****** one line above and two lines below
/*
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 0  35 
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]=-0.3f; // 1  36
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]=-0.3f; // 2  37
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]=-0.3f; // 3  38
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]=-0.3f; // 4  39
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]=-0.3f; // 5  40
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]=-0.3f; // 6  41
        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 7  42
        data[i++]= -1.3f; data[i++]= -0.3f; data[i++]= 0.3f; // 8  43
        data[i++]= -1.3f; data[i++]=  0.0f; data[i++]= 0.3f; // 9  44
        data[i++]= -1.1f; data[i++]=  0.0f; data[i++]= 0.3f; // 10 45
        data[i++]= -0.5f; data[i++]=  0.3f; data[i++]= 0.3f; // 11 46
        data[i++]=  0.1f; data[i++]=  0.3f; data[i++]= 0.3f; // 12 47
        data[i++]=  0.5f; data[i++]=  0.0f; data[i++]= 0.3f; // 13 48
        data[i++]=  1.2f; data[i++]= -0.1f; data[i++]= 0.3f; // 14 49
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]= 0.3f; // 15 50
        data[i++]=  1.3f; data[i++]= -0.3f; data[i++]=-0.3f; // 16 51
        System.out.println("end polygon; total vertex count: "+i/3);
*/
// ****** end of the alternative polygon data
        return data;
    }
	
	public static Appearance createMaterialAppearance(){

        Appearance materialAppear = new Appearance();
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        materialAppear.setPolygonAttributes(polyAttrib);

        Material material = new Material();
        material.setDiffuseColor(new Color3f(0.2f, 0.2f, 0.2f));
        materialAppear.setMaterial(material);
        
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(0.5f, 0.5f, 0.5f);
        materialAppear.setColoringAttributes(ca);

        return materialAppear;
    }
	
	public static Appearance createWireFrameAppearance(){

        Appearance materialAppear = new Appearance();
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        materialAppear.setPolygonAttributes(polyAttrib);
        ColoringAttributes redColoring = new ColoringAttributes();
        redColoring.setColor(1.0f, 0.0f, 0.0f);
        materialAppear.setColoringAttributes(redColoring);

        return materialAppear;
    }
	
	public static Shape3D creerForme(){
		float[] coordinateData = null;
        coordinateData = createCoordinateData();
        int[] stripCount = {17,17,5,5,5,5,5,5,5};  // ******
        
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
        gi.setCoordinates(coordinateData);
        gi.setStripCounts(stripCount);
        gi.recomputeIndices();

        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);
        gi.recomputeIndices();

        Stripifier st = new Stripifier();
        st.stripify(gi);
        gi.recomputeIndices();

        Shape3D part = new Shape3D();
        /*if(wireFrame==true)
                part.setAppearance(createWireFrameAppearance());
        else
                part.setAppearance(createMaterialAppearance());*/
        part.setAppearance(createMaterialAppearance());
        part.setGeometry(gi.getGeometryArray());
        
        return part;
	}
}
