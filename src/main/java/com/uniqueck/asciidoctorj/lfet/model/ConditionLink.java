package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class ConditionLink extends AbstractLink<Condition> {	
	@Attribute(name = "conditionState")
	private boolean conditionState;

	public ConditionLink() {
		super();
		this.conditionState = false;
	}
		
	public boolean getConditionState() {
		return conditionState;
	}
}
