package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ActionOccurrenceLinkTest extends AbstractLfdtTest<ActionOccurrenceLink> {
	private ActionOccurrenceLink createUnderTest() {
		return new ActionOccurrenceLink();
	}

	private String createExpectedXml() {
		return "<actionOccurrenceLink link='actionOccId'/>".replaceAll("'", "\"");
	}

	@Test
	void testActionOccurrenceLink() throws Exception {
		ActionOccurrenceLink actionOccurrenceLink = createUnderTest();
		assertNull(actionOccurrenceLink.getLinkedModel());
		assertNull(actionOccurrenceLink.getLink());
	}

	@Test
	void testPersistActionOccurrenceLink() throws Exception {
		ActionOccurrence actionOccurrence = new ActionOccurrence("actionOccId", new Symbol("English", "symbol"), Arrays.asList(new Title("English", "title")), new Text("English", "text"), null, null);

		ActionOccurrenceLink actionOccurrenceLink = createUnderTest();
		actionOccurrenceLink.setLinkedModel(actionOccurrence);
		actionOccurrenceLink.setLink(actionOccurrence.getUId());
		
		String xml = persist(actionOccurrenceLink);
		assertEquals(createExpectedXml(), xml);
	}
}
