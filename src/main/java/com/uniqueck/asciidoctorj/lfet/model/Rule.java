package com.uniqueck.asciidoctorj.lfet.model;

import java.util.List;

import org.simpleframework.xml.*;

@Root(strict = false)
public class Rule {
	@Attribute(name = "id")
	private String id;

	@Element(name = "Text", required = false)
	private Text text;
	
	@ElementListUnion(
			{
					@ElementList(entry = "ConditionLink", inline = true, required = false, type = ConditionLink.class),
					@ElementList(entry = "ConditionOccurrenceLink", inline = true, required = false, type = ConditionOccurrenceLink.class)
			}
	)
	private List<AbstractLink<?>> conditionLinks;

	@ElementListUnion(
			{
					@ElementList(entry = "ActionLink", inline = true, required = false, type = ActionLink.class),
					@ElementList(entry = "ActionOccurrenceLink", inline = true, required = false, type = ActionOccurrenceLink.class)
			}
	)
	private List<AbstractLink<?>> actionLinks;


	public Rule(
			@Attribute(name = "id") 
			String id, 
			
			@Element(name = "Text", required = false)
			Text text,

			@ElementListUnion(
					{
							@ElementList(entry = "ConditionLink", inline = true, required = false, type = ConditionLink.class),
							@ElementList(entry = "ConditionOccurrenceLink", inline = true, required = false, type = ConditionOccurrenceLink.class)
					}
			)
			List<AbstractLink<?>> conditionLinks,

			@ElementListUnion(
					{
							@ElementList(entry = "ActionLink", inline = true, required = false, type = ActionLink.class),
							@ElementList(entry = "ActionOccurrenceLink", inline = true, required = false, type = ActionOccurrenceLink.class)
					}
			)
			List<AbstractLink<?>> actionLinks


		) {
		this.id = id; 
		this.text = text;
		this.actionLinks = actionLinks;
		this.conditionLinks = conditionLinks;
	}

	public String getId() {
		return id;
	}
	
	public Text getText() {
		return text;
	}
	
	public List<AbstractLink<?>> getConditionLinks() {
		return conditionLinks;
	}
	
	public List<AbstractLink<?>> getActionLinks() {
		return actionLinks;
	}
	
}
