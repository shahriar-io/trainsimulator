package jts.io.xml;

import java.io.BufferedWriter;
import java.io.IOException;

public class AttributXml {

	private String name;
	private String value;
	
	public AttributXml(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getValue() { return value; }

	public void setValue(String value) { this.value = value; }
	
	public void write(BufferedWriter writer) throws IOException {
		writer.write(" " + name + "=\"" + value + "\"");
	}
}
