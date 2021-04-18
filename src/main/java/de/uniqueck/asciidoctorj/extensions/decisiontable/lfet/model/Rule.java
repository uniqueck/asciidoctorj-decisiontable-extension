package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import java.util.List;

import org.simpleframework.xml.*;

@Root(strict = false)
public class Rule {
	@Attribute(name = "id")
	private String id;

	@Element(name = "Text", required = false)
	private de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model.Text text;
	
	@ElementListUnion(
			{
					@ElementList(entry = "ConditionLink", inline = true, required = false, type = ConditionLink.class),
					@ElementList(entry = "ConditionOccurrenceLink", inline = true, required = false, type = ConditionOccurrenceLink.class)
			}
	)
	private List<IConditionEntryLink> conditionLinks;

	@ElementListUnion(
			{
					@ElementList(entry = "ActionLink", inline = true, required = false, type = ActionLink.class),
					@ElementList(entry = "ActionOccurrenceLink", inline = true, required = false, type = ActionOccurrenceLink.class)
			}
	)
	private List<IActionEntryLink> actionLinks;


	public Rule(
			@Attribute(name = "id") 
			String id, 
			
			@Element(name = "Text", required = false)
                    de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model.Text text,

			@ElementListUnion(
					{
							@ElementList(entry = "ConditionLink", inline = true, required = false, type = ConditionLink.class),
							@ElementList(entry = "ConditionOccurrenceLink", inline = true, required = false, type = ConditionOccurrenceLink.class)
					}
			)
			List<IConditionEntryLink> conditionLinks,

			@ElementListUnion(
					{
							@ElementList(entry = "ActionLink", inline = true, required = false, type = ActionLink.class),
							@ElementList(entry = "ActionOccurrenceLink", inline = true, required = false, type = ActionOccurrenceLink.class)
					}
			)
			List<IActionEntryLink> actionLinks


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

	@SuppressWarnings("squid:S1452")
	public List<IConditionEntryLink> getConditionLinks() {
		return conditionLinks;
	}

	@SuppressWarnings("squid:S1452")
	public List<IActionEntryLink> getActionLinks() {
		return actionLinks;
	}

	public IConditionEntryLink getConditionLinkBasedOnCondition(Condition condition) {
		return getConditionLinks().stream().filter(cl -> cl.getCondition().equals(condition)).findFirst().orElse(null);

	}
	
}
