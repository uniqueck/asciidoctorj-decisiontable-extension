package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class ActionOccurrenceLink extends AbstractLink<ActionOccurrence> {
	public ActionOccurrenceLink() {
		super();
	}

	@Override
	public boolean isOccurencesLink() {
		return true;
	}
}
