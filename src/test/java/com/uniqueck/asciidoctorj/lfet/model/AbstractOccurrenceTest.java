package com.uniqueck.asciidoctorj.lfet.model;

import com.uniqueck.asciidoctorj.lfet.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AbstractOccurrenceTest extends AbstractLfdtTest<AbstractOccurrence> {
	private AbstractOccurrence createUnderTest(String uid, Symbol symbol, Title title, Text text, List<SourceCode> sourceCodes, List<Url> urls) {
		return new AbstractOccurrence(uid, symbol, title, text, sourceCodes, urls) {};
	}

	private String createExpectedXml() {
		String xml = "< uId='12345'>" + NEW_LINE
			 + "   <Symbol value='symbol' language='English'/>" + NEW_LINE
			 + "   <Title value='title' language='English'/>" + NEW_LINE
			 + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='value1' codeLanguage='Java' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='value2' codeLanguage='Java' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <UrlsOut>" + NEW_LINE
		     + "      <Url title='title1' url='http://url1'/>" + NEW_LINE
		     + "      <Url title='title2' url='http://url2'/>" + NEW_LINE
		     + "   </UrlsOut>" + NEW_LINE
		     + "</>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}
	
	@Test
	public void testOccurences() throws Exception {
		String uid = "12345";
		Symbol symbol = new Symbol("English", "symbol");
		Title title = new Title("English", "title");
		Text text = new Text("English", "docuText");
		
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		SourceCode sourceCode1 = new SourceCode("Java", "LogArg", "value1");
		sourceCodes.add(sourceCode1);
		SourceCode sourceCode2 = new SourceCode("Java", "LogArg2", "value2");
		sourceCodes.add(sourceCode2);
		
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));
		
		AbstractOccurrence occurrence = createUnderTest(uid, symbol, title, text, sourceCodes, urls);
		assertSame(uid, occurrence.getUId());
		assertSame(symbol, occurrence.getSymbol());
		assertSame(title, occurrence.getTitle());
		assertSame(text, occurrence.getText());
		assertSame(sourceCodes, occurrence.getSourceCodes());
		assertSame(urls, occurrence.getUrls());
	}

	@Test
	public void testPersistModel() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Java", "LogArg", "value1");
		SourceCode sourceCode2 = new SourceCode("Java", "Prolog", "value2");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));

		AbstractOccurrence occurence = createUnderTest("12345", new Symbol("English", "symbol"), new Title("English", "title"),
				new Text("English", "docuText"), sourceCodes, urls);
		
		String xml = persist(occurence);
		assertEquals(createExpectedXml(), xml);
	}
}
