package com.uniqueck.asciidoctorj.lfet.puml.activity;

import com.uniqueck.asciidoctorj.lfet.model.*;
import com.uniqueck.asciidoctorj.lfet.model.Action;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Persister;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Getter(AccessLevel.PACKAGE)
class ActivityDiagramGenerator extends AbstractLFETTraceLogging implements IDecisionTableToPlantUMLActivityDiagram, IActivityDiagramGenerator {

    private List<String> sb;
    @Setter(AccessLevel.PACKAGE)
    private Rule currentRule;
    @Setter(AccessLevel.PACKAGE)
    private AbstractLink<?> currentCondition;
    private Language laguage;
    private LFDecisionTable lfDecisionTable;


    @Override
    public List<String> generate(File decisionTableFile) {
        LFDecisionTable lfDecisionTable = null;
        try {
            lfDecisionTable = new Persister().read(LFDecisionTable.class, decisionTableFile);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Lesen der XML der LFET", e);
        }
        Language language = Language.valueOf(lfDecisionTable.getLanguage());
        List<String> sb = new ArrayList<>();
        sb.add("[plantuml, " +  decisionTableFile.getName() + "]");
        sb.add("----");
        sb.add("title " + lfDecisionTable.getTitle(language).getValue());
        sb.add("");
        sb.add("start");
        sb.addAll(generate(lfDecisionTable, language, lfDecisionTable.getRules().get(0)));
        sb.add("stop");
        sb.add("----");
        return sb;
    }

    List<String> generate(LFDecisionTable lfDecisionTable, Language language, Rule currentRule) {
        this.lfDecisionTable = lfDecisionTable;
        this.laguage = language;
        setCurrentRule(currentRule);
        this.sb = new ArrayList<>();
        new DecisionTableToPlantUMLActivityDiagramRules().execute(this);
        return sb;
    }

    @Override
    public boolean isConditionTypeIsOccurenceTable() {
        return getCurrentCondition().isOccurencesLink();
    }

    @Override
    public boolean isConditionStateIs() {
        return ((ConditionLink)getCurrentCondition()).getConditionState();
    }

    @Override
    public boolean isCurrentOccEntryIs() {
        return ((ConditionOccurrence)getCurrentCondition().getLinkedModel()).getSymbol().getValue().equals("*");
    }

    @Override
    public boolean isCurrentOccEntryIsLast() {
        ConditionOccurrence conOcc = (ConditionOccurrence) getCurrentCondition().getLinkedModel();
        int indexOffCurrentConditionOcc = conOcc.getCondition().getOccurrences().indexOf(conOcc);
        return indexOffCurrentConditionOcc == conOcc.getCondition().getOccurrences().size() - 1;
    }

    @Override
    public boolean isCurrentOccEntryIsFirst() {
        ConditionOccurrence conOcc = (ConditionOccurrence) getCurrentCondition().getLinkedModel();
        int indexOffCurrentConditionOcc = conOcc.getCondition().getOccurrences().indexOf(conOcc);
        return indexOffCurrentConditionOcc == 0;
    }

    @Override
    public boolean isHasMoreConditions() {
        int indexOfCurrentCondition = getCurrentRule().getConditionLinks().indexOf(getCurrentCondition());
        return indexOfCurrentCondition < (getCurrentRule().getConditionLinks().size() - 1);
    }


    @Override
    public void doNextCondition() {
        int indexOfCurrentCondition = getCurrentRule().getConditionLinks().indexOf(getCurrentCondition());
        setCurrentCondition(getCurrentRule().getConditionLinks().get(indexOfCurrentCondition + 1));
    }


    @Override
    public void doGetFirstCondition() {
        setCurrentCondition(getCurrentRule().getConditionLinks().get(0));
    }

    @Override
    public void doIfWithConditionTitleAndLabelYes() {
        Condition condition = (Condition) getCurrentCondition().getLinkedModel();
        sb.add("if (" + condition.getTitle(getLaguage()).getValue() + ") then (" + getLaguage().getActvityLabelTrue() +")");
    }

    @Override
    public void doElseWithLabelNo() {
        sb.add("else ("+getLaguage().getActivtiyLabelFalse() +")");
    }

    @Override
    public void doElse() {
        sb.add("else");
    }

    @Override
    public void doElseIfConditionTitleWithOccEntryAndLabelYes() {
        ConditionOccurrence conditionOcc = (ConditionOccurrence) getCurrentCondition().getLinkedModel();
        Condition condition = conditionOcc.getCondition();
        sb.add("elseif (" + condition.getTitle(getLaguage()).getValue() + "\\n" + conditionOcc.getSymbol().getValue() + ") then (" + getLaguage().getActvityLabelTrue() + ")");
    }

    @Override
    public void doIfConditionTitleWithOccEntryAndLabelYes() {
        ConditionOccurrence conditionOcc = (ConditionOccurrence) getCurrentCondition().getLinkedModel();
        Condition condition = conditionOcc.getCondition();
        sb.add("if (" + condition.getTitle(getLaguage()).getValue()+ "\\n" + conditionOcc.getSymbol().getValue() + ") then (" + getLaguage().getActvityLabelTrue() + ")");
    }

    @Override
    public void doAddListOfActions() {
        List<AbstractLink<?>> actionLinks = getCurrentRule().getActionLinks();
        for (AbstractLink<?> actionLink : actionLinks) {
            if (actionLink.isOccurencesLink()) {
                String actionOccTitle = ((ActionOccurrence)actionLink.getLinkedModel()).getSymbol().getValue();
                String actionTitle = ((ActionOccurrence)actionLink.getLinkedModel()).getAction().getTitle(getLaguage()).getValue();
                sb.add("-" + actionTitle +"\\n" + actionOccTitle);
            } else {
                String actionTitle = ((Action)actionLink.getLinkedModel()).getTitle(getLaguage()).getValue();
                sb.add("-" + actionTitle);
            }

        }
    }

    @Override
    public void doEndif() {
        sb.add("endif");
    }

    @Override
    public void doNextRule() {
        int indexOf = getLfDecisionTable().getRules().indexOf(getCurrentRule());
        this.sb.addAll(new ActivityDiagramGenerator().generate(getLfDecisionTable(), getLaguage(), getLfDecisionTable().getRules().get(indexOf + 1)));
    }
}
