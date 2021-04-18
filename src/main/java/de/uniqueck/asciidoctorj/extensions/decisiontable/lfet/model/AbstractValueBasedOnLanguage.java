package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Attribute;

public abstract class AbstractValueBasedOnLanguage extends AbstractValue {
	@Attribute(name = "language")
	private String language;

	protected AbstractValueBasedOnLanguage(String language, String value) {
		super(value);
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
}

