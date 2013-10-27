package jts.util.obj;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	
	private String name;
	private String mtlName;
	private List<Surface> surfaces;

	public Groupe(){
		this.surfaces = new ArrayList<Surface>();
	}

	public Groupe(String name, String mtlName) {
		this();
		this.name = name;
		this.mtlName = mtlName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMtlName() {
		return mtlName;
	}

	public void setMtlName(String mtlName) {
		this.mtlName = mtlName;
	}

	public List<Surface> getSurfaces() {
		return surfaces;
	}

	public void addSurface(Surface surface) {
		this.surfaces.add(surface);
	}
}
