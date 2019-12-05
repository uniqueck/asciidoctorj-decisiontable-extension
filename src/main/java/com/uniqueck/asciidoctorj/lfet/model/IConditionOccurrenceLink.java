package com.uniqueck.asciidoctorj.lfet.model;

public interface IConditionOccurrenceLink extends IConditionEntryLink {

    String getSymbol();

    boolean isFirstOccurrence();

    boolean isLastOccurrence();

}
