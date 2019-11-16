package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConditionOccurrenceLinkTest extends AbstractLfdtTest<ConditionOccurrenceLink> {
	private ConditionOccurrenceLink createUnderTest() {
		return new ConditionOccurrenceLink();
	}

	private String createExpectedXml() {
		return "<conditionOccurrenceLink link='condOccId'/>".replaceAll("'", "\"");
	}

	@Test
	public void testConditionOccurrenceLink() throws Exception {
		ConditionOccurrenceLink conditionOccurrenceLink = createUnderTest();
		assertNull(conditionOccurrenceLink.getLinkedModel());
		assertNull(conditionOccurrenceLink.getLink());
	}

	@Test
	public void testPersistConditionLink() throws Exception {
		ConditionOccurrence conditionOccurrence = new ConditionOccurrence("condOccId", new Symbol("English", "symbol"), new Title("English", "title"), new Text("English", "text"), null, null);

		ConditionOccurrenceLink conditionOccurrenceLink = createUnderTest();
		conditionOccurrenceLink.setLinkedModel(conditionOccurrence);
		conditionOccurrenceLink.setLink(conditionOccurrence.getUId());
		
		String xml = persist(conditionOccurrenceLink);
		assertEquals(createExpectedXml(), xml);
	}
}
