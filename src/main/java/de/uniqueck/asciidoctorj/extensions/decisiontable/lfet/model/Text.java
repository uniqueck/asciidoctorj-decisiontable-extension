package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="Text")
public class Text extends AbstractValueBasedOnLanguage {
	public Text(
		@Attribute(name = "language") String language, 
		@Attribute(name = "value") String value
	) {
		super(language, value);
	}
}
