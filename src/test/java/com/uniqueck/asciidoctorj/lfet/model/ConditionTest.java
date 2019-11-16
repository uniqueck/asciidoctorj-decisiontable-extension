package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionTest extends AbstractLfdtTest<Condition> {
	private Condition createUnderTest(String uid, Title title, Text text, List<SourceCode> sourceCodes, List<ConditionOccurrence> occurences, List<Url> urls) {
		return new Condition(uid, title, text, sourceCodes, occurences, urls);
	}

	private String createExpectedXml_withoutOccurencesAndUrls() {
		String xml = "<condition uId='12345'>" + NEW_LINE
		     + "   <Title value='title' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='value1' codeLanguage='Java' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='value2' codeLanguage='Java' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <UrlsOut/>" + NEW_LINE
		     + "   <ConditionOccurrences/>" + NEW_LINE
		     + "</condition>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}
	
	private String createExpectedXml_withOccurencesAndUrls() {
		String xml = "<condition uId='12345'>" + NEW_LINE
		     + "   <Title value='title' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='value1' codeLanguage='Java' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='value2' codeLanguage='Java' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <UrlsOut>" + NEW_LINE
		     + "      <Url title='title1' url='http://url1'/>" + NEW_LINE
		     + "      <Url title='title2' url='http://url2'/>" + NEW_LINE
		     + "   </UrlsOut>" + NEW_LINE
		     + "   <ConditionOccurrences>" + NEW_LINE
		     + "      <ConditionOccurrence uId='11111'>" + NEW_LINE
		     + "         <Symbol value='symbol1' language='English'/>" + NEW_LINE
		     + "         <Title value='title1' language='English'/>" + NEW_LINE
	         + "         <Text value='docuText1' language='English'/>" + NEW_LINE
		     + "         <SourceCode value='value11' codeLanguage='Java' sourceCodeType='sourceCodeType11'/>" + NEW_LINE
		     + "         <SourceCode value='value12' codeLanguage='Java' sourceCodeType='sourceCodeType12'/>" + NEW_LINE
		     + "         <UrlsOut>" + NEW_LINE
		     + "            <Url title='title11' url='http://url11'/>" + NEW_LINE
		     + "            <Url title='title21' url='http://url21'/>" + NEW_LINE
		     + "         </UrlsOut>" + NEW_LINE
		     + "      </ConditionOccurrence>" + NEW_LINE
		     + "      <ConditionOccurrence uId='22222'>" + NEW_LINE
		     + "         <Symbol value='symbol2' language='English'/>" + NEW_LINE
		     + "         <Title value='title2' language='English'/>" + NEW_LINE
		     + "         <Text value='docuText2' language='English'/>" + NEW_LINE
		     + "         <SourceCode value='value11' codeLanguage='Java' sourceCodeType='sourceCodeType11'/>" + NEW_LINE
		     + "         <SourceCode value='value12' codeLanguage='Java' sourceCodeType='sourceCodeType12'/>" + NEW_LINE
		     + "         <UrlsOut>" + NEW_LINE
		     + "            <Url title='title12' url='http://url12'/>" + NEW_LINE
		     + "            <Url title='title22' url='http://url22'/>" + NEW_LINE
		     + "         </UrlsOut>" + NEW_LINE
		     + "      </ConditionOccurrence>" + NEW_LINE
		     + "   </ConditionOccurrences>" + NEW_LINE
		     + "</condition>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}

	@Test
	public void testCondition() throws Exception {
		String uid = "12345";
		Title title = new Title("English", "title");
		Text text = new Text("English", "docuText");
		SourceCode sourceCode = new SourceCode("Java", "LogArg", "value1");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode);

		List<ConditionOccurrence> occurences = new ArrayList<ConditionOccurrence>();
		
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));
		
		Condition condition = createUnderTest(uid, title, text, sourceCodes, occurences, urls);
		
		assertSame(uid, condition.getUId());
		assertSame(title, condition.getTitle());
		assertSame(text, condition.getText());
		assertSame(sourceCodes, condition.getSourceCodes());
		assertSame(occurences, condition.getOccurrences());
		assertSame(urls, condition.getUrls());
	}

	@Test
	public void testPersistModel_withoutOccurencesAndUrls() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Java", "LogArg", "value1");
		SourceCode sourceCode2 = new SourceCode("Java", "Prolog", "value2");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);
		
		Condition condition = createUnderTest("12345", new Title("English", "title"),
				new Text("English", "docuText"), sourceCodes, null, null);
		
		String xml = persist(condition);
		assertEquals(createExpectedXml_withoutOccurencesAndUrls(), xml);
	}
	
	@Test
	public void testPersistModel_withOccurences() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Java", "LogArg", "value1");
		SourceCode sourceCode2 = new SourceCode("Java", "Prolog", "value2");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);

		List<SourceCode> sourceCodesForConditionOccurrence1 = new ArrayList<SourceCode>();
		sourceCodesForConditionOccurrence1.add(new SourceCode("Java", "sourceCodeType11", "value11"));
		sourceCodesForConditionOccurrence1.add(new SourceCode("Java", "sourceCodeType12", "value12"));
		List<Url> urlsForConditionOccurrence1 = new ArrayList<Url>();
		urlsForConditionOccurrence1.add(new Url("title11", "http://url11", null));
		urlsForConditionOccurrence1.add(new Url("title21", "http://url21", null));
		ConditionOccurrence occurence1 = new ConditionOccurrence("11111", new Symbol("English", "symbol1"), new Title("English", "title1"), new Text("English", "docuText1"), sourceCodesForConditionOccurrence1, urlsForConditionOccurrence1);
		List<SourceCode> sourceCodesForCondtionOccurrence2 = new ArrayList<SourceCode>();
		sourceCodesForCondtionOccurrence2.add(new SourceCode("Java", "sourceCodeType11", "value11"));
		sourceCodesForCondtionOccurrence2.add(new SourceCode("Java", "sourceCodeType12", "value12"));
		List<Url> urlsForConditionOccurrence2 = new ArrayList<Url>();
		urlsForConditionOccurrence2.add(new Url("title12", "http://url12", null));
		urlsForConditionOccurrence2.add(new Url("title22", "http://url22", null));
		ConditionOccurrence occurence2 = new ConditionOccurrence("22222", new Symbol("English", "symbol2"), new Title("English", "title2"), new Text("English", "docuText2"), sourceCodesForCondtionOccurrence2, urlsForConditionOccurrence2);
		List<ConditionOccurrence> occurences = new ArrayList<ConditionOccurrence>();
		occurences.add(occurence1);
		occurences.add(occurence2);
		
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));
		
		Condition condition = createUnderTest("12345", new Title("English", "title"),
				new Text("English", "docuText"), sourceCodes, occurences, urls);
		
		String xml = persist(condition);
		assertEquals(createExpectedXml_withOccurencesAndUrls(), xml);
	}
	
	@Test
	public void testConvertActionXML_withoutOccurencesAndUrls() throws Exception {
		Condition condition = convertToModel(createExpectedXml_withoutOccurencesAndUrls());
		
		assertEquals("12345", condition.getUId());
		assertEquals(2, condition.getSourceCodes().size());
		assertSourceCode(condition.getSourceCodes().get(0), "Java", "LogArg", "value1");
		assertSourceCode(condition.getSourceCodes().get(1), "Java", "Prolog", "value2");
		assertTitle(condition.getTitle(), "title", "English");
		assertText(condition.getText(), "docuText", "English");
		assertNull(condition.getOccurrences());
		assertNull(condition.getUrls());
	}
	
	@Test
	public void testConvertActionXML_withOccurencesAndUrls() throws Exception {
		Condition condition = convertToModel(createExpectedXml_withOccurencesAndUrls());
		
		assertEquals("12345", condition.getUId());
		assertEquals(2, condition.getSourceCodes().size());
		assertSourceCode(condition.getSourceCodes().get(0), "Java", "LogArg", "value1");
		assertSourceCode(condition.getSourceCodes().get(1), "Java", "Prolog", "value2");
		assertTitle(condition.getTitle(), "title", "English");
		assertText(condition.getText(), "docuText", "English");
		assertEquals(2, condition.getUrls().size());
		assertUrl(condition.getUrls().get(0), "title1", "http://url1", false);
		assertUrl(condition.getUrls().get(1), "title2", "http://url2", false);
		
		assertEquals(2, condition.getOccurrences().size());

		ConditionOccurrence conditionOccurrence = condition.getOccurrences().get(0);
		assertEquals("11111", conditionOccurrence.getUId());
		assertSymbol(conditionOccurrence.getSymbol(), "symbol1", "English");
		assertTitle(conditionOccurrence.getTitle(), "title1", "English");
		assertText(conditionOccurrence.getText(), "docuText1", "English");
		assertEquals(2, conditionOccurrence.getSourceCodes().size());
		assertSourceCode(conditionOccurrence.getSourceCodes().get(0), "Java", "sourceCodeType11", "value11");
		assertSourceCode(conditionOccurrence.getSourceCodes().get(1), "Java", "sourceCodeType12", "value12");
		assertEquals(2, conditionOccurrence.getUrls().size());
		assertUrl(conditionOccurrence.getUrls().get(0), "title11", "http://url11", false);
		assertUrl(conditionOccurrence.getUrls().get(1), "title21", "http://url21", false);
		
		conditionOccurrence = condition.getOccurrences().get(1);
		assertEquals("22222", conditionOccurrence.getUId());
		assertSymbol(conditionOccurrence.getSymbol(), "symbol2", "English");
		assertTitle(conditionOccurrence.getTitle(), "title2", "English");
		assertText(conditionOccurrence.getText(), "docuText2", "English");
		assertEquals(2, conditionOccurrence.getSourceCodes().size());
		assertSourceCode(conditionOccurrence.getSourceCodes().get(0), "Java", "sourceCodeType11", "value11");
		assertSourceCode(conditionOccurrence.getSourceCodes().get(1), "Java", "sourceCodeType12", "value12");
		assertEquals(2, conditionOccurrence.getUrls().size());
		assertUrl(conditionOccurrence.getUrls().get(0), "title12", "http://url12", false);
		assertUrl(conditionOccurrence.getUrls().get(1), "title22", "http://url22", false);
	}

}
