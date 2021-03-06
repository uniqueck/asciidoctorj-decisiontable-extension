package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractValueBasedOnLanguageTest extends AbstractLfdtTest<AbstractValueBasedOnLanguage> {
	private AbstractValueBasedOnLanguage createUnderTest() {
		return createUnderTest("English", "myValue");
	}
	
	private AbstractValueBasedOnLanguage createUnderTest(String language, String value) {
		return new AbstractValueBasedOnLanguage(language, value) {};
	}

	private String createExpectedXml() {
		return "< value='myValue' language='English'/>".replaceAll("'", "\"");
	}

	@Test
	void testAbstractValueBasedOnLanguage() throws Exception {
		AbstractValueBasedOnLanguage value = createUnderTest("English", null);
		assertEquals("English", value.getLanguage());
		assertNull(value.getValue());
		
		value = createUnderTest();
		assertEquals("English", value.getLanguage());
		assertEquals("myValue", value.getValue());
	}
	
	@Test
	void testPersistAbstractValueBasedOnLanguage_LanguageAndValueGiven_noError() throws Exception {
		AbstractValueBasedOnLanguage value = createUnderTest();
		String xml = persist(value);
		assertEquals(createExpectedXml(), xml);
	}

	@Test
	void testPersistAbstractValueBasedOnLanguage_ValueNotGiven_error() throws Exception {
		AbstractValueBasedOnLanguage value = createUnderTest("English", null);
		
		try {
			persist(value);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("value is null"));
		}
	}
	
	@Test
	void testPersistAbstractValueBasedOnLanguage_LanguageNotGiven_error() throws Exception {
		AbstractValueBasedOnLanguage value = createUnderTest(null, "myValue");
		
		try {
			persist(value);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("language is null"));
		}
	}
}
