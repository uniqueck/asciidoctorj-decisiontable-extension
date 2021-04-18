package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="Title")
public class Title extends AbstractValueBasedOnLanguage {
	public Title(
		@Attribute(name = "language") String language, 
		@Attribute(name = "value") String value
	) {
		super(language, value);
	}
}
