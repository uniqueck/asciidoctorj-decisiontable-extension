package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TitleTest extends AbstractLfdtTest<Title> {
	private Title createUnderTest() {
		return createUnderTest("English", "myTitle");
	}
	
	private Title createUnderTest(String language, String value) {
		return new Title(language, value);
	}

	private String createExpectedXml() {
		return "<Title value='myTitle' language='English'/>".replaceAll("'", "\"");
	}

	@Test
	void testTitle() throws Exception {
		Title title = createUnderTest("English", null);
		assertTitle(Arrays.asList(title), null, "English");
		
		title = createUnderTest();
		assertTitle(Arrays.asList(title), "myTitle", "English");
	}
	
	@Test
	void testPersistTitle_LanguageAndValueGiven_noError() throws Exception {
		Title title = createUnderTest();
		String xml = persist(title);
		assertEquals(createExpectedXml(), xml);
	}

	@Test
	void testPersistTitle_ValueNotGiven_error() throws Exception {
		Title title = createUnderTest("English", null);
		
		try {
			persist(title);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("value is null"));
		}
	}
	
	@Test
	void testPersistTitle_LanguageNotGiven_error() throws Exception {
		Title title = createUnderTest(null, "myTitle");
		
		try {
			persist(title);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("language is null"));
		}
	}
	
	@Test
	void testConvertTitleXML() throws Exception {
		Title title = convertToModel(createExpectedXml());
		assertTitle(Arrays.asList(title), "myTitle", "English");
	}
}
