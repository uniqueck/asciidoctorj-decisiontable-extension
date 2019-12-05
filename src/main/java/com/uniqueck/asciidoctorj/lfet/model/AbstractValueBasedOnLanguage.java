package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Attribute;

public abstract class AbstractValueBasedOnLanguage extends AbstractValue {
	@Attribute(name = "language")
	private String language;

	public AbstractValueBasedOnLanguage(String language, String value) {
		super(value);
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
}

