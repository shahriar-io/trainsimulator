package jts.util.langues;

public class EsperantoAdapter implements LangueAdapter {
	
	public EsperantoAdapter(){
		
	}

	public String adapt(String str){
		String adaptedStr = str;
		adaptedStr = adaptedStr.replaceAll("Cx", "&#264;");
		adaptedStr = adaptedStr.replaceAll("cx", "&#265;");
		adaptedStr = adaptedStr.replaceAll("Gx", "&#284;");
		adaptedStr = adaptedStr.replaceAll("gx", "&#285;");
		adaptedStr = adaptedStr.replaceAll("Hx", "&#292;");
		adaptedStr = adaptedStr.replaceAll("hx", "&#293;");
		adaptedStr = adaptedStr.replaceAll("Jx", "&#308;");
		adaptedStr = adaptedStr.replaceAll("jx", "&#309;");
		adaptedStr = adaptedStr.replaceAll("Sx", "&#348;");
		adaptedStr = adaptedStr.replaceAll("sx", "&#349;");
		adaptedStr = adaptedStr.replaceAll("Ux", "&#364;");
		adaptedStr = adaptedStr.replaceAll("ux", "&#365;");
		return adaptedStr;
	}

}
