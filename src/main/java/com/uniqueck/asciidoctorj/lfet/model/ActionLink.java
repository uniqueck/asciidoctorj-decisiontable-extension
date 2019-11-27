package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class ActionLink extends AbstractLink<Action> {
	public ActionLink() {
		super();
	}

	@Override
	public boolean isOccurencesLink() {
		return false;
	}
}
