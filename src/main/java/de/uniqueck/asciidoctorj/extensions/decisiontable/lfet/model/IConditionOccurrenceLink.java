package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

public interface IConditionOccurrenceLink extends IConditionEntryLink {

    String getSymbol();

    boolean isFirstOccurrence();

    boolean isLastOccurrence();

}
