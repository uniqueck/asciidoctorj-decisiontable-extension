package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="SourceCode")
public class SourceCode extends AbstractValue {
	@Attribute(name = "codeLanguage")
	private String codeLanguage;

	@Attribute(name = "sourceCodeType")
	private String sourceCodeType;
	
	public SourceCode(
		@Attribute(name = "codeLanguage") String codeLanguage, 
		@Attribute(name = "sourceCodeType") String sourceCodeType, 
		@Attribute(name = "value") String value
	) {
		super(value);
		this.codeLanguage = codeLanguage;
		this.sourceCodeType = sourceCodeType;
	}
	
	public String getCodeLanguage() {
		return codeLanguage;
	}
	
	public String getSourceCodeType() {
		return sourceCodeType;
	}
}
