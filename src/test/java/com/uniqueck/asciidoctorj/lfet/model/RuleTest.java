package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class RuleTest extends AbstractLfdtTest<Rule>{
	private Rule createUnderTest(String id, Text text, List<IConditionEntryLink> conditionLinks, List<IActionEntryLink> actionLinks) {
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
		List<IConditionEntryLink> conditionLinks = new ArrayList<>();
		List<IActionEntryLink> actionLinks = new ArrayList<>();

		Rule rule = createUnderTest(id, text, conditionLinks, actionLinks);

		assertEquals(id, rule.getId());
		assertSame(text, rule.getText());
		assertSame(actionLinks, rule.getActionLinks());
		assertSame(conditionLinks, rule.getConditionLinks());
	}

	@Test
	public void testPersistRule() throws Exception {
		Condition condition = new Condition("condId", Arrays.asList(new Title("English", "title")), new Text("English", "text"), null, null, null);
		ConditionOccurrence conditionOccurrence = new ConditionOccurrence("condOccId", new Symbol("English", "symbol"),
				Arrays.asList(new Title("English", "title")), new Text("English", "text"), null, null);
		Action action = new Action("actionId", Arrays.asList(new Title("English", "title")), new Text("English", "text"), null, null, null);
		ActionOccurrence actionOccurrence = new ActionOccurrence("actionOccId", new Symbol("English", "symbol"),
				Arrays.asList(new Title("English", "title")), new Text("English", "text"), null, null);
		
		String id = "10";
		
		Text text = new Text("English", "docuText");

		List<IConditionEntryLink> conditionLinks = new ArrayList<>();
		ConditionLink conditionLink = new ConditionLink();
		conditionLink.setLinkedModel(condition);
		conditionLink.setLink(condition.getUId());
		conditionLinks.add(conditionLink);

		ConditionOccurrenceLink conditionOccurrenceLink = new ConditionOccurrenceLink();
		conditionOccurrenceLink.setLinkedModel(conditionOccurrence);
		conditionOccurrenceLink.setLink(conditionOccurrence.getUId());


		conditionLinks.add(conditionOccurrenceLink);

		List<IActionEntryLink> actionLinks = new ArrayList<>();
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
