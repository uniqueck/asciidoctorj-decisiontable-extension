package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class RuleTest extends AbstractLfdtTest<Rule>{
	private Rule createUnderTest(String id, Text text, List<AbstractLink<?>> conditionLinks, List<AbstractLink<?>> actionLinks) {
		return new Rule(id, text, conditionLinks, actionLinks);
	}

	private String createExpectedXml() {
		String xml = "<rule id='10'>" + NEW_LINE
				   + "   <Text value='docuText' language='English'/>" + NEW_LINE
				   + "   <ConditionLink link='condId' conditionState='false'/>" + NEW_LINE
				   + "   <ConditionOccurrenceLink link='condOccId'/>" + NEW_LINE
				   + "   <ActionLink link='actionId'/>" + NEW_LINE
				   + "   <ActionOccurrenceLink link='actionOccId'/>" + NEW_LINE
				   + "</rule>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}
		
	@Test
	public void testRule() throws Exception {
		String id = "10";
		Text text = new Text("English", "docuText");
		List<AbstractLink<?>> conditionLinks = new ArrayList<AbstractLink<?>>();
		List<AbstractLink<?>> actionLinks = new ArrayList<AbstractLink<?>>();

		Rule rule = createUnderTest(id, text, conditionLinks, actionLinks);

		assertEquals(id, rule.getId());
		assertSame(text, rule.getText());
		assertSame(actionLinks, rule.getActionLinks());
		assertSame(conditionLinks, rule.getConditionLinks());
	}

	@Test
	public void testPersistRule() throws Exception {
		Condition condition = new Condition("condId", new Title("English", "title"), new Text("English", "text"), null, null, null);
		ConditionOccurrence conditionOccurrence = new ConditionOccurrence("condOccId", new Symbol("English", "symbol"),
				new Title("English", "title"), new Text("English", "text"), null, null);
		Action action = new Action("actionId", new Title("English", "title"), new Text("English", "text"), null, null, null);
		ActionOccurrence actionOccurrence = new ActionOccurrence("actionOccId", new Symbol("English", "symbol"),
				new Title("English", "title"), new Text("English", "text"), null, null);
		
		String id = "10";
		
		Text text = new Text("English", "docuText");

		List<AbstractLink<?>> conditionLinks = new ArrayList<AbstractLink<?>>();
		ConditionLink conditionLink = new ConditionLink();
		conditionLink.setLinkedModel(condition);
		conditionLink.setLink(condition.getUId());
		conditionLinks.add(conditionLink);

		ConditionOccurrenceLink conditionOccurrenceLink = new ConditionOccurrenceLink();
		conditionOccurrenceLink.setLinkedModel(conditionOccurrence);
		conditionOccurrenceLink.setLink(conditionOccurrence.getUId());


		conditionLinks.add(conditionOccurrenceLink);

		List<AbstractLink<?>> actionLinks = new ArrayList<AbstractLink<?>>();
		ActionLink actionLink = new ActionLink();
		actionLink.setLinkedModel(action);
		actionLink.setLink(action.getUId());
		actionLinks.add(actionLink);

		ActionOccurrenceLink actionOccurrenceLink = new ActionOccurrenceLink();
		actionOccurrenceLink.setLinkedModel(actionOccurrence);
		actionOccurrenceLink.setLink(actionOccurrence.getUId());

		actionLinks.add(actionOccurrenceLink);
		
		Rule rule = createUnderTest(id, text, conditionLinks, actionLinks);

		String xml = persist(rule);
		assertEquals(createExpectedXml(), xml);
	}
}
