package com.uniqueck.asciidoctorj.lfet.puml.activity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
class ConditionDiagramGenerator extends AbstractLFETTraceLogging implements IConditionToPlantUMLActivityDiagram, IConditionDiagramGenerator {

    List<String> content;
    @Getter(AccessLevel.PROTECTED)
    private Element rootElement;
    @Getter(AccessLevel.PROTECTED)
    private Element rule;
    @Getter(AccessLevel.PROTECTED)
    private Element condition;
    @Getter(AccessLevel.PROTECTED)
    private List<String> conditionIds;


    ConditionDiagramGenerator(Element rootElement, Element rule, Element condition) {
        this.rootElement = rootElement;
        this.rule = rule;
        this.conditionIds = this.rule.getChildren().stream().filter(e -> e.getName().startsWith("Condition")).map(e -> e.getAttributeValue("link")).collect(Collectors.toList());
        this.condition = condition;
        content = new ArrayList<>();
    }

    @Override
    public boolean isConditionTypeIsOccurenceTable() {
        return getCondition().getChild("ConditionOccurences") != null;
    }

    @Override
    public boolean isConditionStateIs() {
        List<Element> conditionLinks = getRule().getChildren("ConditionLink");
        Optional<Element> founded = conditionLinks.stream().filter(c -> c.getAttributeValue("link").equalsIgnoreCase(getCondition().getAttributeValue("uId"))).findFirst();
        try {
            return founded.isPresent() && founded.get().getAttribute("conditionState").getBooleanValue();
        } catch (DataConversionException e) {
            return false;
        }

    }

    @Override
    public boolean isCurrentOccEntryIs() {
        return false;
    }

    @Override
    public boolean isCurrentOccEntryIsLast() {
        return false;
    }

    @Override
    public boolean isCurrentOccEntryIsFirst() {
        return false;
    }

    @Override
    public boolean isHasMoreConditions() {
        List<Element> conditionLinks = getRule().getChildren("ConditionLink");
        return !conditionLinks.get(conditionLinks.size() - 1).getAttributeValue("link").equals(getCondition().getAttributeValue("uId"));
    }

    @Override
    public void doIfWithConditionTitleAndLabelYes() {
        add("if (" + getConditionTitle() + ") then (yes) ");
    }

    @Override
    public void doElseWithLabelNo() {
        add(" else (no) ");
    }

    @Override
    public void doElse() {
        add(" else ");

    }

    @Override
    public void doElseIfConditionTitleWithOccEntryAndLabelYes() {
        add("elsif (" + getConditionTitle() + "\n" + getConditionOccEntry() + ") then (yes) ");
    }

    @Override
    public void doIfConditionTitleWithOccEntryAndLabelYes() {
        add("if (" + getConditionTitle() + "\n" + getConditionOccEntry() + ") then (yes) ");
    }

    @Override
    public void doAddListOfActions() {
        List<String> actionIds = getRule().getChildren().stream().filter(e -> e.getName().startsWith("Action")).map(e -> e.getAttributeValue("link")).collect(Collectors.toList());
        List<Element> actions = getRootElement().getChild("Actions").getChildren("Action").stream().filter(a -> actionIds.contains(a.getAttributeValue("uId"))).collect(Collectors.toList());
        for (Element action : actions) {
            add("-" + action.getChild("Title").getAttributeValue("value"));
        }
    }

    @Override
    public void doEndif() {
        add("endif");
    }

    @Override
    public List<String> generate() {
        new ConditionToPlantUMLActivityDiagramRules().execute(this);
        return content;
    }

    protected void add(String line) {
        this.content.add(line);
    }

    protected String getConditionTitle() {
        return getCondition().getChild("Title").getAttributeValue("value");
    }

    protected String getConditionOccEntry() {
        return "";
    }

}
