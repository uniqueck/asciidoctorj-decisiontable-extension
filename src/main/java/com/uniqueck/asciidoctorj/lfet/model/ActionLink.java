package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class ActionLink extends AbstractLink<Action> implements IActionEntryLink {
	public ActionLink() {
		super();
	}

	@Override
	public boolean isOccurencesLink() {
		return false;
	}

	@Override
	public Action getAction() {
		return getLinkedModel();
	}
}
