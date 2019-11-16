package com.uniqueck.asciidoctorj.lfet.puml.activity;

import lombok.Getter;

@Getter
public enum Language {
    German("J", "N", "B", "Ja", "Nein"), English("Y", "N", "C", "yes", "no");


    private final String labelTrue;
    private final String labelFalse;
    private String labelCondition;
    private String actvityLabelTrue;
    private String activtiyLabelFalse;

    Language(String labelTrue, String labelFalse, String labelCondition, String actvityLabelTrue, String activtiyLabelFalse) {
        this.labelTrue = labelTrue;
        this.labelFalse = labelFalse;
        this.labelCondition = labelCondition;
        this.actvityLabelTrue = actvityLabelTrue;
        this.activtiyLabelFalse = activtiyLabelFalse;
    }

    public String getActivtyLabel(boolean conditionState)  {
        return conditionState ? getActvityLabelTrue() : getActivtiyLabelFalse();
    }
}