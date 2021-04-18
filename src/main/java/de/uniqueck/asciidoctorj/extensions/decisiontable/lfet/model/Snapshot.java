package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="Snapshot")
public class Snapshot {
	@Attribute(name = "crDat")
	private String crDat;
	
	@Attribute(name = "rSeq")
	private String rSeq; 
	
	@Attribute(name = "scm", required = false)
	private String scm; 
	
	@Attribute(name = "cars", required = false)
	private String cars; 
	
	@Attribute(name = "focR", required = false)
	private String focR;
	
	@Attribute(name = "focCA", required = false)
	private String focCA;
	
	public Snapshot(
			@Attribute(name = "crDat")
			String crDat,
			
			@Attribute(name = "rSeq")
			String rSeq,
			
			@Attribute(name = "scm", required = false)
			String scm,
			
			@Attribute(name = "cars", required = false)
			String cars, 
			
			@Attribute(name = "focR", required = false)
			String focR,
			
			@Attribute(name = "focCA", required = false)
			String focCA
		) {
		super();
		this.crDat = crDat;
		this.rSeq = rSeq;
		this.scm = scm;
		this.cars = cars;
		this.focR = focR;
		this.focCA = focCA;
	}
	
	public String getCrDat() {
		return crDat;
	}
	
	public String getRSeq() {
		return rSeq;
	}
	
	public String getScm() {
		return scm;
	}
	
	public String getCars() {
		return cars;
	}
	
	public String getFocR() {
		return focR;
	}
	
	public String getFocCA() {
		return focCA;
	}
}
