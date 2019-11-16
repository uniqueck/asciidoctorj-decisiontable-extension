package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AbstractLfdtTest<MODELCLASS_UNDER_TEST> {
	protected static String NEW_LINE = "\n";
	
	protected final String persist(MODELCLASS_UNDER_TEST modelClass) throws Exception {
		Persister xmlPersister = new Persister();
		StringWriter out = new StringWriter();
		xmlPersister.write(modelClass, out);
		return out.toString();		
	}
	
	@SuppressWarnings ("unchecked")
    public Class<MODELCLASS_UNDER_TEST> getTypeParameterClass()
    {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<MODELCLASS_UNDER_TEST>) paramType.getActualTypeArguments()[0];
    }

	protected final MODELCLASS_UNDER_TEST convertToModel(String xml) throws Exception {
		return new Persister().read(getTypeParameterClass(), xml);
	}
	
	protected void assertSnapshot(Snapshot snapshot, String crDat, String rSeq, String scm, String cars, String focR, String focCA) {
		assertEquals(crDat, snapshot.getCrDat());
		assertEquals(rSeq, snapshot.getRSeq());
		assertEquals(scm, snapshot.getScm());
		assertEquals(cars, snapshot.getCars());
		assertEquals(focR, snapshot.getFocR());
		assertEquals(focCA, snapshot.getFocCA());
	}

	protected void assertConditionLink(ConditionLink conditionLink, Condition expectedLinkedCondition, boolean expectedConditionState) {
		assertSame(expectedLinkedCondition, conditionLink.getLinkedModel());
		assertEquals(conditionLink.getLinkedModel().getUId(), conditionLink.getLink());
		assertEquals(expectedConditionState, conditionLink.getConditionState());
	}

	protected void assertActionLink(ActionLink actionLink, Action expectedLinkedAction) {
		assertSame(expectedLinkedAction, actionLink.getLinkedModel());
		assertEquals(actionLink.getLinkedModel().getUId(), actionLink.getLink());
	}

	protected void assertConditionOccurrenceLink(ConditionOccurrenceLink conditionOccurrenceLink, ConditionOccurrence expectedLinkedConditionOccurrence) {
		assertSame(expectedLinkedConditionOccurrence, conditionOccurrenceLink.getLinkedModel());
		assertEquals(conditionOccurrenceLink.getLinkedModel().getUId(), conditionOccurrenceLink.getLink());
	}
	
	protected void assertActionOccurrenceLink(ActionOccurrenceLink actionOccurrenceLink, ActionOccurrence expectedLinkedActionOccurrence) {
		assertSame(expectedLinkedActionOccurrence, actionOccurrenceLink.getLinkedModel());
		assertEquals(actionOccurrenceLink.getLinkedModel().getUId(), actionOccurrenceLink.getLink());
	}
	
	protected void assertUrl(Url url, String expectedTitle, String expectedUrl, boolean expectedExecutable) {
		assertEquals(expectedTitle, url.getTitle());
		assertEquals(expectedUrl, url.getUrl());
		assertEquals(expectedExecutable, url.isExecutable());
	}
	
	protected void assertSourceCode(SourceCode sourceCode, String expectedCodeLanguage, String expectedSourceCodeType, String expectedValue) {
		assertEquals(expectedCodeLanguage, sourceCode.getCodeLanguage());
		assertEquals(expectedSourceCodeType, sourceCode.getSourceCodeType());
		assertEquals(expectedValue, sourceCode.getValue());
	}

	protected void assertSymbol(Symbol symbol, String expectedSymbol, String expectedLanguage) {
		assertEquals(expectedSymbol, symbol.getValue());
		assertEquals(expectedLanguage, symbol.getLanguage());
	}
	
	protected void assertTitle(Title title, String expectedTitle, String expectedLanguage) {
		assertEquals(expectedTitle, title.getValue());
		assertEquals(expectedLanguage, title.getLanguage());
	}
	
	protected void assertText(Text text, String expectedDocuText, String expectedLanguage) {
		assertEquals(expectedDocuText, text.getValue());
		assertEquals(expectedLanguage, text.getLanguage());		
	}
}
