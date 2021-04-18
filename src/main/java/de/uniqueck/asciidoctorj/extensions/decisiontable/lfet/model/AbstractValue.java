package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Attribute;

public abstract class AbstractValue {
	@Attribute(name = "value")
	private String value;

	public AbstractValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
