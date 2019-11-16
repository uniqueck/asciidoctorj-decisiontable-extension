package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AbstractLinkTest extends AbstractLfdtTest<AbstractLink<String>>{
	private AbstractLink<String> createUnderTest() {
		return new AbstractLink<String>() {};
	}

	private String createExpectedXml() {
		return "< link='modelId'/>".replaceAll("'", "\"");
	}

	@Test
	public void testActionLink() throws Exception {
		AbstractLink<String> link = createUnderTest();
		assertNull(link.getLinkedModel());
		assertNull(link.getLink());
	}

	@Test
	public void testPersistAbstractLink() throws Exception {
		AbstractLink<String> link = createUnderTest();
		link.setLinkedModel("model");
		link.setLink("modelId");
		
		String xml = persist(link);
		assertEquals(createExpectedXml(), xml);
	}
}
