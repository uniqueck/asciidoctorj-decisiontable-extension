package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class ActionOccurrenceLink extends AbstractLink<ActionOccurrence> implements IActionOccurrenceLink {
	public ActionOccurrenceLink() {
		super();
	}

	@Override
	public boolean isOccurencesLink() {
		return true;
	}

	@Override
	public Action getAction() {
		return getLinkedModel().getAction();
	}

	@Override
	public String getSymbol() {
		return getLinkedModel().getSymbol().getValue();
	}
}
