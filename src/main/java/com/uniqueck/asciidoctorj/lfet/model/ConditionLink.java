package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class ConditionLink extends AbstractLink<Condition> implements IConditionEntryLink {
	@Attribute(name = "conditionState")
	private boolean conditionState;

	public ConditionLink() {
		super();
		this.conditionState = false;
	}
		
	public boolean getConditionState() {
		return conditionState;
	}

	@Override
	public boolean isOccurencesLink() {
		return false;
	}

	@Override
	public Condition getCondition() {
		return getLinkedModel();
	}



}
