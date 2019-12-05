package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;
import org.simpleframework.xml.core.AttributeException;

import static org.junit.jupiter.api.Assertions.*;

class UrlTest extends AbstractLfdtTest<Url> {
	private Url createUnderTest() {
		return createUnderTest("title", "file://path", true);
	}
	
	private Url createUnderTest(String title, String link, Boolean executable) {
		return new Url(title, link, executable);
	}
	
	private String createExpectedXml() {
		return "<url title='title' url='file://path' executable='true'/>".replaceAll("'", "\"");
	}

	private String createExpectedXml2() {
		return "<url title='title' url='file://path'/>".replaceAll("'", "\"");
	}

	private String createExpectedXml3() {
		return "<url title='title' url='file://path' executable='false'/>".replaceAll("'", "\"");
	}

	@Test
	void testUrl_threeParams() throws Exception {
		Url url = createUnderTest("title", "http://url", true);
		assertUrl(url, "title", "http://url", true);
		
		url = createUnderTest("title", "http://url", false);
		assertUrl(url, "title", "http://url", false);
		
		url = createUnderTest("title", "http://url", null);
		assertUrl(url, "title", "http://url", false);
	}
	
	@Test
	void testPersistUrl_TitleUrlAndExecutableGiven_noError() throws Exception {
		Url url = createUnderTest();
		String xml = persist(url);
		assertEquals(createExpectedXml(), xml);
	}
	
	@Test
	void testPersistUrl_TitleNotGiven_error() throws Exception {
		Url url = createUnderTest(null, "http://url", true);
		
		try {
			persist(url);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("title is null"));
		}
	}

	@Test
	void testPersistUrl_UrlNotGiven_error() throws Exception {
		Url url = createUnderTest("title", null, true);
		
		try {
			persist(url);
			fail("Exception expected!");
		} catch (AttributeException ex) {
			assertTrue(ex.getMessage().contains("link is null"));
		}
	}
	
	@Test
	void testPersistUrl_ExecutableNotGiven_noErrorBecauseOptional() throws Exception {
		Url url = createUnderTest("title", "file://path", null);
		String xml = persist(url);
		assertEquals(createExpectedXml2(), xml);
	}
	
	@Test
	void testPersistUrl_ExecutableValueFalseGiven_noError() throws Exception {
		Url url = createUnderTest("title", "file://path", false);
		String xml = persist(url);
		assertEquals(createExpectedXml3(), xml);
	}
	
	@Test
	void testConvertUrlXML_urlTagWithTwoAttributes() throws Exception {
		Url url = convertToModel(createExpectedXml2());
		assertUrl(url, "title", "file://path", false);
	}
	
	@Test
	void testConvertUrlXML_urlTagWithThreeAttributes() throws Exception {
		Url url = convertToModel(createExpectedXml());
		assertUrl(url, "title", "file://path", true);
		
		url = convertToModel(createExpectedXml3());
		assertUrl(url, "title", "file://path", false);
	}
}
