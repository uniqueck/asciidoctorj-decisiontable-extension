package com.uniqueck.asciidoctorj.lfet;

import com.uniqueck.asciidoctorj.exceptions.AsciiDoctorDecisionTableRuntimeException;
import lombok.AccessLevel;
import lombok.Getter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Getter(AccessLevel.PACKAGE)
public class LFETAsciiDocTableGenerator {

    public static final String ELEMENT_TITLE = "Title";
    public static final String ATTR_VALUE = "value";
    public static final String ELEMENT_ACTIONS = "Actions";
    public static final String ELEMENT_ACTION = "Action";

    @Getter
    enum Language {
        German("J", "N", "B"), English("Y", "N", "C");


        private final String labelTrue;
        private final String labelFalse;
        private String labelCondition;

        Language(String labelTrue, String labelFalse, String labelCondition) {
            this.labelTrue = labelTrue;
            this.labelFalse = labelFalse;
            this.labelCondition = labelCondition;
        }
    }

    public static final String TABLE_START_END_TAG = "|====";
    private final Document lfetDocument;
    private final Language lfetLanguage;

    public LFETAsciiDocTableGenerator(File sourceFile) {
        try {
            lfetDocument = new SAXBuilder().build(sourceFile);
            lfetLanguage = Language.valueOf(lfetDocument.getRootElement().getAttributeValue("language"));
        } catch (Exception e) {
            throw new AsciiDoctorDecisionTableRuntimeException("Error on reading decision table file '" + sourceFile.getPath() + "'", e);
        }
    }

    public List<String> generate() {
        List<String> content = new ArrayList<>();
        content.add("." +  getTitle());

        int ruleCount = getRuleCount();

        String colsOptionString = getColsOption(ruleCount);

        content.add("[width=\"100%\",options=header,cols=\""+colsOptionString+"\",frame=none,grid=all]");

        content.add(TABLE_START_END_TAG);

        content.addAll(createHeader(ruleCount));


        List<Element> conditions = getRootElement().getChild("Conditions").getChildren("Condition");
        List<Element> rules = getRootElement().getChild("Rules").getChildren("Rule");

        for (int conditionIndex = 0; conditionIndex < conditions.size(); conditionIndex++) {

            Element condition = conditions.get(conditionIndex);

            content.add(String.format("^.^h|%s%02d", getLfetLanguage().getLabelCondition(), conditionIndex+1));
            content.add(String.format(".^h|%s", condition.getChild(ELEMENT_TITLE).getAttributeValue(ATTR_VALUE)));

            for (Element rule : rules) {

                content.add("^.^|" + getConditionSymbol(rule, condition));

            }

        }

        content.add((ruleCount + 2) + "+|");

        List<Element> actions = getRootElement().getChild(ELEMENT_ACTIONS).getChildren(ELEMENT_ACTION);
        for (int actionIndex = 0; actionIndex < actions.size(); actionIndex++) {

            Element action = actions.get(actionIndex);

            content.add(String.format("^.^h|A%02d", actionIndex+1));
            content.add(String.format(".^h|%s", action.getChild(ELEMENT_TITLE).getAttributeValue(ATTR_VALUE)));

            for (Element rule : rules) {

                content.add("^.^|" + getActionSymbol(rule, action));

            }

        }



        content.add(TABLE_START_END_TAG);

        return content;
    }


    private String getConditionSymbol(Element rule, Element condition) {
        Element conditionOccurrences = condition.getChild("ConditionOccurrences");
        if (conditionOccurrences != null) {
            List<Element> conditionOccurrence = conditionOccurrences.getChildren("ConditionOccurrence");
            List<String> conOccLinks = rule.getChildren("ConditionOccurrenceLink").stream().map(col -> col.getAttributeValue("link")).collect(Collectors.toList());
            Optional<Element> conditionOcc = conditionOccurrence.stream().filter(conOcc -> conOccLinks.contains(conOcc.getAttributeValue("uId"))).findFirst();
            if (conditionOcc.isPresent()) {
                return conditionOcc.get().getChild("Symbol").getAttributeValue("value");
            }
        } else {
            Optional<Element> conditionLink = rule.getChildren("ConditionLink").stream().filter(cl -> cl.getAttributeValue("link").equalsIgnoreCase(condition.getAttributeValue("uId"))).findFirst();
            if (conditionLink.isPresent()) {
                return (conditionLink.get().getAttributeValue("conditionState").equalsIgnoreCase("true") ? getLfetLanguage().getLabelTrue() : getLfetLanguage().getLabelFalse());
            }
        }
        return "-";
    }

    private String getActionSymbol(Element rule, Element action) {
        Element actionOccurrences = action.getChild("ActionOccurrences");
        if (actionOccurrences != null) {
            List<Element> actionOccurrence = actionOccurrences.getChildren("ActionOccurrence");
            List<String> actOccLinks = rule.getChildren("ActionOccurrenceLink").stream().map(acl -> acl.getAttributeValue("link")).collect(Collectors.toList());
            Optional<Element> actionOcc = actionOccurrence.stream().filter(actOcc -> actOccLinks.contains(actOcc.getAttributeValue("uId"))).findFirst();
            if (actionOcc.isPresent()) {
                return actionOcc.get().getChild("Symbol").getAttributeValue("value");
            }
        } else {
            Optional<Element> actionLink = rule.getChildren("ActionLink").stream().filter(act -> act.getAttributeValue("link").equalsIgnoreCase(action.getAttributeValue("uId"))).findFirst();
            if (actionLink.isPresent()) {
                return "X";
            }
        }
        return "";
    }

    private List<String> createHeader(int ruleCount) {
        List<String> content = new ArrayList<>();
        content.add("2+|");
        for (int i = 0; i < ruleCount; i++) {
            content.add(String.format("^.^|R%02d", i + 1));
        }
        return content;
    }

    private String getColsOption(int ruleCount) {
        List<String> cols = new ArrayList<>();
        cols.add("1");
        cols.add("3");
        for (int i = 0; i<ruleCount; i++) {
            cols.add("2");
        }
        return cols.stream().collect(Collectors.joining(","));
    }

    private String getTitle() {
        return getRootElement().getChild("Title").getAttributeValue("value");
    }

    private Element getRootElement() {
        return getLfetDocument().getRootElement();
    }

    private int getRuleCount() {
        return getRootElement().getChild("Rules").getChildren("Rule").size();
    }

}
