package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import static org.junit.jupiter.api.Assertions.*;

public class SourceCodeTest extends AbstractLfdtTest<SourceCode> {
	private SourceCode createUnderTest() {
		return createUnderTest("Java", "LogArg", "value");
	}
	
	private SourceCode createUnderTest(String codeLanguage, String codeType, String value) {
		return new SourceCode(codeLanguage, codeType, value);
	}

	private String createExpectedXml() {
		return "<SourceCode value='value' codeLanguage='Java' sourceCodeType='LogArg'/>".replaceAll("'", "\"");
	}

	@Test
	public void testSourceCode() throws Exception {
		SourceCode sourceCode = createUnderTest();
		assertSourceCode(sourceCode, "Java", "LogArg", "value");
	}

	@Test
	public void testPersistSourceCode_codeLanguageAndTypeAndValueGiven_noError() throws Exception {
		SourceCode sourceCode = createUnderTest();
		String xml = persist(sourceCode);
		assertEquals(createExpectedXml(), xml);
	}
	
	@Test
	public void testPersistSourceCode_codeLanguageNotGiven_Error() throws Exception {
		SourceCode sourceCode = createUnderTest(null, "LogArg", "value");
		
		try {
			persist(sourceCode);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("codeLanguage is null"));
		}	
	}

	@Test
	public void testPersistSourceCode_sourceCodeTypeNotGiven_Error() throws Exception {
		SourceCode sourceCode = createUnderTest("Java", null, "value");
		
		try {
			persist(sourceCode);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("sourceCodeType is null"));
		}	
	}

	@Test
	public void testPersistSourceCode_valueNotGiven_Error() throws Exception {
		SourceCode sourceCode = createUnderTest("Java", "LogArg", null);
		
		try {
			persist(sourceCode);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("value is null"));
		}	
	}

	@Test
	public void testConvertSourceCodeXML() throws Exception {
		SourceCode sourceCode = convertToModel(createExpectedXml());
		assertSourceCode(sourceCode, "Java", "LogArg", "value");
	}
}
