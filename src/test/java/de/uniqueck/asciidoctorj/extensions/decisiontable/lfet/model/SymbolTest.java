package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTest extends AbstractLfdtTest<Symbol> {
	private Symbol createUnderTest() {
		return createUnderTest("English", "myValue");
	}
	
	private Symbol createUnderTest(String language, String value) {
		return new Symbol(language, value);
	}

	private String createExpectedXml() {
		return "<Symbol value='myValue' language='English'/>".replaceAll("'", "\"");
	}

	@Test
	void testSymbol() throws Exception {
		Symbol symbol = createUnderTest("English", null);
		assertSymbol(symbol, null, "English");
		
		symbol = createUnderTest();
		assertSymbol(symbol, "myValue", "English");
	}
	
	@Test
	void testPersistSymbol_LanguageAndValueGiven_noError() throws Exception {
		Symbol value = createUnderTest();
		String xml = persist(value);
		assertEquals(createExpectedXml(), xml);
	}

	@Test
	void testPersistSymbol_ValueNotGiven_error() throws Exception {
		Symbol symbol = createUnderTest("English", null);
		
		try {
			persist(symbol);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("value is null"));
		}
	}
	
	@Test
	void testPersistSymbol_LanguageNotGiven_error() throws Exception {
		Symbol symbol = createUnderTest(null, "myValue");
		
		try {
			persist(symbol);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("language is null"));
		}
	}
	
	@Test
	void testConvertSymbolXML() throws Exception {
		Symbol symbol = convertToModel(createExpectedXml());
		assertSymbol(symbol, "myValue", "English");
	}
}
