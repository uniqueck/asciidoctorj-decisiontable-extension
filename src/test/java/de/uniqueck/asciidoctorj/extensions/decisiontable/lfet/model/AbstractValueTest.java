package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractValueTest extends AbstractLfdtTest<AbstractValue> {
	private AbstractValue createUnderTest(String value) {
		return new AbstractValue(value) {};
	}

	private String createExpectedXml() {
		return "< value='myValue'/>".replaceAll("'", "\"");
	}

	@Test
	void testValue() throws Exception {
		AbstractValue value = createUnderTest("myValue");
		assertEquals("myValue", value.getValue());
	}
	
	@Test
	void testPersistValue_ValueGiven_noError() throws Exception {
		AbstractValue value = createUnderTest("myValue");
		String xml = persist(value);
		assertEquals(createExpectedXml(), xml);
	}

	@Test
	void testPersistValue_ValueNotGiven_error() throws Exception {
		AbstractValue value = createUnderTest(null);
		
		try {
			persist(value);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("value is null"));
		}
	}
}
