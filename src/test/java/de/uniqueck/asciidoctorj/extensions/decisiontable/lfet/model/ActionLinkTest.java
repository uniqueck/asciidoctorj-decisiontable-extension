package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ActionLinkTest extends AbstractLfdtTest<ActionLink> {
	private ActionLink createUnderTest() {
		return new ActionLink();
	}

	private String createExpectedXml() {
		return "<actionLink link='actionId'/>".replaceAll("'", "\"");
	}

	@Test
	public void testActionLink() throws Exception {
		ActionLink actionLink = createUnderTest();
		assertNull(actionLink.getLinkedModel());
		assertNull(actionLink.getLink());
	}

	@Test
	public void testPersistActionLink() throws Exception {
		Action action = new Action("actionId", Arrays.asList(new Title("English", "title")), new Text("English", "text"), null, null, null);

		ActionLink actionLink = createUnderTest();
		actionLink.setLinkedModel(action);
		actionLink.setLink(action.getUId());
		
		String xml = persist(actionLink);
		assertEquals(createExpectedXml(), xml);
	}
}
