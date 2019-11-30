package com.uniqueck.asciidoctorj.lfet.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AbstractRulePartTest extends AbstractLfdtTest<AbstractRulePart<AbstractOccurrence>> {
	private AbstractRulePart<AbstractOccurrence> createUnderTest(String uid, Title title, Text text, List<SourceCode> sourceCodes, List<AbstractOccurrence> occurences, List<Url> urls) {
		return new AbstractRulePart<AbstractOccurrence>(uid, Arrays.asList(title), text, sourceCodes, occurences, urls) {};
	}
	
	private String createExpectedXml_withoutOccurences() {
		String xml = "< uId='12345'>" + NEW_LINE
		     + "   <Title value='title' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='$foundItem' codeLanguage='Perl' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='$foundItem = ();' codeLanguage='Perl' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <UrlsOut/>" + NEW_LINE
		     + "</>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}

	private String createExpectedXml_withOccurencesAndUrls() {
		String xml = "< uId='12345'>" + NEW_LINE
		     + "   <Title value='title' language='English'/>" + NEW_LINE
		     + "   <SourceCode value='$foundItem' codeLanguage='Perl' sourceCodeType='LogArg'/>" + NEW_LINE
		     + "   <SourceCode value='$foundItem = ();' codeLanguage='Perl' sourceCodeType='Prolog'/>" + NEW_LINE
		     + "   <Text value='docuText' language='English'/>" + NEW_LINE
		     + "   <UrlsOut>" + NEW_LINE
		     + "      <Url title='title1' url='http://url1'/>" + NEW_LINE
		     + "      <Url title='title2' url='http://url2'/>" + NEW_LINE
		     + "   </UrlsOut>" + NEW_LINE
		     + "</>";
		xml = xml.replaceAll("'", "\"");
		return xml;
	}
	
	@Test
	public void testAbstractRulePart() throws Exception {
		String uid = "12345";
		Title title = new Title("English", "title");
		Text text = new Text("English", "docuText");
		
		SourceCode sourceCode = new SourceCode("Perl", "LogArg", "$foundItem");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode);
		
		List<AbstractOccurrence> occurences = new ArrayList<AbstractOccurrence>();
		List<Url> urls = new ArrayList<Url>();
		
		AbstractRulePart<AbstractOccurrence> rulePart = new AbstractRulePart<AbstractOccurrence>(uid, Arrays.asList(title), text, sourceCodes, occurences, urls) {};
		
		assertSame(uid, rulePart.getUId());
		assertEquals(Arrays.asList(title), rulePart.getTitle());
		assertSame(text, rulePart.getText());
		assertSame(sourceCodes, rulePart.getSourceCodes());
		assertSame(occurences, rulePart.getOccurrences());
		assertSame(urls, rulePart.getUrls());
	}

	@Test
	public void testPersistModel_withoutOccurencesAndWithoutUrls() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Perl", "LogArg", "$foundItem");
		SourceCode sourceCode2 = new SourceCode("Perl", "Prolog", "$foundItem = ();");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);
		
		AbstractRulePart<AbstractOccurrence> action = createUnderTest("12345", new Title("English", "title"),
			new Text("English", "docuText"), sourceCodes, null, null);
		
		String xml = persist(action);
		assertEquals(createExpectedXml_withoutOccurences(), xml);
	}

	@Test
	public void testPersistModel_withOccurencesAndUrls() throws Exception {
		SourceCode sourceCode1 = new SourceCode("Perl", "LogArg", "$foundItem");
		SourceCode sourceCode2 = new SourceCode("Perl", "Prolog", "$foundItem = ();");
		List<SourceCode> sourceCodes = new ArrayList<SourceCode>();
		sourceCodes.add(sourceCode1);
		sourceCodes.add(sourceCode2);
		List<AbstractOccurrence> occurrences = new ArrayList<AbstractOccurrence>();
		List<SourceCode> sourceCodesForOccurrence1 = new ArrayList<SourceCode>();
		sourceCodesForOccurrence1.add(new SourceCode("Perl", "sourceCodeType11", "value11"));
		sourceCodesForOccurrence1.add(new SourceCode("Perl", "sourceCodeType12", "value12"));
		List<Url> urlsForConditionOccurrence1 = new ArrayList<Url>();
		urlsForConditionOccurrence1.add(new Url("title11", "http://url11", null));
		urlsForConditionOccurrence1.add(new Url("title21", "http://url21", null));
		occurrences.add(new AbstractOccurrence("23456", new Symbol("German", "SYMBOL"), Arrays.asList(new Title("German", "titleValue")), new Text("English", "docuText1"), sourceCodesForOccurrence1, urlsForConditionOccurrence1) {});
		List<SourceCode> sourceCodesForOccurrence2 = new ArrayList<SourceCode>();
		sourceCodesForOccurrence2.add(new SourceCode("Perl", "sourceCodeType21", "value21"));
		sourceCodesForOccurrence2.add(new SourceCode("Perl", "sourceCodeType22", "value22"));
		List<Url> urlsForConditionOccurrence2 = new ArrayList<Url>();
		urlsForConditionOccurrence2.add(new Url("title12", "http://url12", null));
		urlsForConditionOccurrence2.add(new Url("title22", "http://url22", null));
		occurrences.add(new AbstractOccurrence("34567", new Symbol("German", "SYMBOL2"), Arrays.asList(new Title("German", "titleValue2")), new Text("English", "docuText2"), sourceCodesForOccurrence2, urlsForConditionOccurrence2) {});
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("title1", "http://url1", null));
		urls.add(new Url("title2", "http://url2", null));

		AbstractRulePart<AbstractOccurrence> action = createUnderTest("12345", new Title("English", "title"),
			new Text("English", "docuText"), sourceCodes, occurrences, urls);
		
		String xml = persist(action);
		assertEquals(createExpectedXml_withOccurencesAndUrls(), xml);
	}	

}
