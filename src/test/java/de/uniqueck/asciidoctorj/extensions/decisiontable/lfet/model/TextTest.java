package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import static org.junit.jupiter.api.Assertions.*;

public class TextTest extends AbstractLfdtTest<Text> {
	private Text createUnderTest() {
		return createUnderTest("English", "this is a docu");
	}
	
	private Text createUnderTest(String language, String value) {
		return new Text(language, value);
	}

	private String createExpectedXml() {
		return "<Text value='this is a docu' language='English'/>".replaceAll("'", "\"");
	}

	@Test
	public void testText() throws Exception {
		Text text = createUnderTest("English", null);
		assertText(text, null, "English");
		
		text = createUnderTest();
		assertText(text, "this is a docu", "English");
	}
	
	@Test
	public void testPersistText_LanguageAndValueGiven_noError() throws Exception {
		Text text = createUnderTest();
		String xml = persist(text);
		assertEquals(createExpectedXml(), xml);
	}

	@Test
	public void testPersistText_ValueNotGiven_error() throws Exception {
		Text text = createUnderTest("English", null);
		
		try {
			persist(text);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("value is null"));
		}
	}
	
	@Test
	public void testPersistText_LanguageNotGiven_error() throws Exception {
		Text text = createUnderTest(null, "this is a docu");
		
		try {
			persist(text);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("language is null"));
		}
	}
	
	@Test
	public void testConvertTextXML() throws Exception {
		Text text = convertToModel(createExpectedXml());
		assertText(text, "this is a docu", "English");
	}
}
