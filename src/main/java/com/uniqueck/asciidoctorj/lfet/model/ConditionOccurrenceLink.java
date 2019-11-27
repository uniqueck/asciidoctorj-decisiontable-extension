package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class ConditionOccurrenceLink extends AbstractLink<ConditionOccurrence> {
	public ConditionOccurrenceLink() {
		super();
	}

	@Override
	public boolean isOccurencesLink() {
		return true;
	}
}
