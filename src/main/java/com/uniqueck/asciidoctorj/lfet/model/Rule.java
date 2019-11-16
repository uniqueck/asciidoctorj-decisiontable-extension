package com.uniqueck.asciidoctorj.lfet.model;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Rule {
	@Attribute(name = "id")
	private String id;

	@Element(name = "Text", required = false)
	private Text text;
	
	@ElementList(entry = "ConditionLink", required = false, inline = true)
	private List<ConditionLink> conditionLinks;

	@ElementList(entry = "ActionLink", required = false, inline = true)
	private List<ActionLink> actionLinks;

	@ElementList(entry = "ConditionOccurrenceLink", required = false, inline = true)
	private List<ConditionOccurrenceLink> conditionOccurrenceLinks;
	
	@ElementList(entry = "ActionOccurrenceLink", required = false, inline = true)
	private List<ActionOccurrenceLink> actionOccurrenceLinks;

	public Rule(
			@Attribute(name = "id") 
			String id, 
			
			@Element(name = "Text", required = false)
			Text text,
			
			@ElementList(entry = "ConditionLink", required = false, inline = true)
			List<ConditionLink> conditionLinks,

			@ElementList(entry = "ActionLink", required = false, inline = true)
			List<ActionLink> actionLinks,

			@ElementList(entry = "ConditionOccurrenceLink", required = false, inline = true)
			List<ConditionOccurrenceLink> conditionOccurrenceLinks,
			
			@ElementList(entry = "ActionOccurrenceLink", required = false, inline = true)
			List<ActionOccurrenceLink> actionOccurrenceLinks
		) {
		this.id = id; 
		this.text = text;
		this.actionOccurrenceLinks = actionOccurrenceLinks;
		this.actionLinks = actionLinks;
		this.conditionOccurrenceLinks = conditionOccurrenceLinks;
		this.conditionLinks = conditionLinks;
	}

	public String getId() {
		return id;
	}
	
	public Text getText() {
		return text;
	}
	
	public List<ConditionLink> getConditionLinks() {
		return conditionLinks;
	}
	
	public List<ConditionOccurrenceLink> getConditionOccurrenceLinks() {
		return conditionOccurrenceLinks;
	}
	
	public List<ActionLink> getActionLinks() {
		return actionLinks;
	}
	
	public List<ActionOccurrenceLink> getActionOccurrenceLinks() {
		return actionOccurrenceLinks;
	}
}
