package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class ConditionOccurrenceLink extends AbstractLink<ConditionOccurrence> implements IConditionOccurrenceLink {
	public ConditionOccurrenceLink() {
		super();
	}

	@Override
	public boolean isOccurencesLink() {
		return true;
	}

	@Override
	public Condition getCondition() {
		return getLinkedModel().getCondition();
	}

	@Override
	public String getSymbol() {
		return getLinkedModel().getSymbol().getValue();
	}

	@Override
	public boolean isFirstOccurrence() {
		return getLinkedModel().isFirstOccurrence();
	}

	@Override
	public boolean isLastOccurrence() {
		return getLinkedModel().isLastOccurrence();
	}
}
