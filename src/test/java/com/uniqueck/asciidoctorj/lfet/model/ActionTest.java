package com.uniqueck.asciidoctorj.lfet.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest extends AbstractLfdtTest<Action> {
	private Action createUnderTest(String uid, Title title, Text text, List<SourceCode> sourceCodes, List<ActionOccurrence> occurences, List<Url> urls) {
		return new Action(uid, title, text, sourceCodes, occurences, urls);
	}

	private String createExpectedXml_withoutOccurencesAndUrls() {
		String xml = "<action uId='12345'>" + NEW_LINE
		     + "   <Title value='title' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='value1' codeLanguage='Java' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='value2' codeLanguage='Java' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <UrlsOut/>" + NEW_LINE 
		     + "   <ActionOccurrences/>" + NEW_LINE
		     + "</action>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}

	private String createExpectedXml_withOccurencesAndUrls() {
		String xml = "<action uId='12345'>" + NEW_LINE
		     + "   <Title value='title' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='value1' codeLanguage='Java' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='value2' codeLanguage='Java' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <UrlsOut>" + NEW_LINE
		     + "      <Url title='title1' url='http://url1'/>" + NEW_LINE
		     + "      <Url title='title2' url='http://url2'/>" + NEW_LINE
		     + "   </UrlsOut>" + NEW_LINE
		     + "   <ActionOccurrences>" + NEW_LINE
		     + "      <ActionOccurrence uId='11111'>" + NEW_LINE
		     + "         <Symbol value='symbol1' language='English'/>" + NEW_LINE
		     + "         <Title value='title1' language='English'/>" + NEW_LINE
	         + "         <Text value='docuText1' language='English'/>" + NEW_LINE
		     + "         <SourceCode value='value11' codeLanguage='Java' sourceCodeType='sourceCodeType11'/>" + NEW_LINE
		     + "         <SourceCode value='value12' codeLanguage='Java' sourceCodeType='sourceCodeType12'/>" + NEW_LINE
		     + "         <UrlsOut>" + NEW_LINE
		     + "            <Url title='title11' url='http://url11'/>" + NEW_LINE
		     + "            <Url title='title21' url='http://url21'/>" + NEW_LINE
		     + "         </UrlsOut>" + NEW_LINE 
		     + "      </ActionOccurrence>" + NEW_LINE
		     + "      <ActionOccurrence uId='22222'>" + NEW_LINE
		     + "         <Symbol value='symbol2' language='English'/>" + NEW_LINE
		     + "         <Title value='title2' language='English'/>" + NEW_LINE
	         + "         <Text value='docuText2' language='English'/>" + NEW_LINE
		     + "         <SourceCode value='value11' codeLanguage='Java' sourceCodeType='sourceCodeType11'/>" + NEW_LINE
		     + "         <SourceCode value='value12' codeLanguage='Java' sourceCodeType='sourceCodeType12'/>" + NEW_LINE
		     + "         <UrlsOut>" + NEW_LINE
		     + "            <Url title='title12' url='http://url12'/>" + NEW_LINE
		     + "            <Url title='title22' url='http://url22'/>" + NEW_LINE
		     + "         </UrlsOut>" + NEW_LINE
		     + "      </ActionOccurrence>" + NEW_LINE
		     + "   </ActionOccurrences>" + NEW_LINE
		     + "</action>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}
	
	@Test
	public void testAction() throws Exception {
		String uid = "12345";
		Title title = new Title("English", "title");
		Text text = new Text("English", "docuText");
		
		SourceCode sourceCode = new SourceCode("Java", "LogArg", "value1");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode);

		List<ActionOccurrence> occurences = new ArrayList<ActionOccurrence>();
		
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));
		
		Action action = createUnderTest(uid, title, text, sourceCodes, occurences, urls);
		
		assertSame(uid, action.getUId());
		assertSame(title, action.getTitle());
		assertSame(text, action.getText());
		assertSame(sourceCodes, action.getSourceCodes());
		assertSame(occurences, action.getOccurrences());
		assertSame(urls, action.getUrls());
	}

	@Test
	public void testPersistModel_withoutOccurencesAndUrls() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Java", "LogArg", "value1");
		SourceCode sourceCode2 = new SourceCode("Java", "Prolog", "value2");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);

		Action action = createUnderTest("12345", new Title("English", "title"),
				new Text("English", "docuText"), sourceCodes, null, null);
		
		String xml = persist(action);
		assertEquals(createExpectedXml_withoutOccurencesAndUrls(), xml);
	}
	
	@Test
	public void testPersistModel_withOccurences() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Java", "LogArg", "value1");
		SourceCode sourceCode2 = new SourceCode("Java", "Prolog", "value2");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);

		List<SourceCode> sourceCodesForActionOccurrence1 = new ArrayList<SourceCode>();
		sourceCodesForActionOccurrence1.add(new SourceCode("Java", "sourceCodeType11", "value11"));
		sourceCodesForActionOccurrence1.add(new SourceCode("Java", "sourceCodeType12", "value12"));
		List<Url> urlsForConditionOccurrence1 = new ArrayList<Url>();
		urlsForConditionOccurrence1.add(new Url("title11", "http://url11", null));
		urlsForConditionOccurrence1.add(new Url("title21", "http://url21", null));
		ActionOccurrence occurence1 = new ActionOccurrence("11111", new Symbol("English", "symbol1"), new Title("English", "title1"), new Text("English", "docuText1"), sourceCodesForActionOccurrence1, urlsForConditionOccurrence1);
		
		List<SourceCode> sourceCodesForActionOccurrence2 = new ArrayList<SourceCode>();
		sourceCodesForActionOccurrence2.add(new SourceCode("Java", "sourceCodeType11", "value11"));
		sourceCodesForActionOccurrence2.add(new SourceCode("Java", "sourceCodeType12", "value12"));
		List<Url> urlsForConditionOccurrence2 = new ArrayList<Url>();
		urlsForConditionOccurrence2.add(new Url("title12", "http://url12", null));
		urlsForConditionOccurrence2.add(new Url("title22", "http://url22", null));
		ActionOccurrence occurence2 = new ActionOccurrence("22222", new Symbol("English", "symbol2"), new Title("English", "title2"), new Text("English", "docuText2"), sourceCodesForActionOccurrence2, urlsForConditionOccurrence2);			
		
		List<ActionOccurrence> occurences = new ArrayList<ActionOccurrence>();
		occurences.add(occurence1);
		occurences.add(occurence2);

		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));

		Action action = createUnderTest("12345", new Title("English", "title"),
				new Text("English", "docuText"), sourceCodes, occurences, urls);
		
		String xml = persist(action);
		assertEquals(createExpectedXml_withOccurencesAndUrls(), xml);
	}
	
	@Test
	public void testConvertActionXML_withoutOccurencesAndUrls() throws Exception {
		Action action = convertToModel(createExpectedXml_withoutOccurencesAndUrls());
		
		assertEquals("12345", action.getUId());
		assertEquals(2, action.getSourceCodes().size());
		assertSourceCode(action.getSourceCodes().get(0), "Java", "LogArg", "value1");
		assertSourceCode(action.getSourceCodes().get(1), "Java", "Prolog", "value2");
		assertTitle(action.getTitle(), "title", "English");
		assertText(action.getText(), "docuText", "English");
		assertNull(action.getOccurrences());
		assertNull(action.getUrls());
	}
	
	@Test
	public void testConvertActionXML_withOccurencesAndUrls() throws Exception {
		Action action = convertToModel(createExpectedXml_withOccurencesAndUrls());
		
		assertEquals("12345", action.getUId());
		assertEquals(2, action.getSourceCodes().size());
		assertSourceCode(action.getSourceCodes().get(0), "Java", "LogArg", "value1");
		assertSourceCode(action.getSourceCodes().get(1), "Java", "Prolog", "value2");
		assertTitle(action.getTitle(), "title", "English");
		assertText(action.getText(), "docuText", "English");
		assertEquals(2, action.getUrls().size());
		assertUrl(action.getUrls().get(0), "title1", "http://url1", false);
		assertUrl(action.getUrls().get(1), "title2", "http://url2", false);
		
		assertEquals(2, action.getOccurrences().size());

		ActionOccurrence actionOccurrence = action.getOccurrences().get(0);
		assertEquals("11111", actionOccurrence.getUId());
		assertSymbol(actionOccurrence.getSymbol(), "symbol1", "English");
		assertTitle(actionOccurrence.getTitle(), "title1", "English");
		assertText(actionOccurrence.getText(), "docuText1", "English");
		assertEquals(2, actionOccurrence.getSourceCodes().size());
		assertSourceCode(actionOccurrence.getSourceCodes().get(0), "Java", "sourceCodeType11", "value11");
		assertSourceCode(actionOccurrence.getSourceCodes().get(1), "Java", "sourceCodeType12", "value12");
		assertEquals(2, actionOccurrence.getUrls().size());
		assertUrl(actionOccurrence.getUrls().get(0), "title11", "http://url11", false);
		assertUrl(actionOccurrence.getUrls().get(1), "title21", "http://url21", false);
		
		actionOccurrence = action.getOccurrences().get(1);
		assertEquals("22222", actionOccurrence.getUId());
		assertSymbol(actionOccurrence.getSymbol(), "symbol2", "English");
		assertTitle(actionOccurrence.getTitle(), "title2", "English");
		assertText(actionOccurrence.getText(), "docuText2", "English");
		assertEquals(2, actionOccurrence.getSourceCodes().size());
		assertSourceCode(actionOccurrence.getSourceCodes().get(0), "Java", "sourceCodeType11", "value11");
		assertSourceCode(actionOccurrence.getSourceCodes().get(1), "Java", "sourceCodeType12", "value12");
		assertEquals(2, actionOccurrence.getUrls().size());
		assertUrl(actionOccurrence.getUrls().get(0), "title12", "http://url12", false);
		assertUrl(actionOccurrence.getUrls().get(1), "title22", "http://url22", false);
	}
}
