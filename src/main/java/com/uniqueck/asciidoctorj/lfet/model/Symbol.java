package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="Symbol")
public class Symbol extends AbstractValueBasedOnLanguage {
	public Symbol(
		@Attribute(name = "language") String language, 
		@Attribute(name = "value") String value
	) {
		super(language, value);
	}	
}

