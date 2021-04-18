package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AbstractLinkTest extends AbstractLfdtTest<AbstractLink<String>>{
	private AbstractLink<String> createUnderTest() {
		return new AbstractLink<String>() {

			@Override
			public boolean isOccurencesLink() {
				return false;
			}
		};



	}

	private String createExpectedXml() {
		return "< link='modelId'/>".replaceAll("'", "\"");
	}

	@Test
	void testActionLink() throws Exception {
		AbstractLink<String> link = createUnderTest();
		assertNull(link.getLinkedModel());
		assertNull(link.getLink());
	}

	@Test
	void testPersistAbstractLink() throws Exception {
		AbstractLink<String> link = createUnderTest();
		link.setLinkedModel("model");
		link.setLink("modelId");
		
		String xml = persist(link);
		assertEquals(createExpectedXml(), xml);
	}
}
