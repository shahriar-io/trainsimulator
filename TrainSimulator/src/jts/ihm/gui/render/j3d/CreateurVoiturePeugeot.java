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

public class CreateurVoiturePeugeot {

	private static float[] creerCoordonnees() {
        float[] data = new float[69*3];         // ******
        int i = 0;

        data[i++]= -1.818f; data[i++]= 0.290f; data[i++]= 0.794f; //0
        data[i++]= -1.693f; data[i++]= 0.277f; data[i++]= 0.794f; //1
        data[i++]= -1.565f; data[i++]= 0.556f; data[i++]= 0.794f; //2
        data[i++]= -1.169f; data[i++]= 0.554f; data[i++]= 0.794f; //3
        data[i++]= -0.998f; data[i++]= 0.234f; data[i++]= 0.794f; //4
        data[i++]=  0.809f; data[i++]= 0.202f; data[i++]= 0.794f; //5
        data[i++]=  0.943f; data[i++]= 0.584f; data[i++]= 0.794f; //6
        data[i++]=  1.409f; data[i++]= 0.582f; data[i++]= 0.794f; //7
        data[i++]=  1.517f; data[i++]= 0.223f; data[i++]= 0.794f; //8
        data[i++]=  1.833f; data[i++]= 0.213f; data[i++]= 0.794f; //9
        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]= 0.794f; //10
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]= 0.794f; //11
        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]= 0.794f; //12
        data[i++]= 	0.866f; data[i++]=  0.951f; data[i++]= 0.794f; //13
        data[i++]= 	0.191f; data[i++]=  1.378f; data[i++]= 0.794f; //14
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]= 0.794f; //15
        data[i++]= -1.818f; data[i++]=  0.290f; data[i++]= 0.794f; //16
        System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]= -1.818f; data[i++]=  0.290f; data[i++]=-0.794f; // 0 17
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]=-0.794f; // 1 18
        data[i++]= 	0.191f; data[i++]=  1.378f; data[i++]=-0.794f; // 2 19
        data[i++]= 	0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 3 20
        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]=-0.794f; // 4 21
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 5 22
        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]=-0.794f; // 6 23
        data[i++]=  1.833f; data[i++]= 0.213f; data[i++]=-0.794f; // 7 24
        data[i++]=  1.517f; data[i++]= 0.223f; data[i++]=-0.794f; // 8 25
        data[i++]=  1.409f; data[i++]= 0.582f; data[i++]=-0.794f; // 9 26
        data[i++]=  0.943f; data[i++]= 0.584f; data[i++]=-0.794f; //10 27
        data[i++]=  0.809f; data[i++]= 0.202f; data[i++]=-0.794f; //11 28
        data[i++]= -0.998f; data[i++]= 0.234f; data[i++]=-0.794f; //12 29
        data[i++]= -1.169f; data[i++]= 0.554f; data[i++]=-0.794f; //13 30
        data[i++]= -1.565f; data[i++]= 0.556f; data[i++]=-0.794f; //14 31
        data[i++]= -1.693f; data[i++]= 0.277f; data[i++]=-0.794f; //15 32
        data[i++]= -1.818f; data[i++]= 0.290f; data[i++]=-0.794f; //16 33
        System.out.println("end polygon; total vertex count: "+i/3);
        
        data[i++]=  1.833f; data[i++]= 0.213f; data[i++]=-0.794f; // 0 34
        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]=-0.794f; // 1 35
        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]= 0.794f; // 2 36
        data[i++]=  1.833f; data[i++]= 0.213f; data[i++]= 0.794f; // 3 37
        data[i++]=  1.833f; data[i++]= 0.213f; data[i++]=-0.794f; // 4 38
        System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]=-0.794f; // 0 39
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 1 40
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]= 0.794f; // 2 41
        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]= 0.794f; // 3 42
        data[i++]=  1.840f; data[i++]= 0.708f; data[i++]=-0.794f; // 4 43
        System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 0 44
        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]=-0.794f; // 1 45
        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]= 0.794f; // 2 46
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]= 0.794f; // 3 47
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 4 48
        System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]=-0.794f; // 0 49
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 1 50
        data[i++]=  0.866f; data[i++]=  0.951f; data[i++]= 0.794f; // 2 51
        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]= 0.794f; // 3 52
        data[i++]=  1.840f; data[i++]=  0.708f; data[i++]=-0.794f; // 4 53
        System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]= 0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 0 54
        data[i++]= 0.191f; data[i++]=  1.378f; data[i++]=-0.794f; // 1 55
        data[i++]= 0.191f; data[i++]=  1.378f; data[i++]= 0.794f; // 2 56
        data[i++]= 0.866f; data[i++]=  0.951f; data[i++]= 0.794f; // 3 57
        data[i++]= 0.866f; data[i++]=  0.951f; data[i++]=-0.794f; // 4 58
        System.out.println("end polygon; total vertex count: "+i/3);

        data[i++]= 0.191f; data[i++]=  1.378f; data[i++]=-0.794f; // 0 59
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]=-0.794f; // 1 60
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]= 0.794f; // 2 61
        data[i++]= 0.191f; data[i++]=  1.378f; data[i++]= 0.794f; // 3 62
        data[i++]= 0.191f; data[i++]=  1.378f; data[i++]=-0.794f; // 4 63
        System.out.println("end polygon; total vertex count: "+i/3);
                                                                    
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]=-0.794f; // 0 64
        data[i++]= -1.818f; data[i++]= 0.290f; data[i++]=-0.794f; // 1 65
        data[i++]= -1.818f; data[i++]= 0.290f; data[i++]= 0.794f; // 2 66
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]= 0.794f; // 3 67
        data[i++]= -1.5f; data[i++]=  1.365f; data[i++]=-0.794f; // 4 68
        System.out.println("end polygon; total vertex count: "+i/3);
        
        return data;
    }
	
	private static Appearance createMaterialAppearance(boolean bleu){

        Appearance materialAppear = new Appearance();
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        materialAppear.setPolygonAttributes(polyAttrib);

        if(bleu){
        	Material material = new Material();
            material.setDiffuseColor(new Color3f(1.0f, 0.0f, 0.0f));
            materialAppear.setMaterial(material);
        } else {
        	Material material = new Material();
            material.setDiffuseColor(new Color3f(0.0f, 0.0f, 1.0f));
            materialAppear.setMaterial(material);
        }
        
        
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(0.3f, 0.3f, 0.3f);
        materialAppear.setColoringAttributes(ca);

        return materialAppear;
    }
	
	public static Shape3D creerForme(boolean bleu){
		float[] coordinateData = null;
        coordinateData = creerCoordonnees();
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
        part.setAppearance(createMaterialAppearance(bleu));
        part.setGeometry(gi.getGeometryArray());
        
        return part;
	}
}
