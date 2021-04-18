package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.puml.activity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Language {
    GERMAN("J", "N", "B", "Ja", "Nein"), ENGLISH("Y", "N", "C", "yes", "no");

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

    public static Language getEnum(String value) {
        return Arrays.asList(values()).stream().filter(e -> e.name().equalsIgnoreCase(value)).findAny().orElseThrow(() -> new IllegalArgumentException(value + " is not a valid PropName"));
    }
}