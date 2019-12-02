package com.uniqueck.asciidoctorj.lfet.puml.activity;

import com.uniqueck.asciidoctorj.lfet.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter(AccessLevel.PACKAGE)
class ActivityDiagramGenerator extends AbstractLFETTraceLogging implements IDecisionTableToPlantUMLActivityDiagram, IActivityDiagramGenerator {

    static final String LINE_SEPARATOR = "\\n";
    static final String PLANTUML_ENDIF = "endif";
    static final String PLANTUML_IF_CONDITION_OCC_TITLE = "if (%s" + LINE_SEPARATOR + "%s) then (%s)";
    static final String PLANTUML_IF_CONDITION_TITLE = "if (%s) then (%s)";
    static final String PLANTUML_ELSEIF_CONDITION_TITLE = "elseif (%s) then (%s)";
    static final String PLANTUML_ELSEIF_CONDITION_OCC_TITLE = "elseif (%s" + LINE_SEPARATOR + "%s) then (%s)";
    static final String PLANTUML_ELSE = "else";
    static final String PLANTUML_ELSE_WITH_LABEL = "else (%s)";
    static final String PLANTUML_ACTION = "-%s";
    static final String PLANTUML_ACTION_OCC = "-%s" + LINE_SEPARATOR + "%s";
    static final String PLANTUML_4TIMES_HYPHEN = "----";
    static final String PLANTUML_STOP = "stop";
    static final String PLANTUML_START = "start";
    static final String PLANTUML_TAG  = "[plantuml, %s]";
    static final String PLANTUML_TITLE = "title %s";

    private List<String> plantUmlContent;
    @Setter(AccessLevel.PACKAGE)
    private Rule currentRule;
    @Setter(AccessLevel.PACKAGE)
    private AbstractLink<?> currentCondition;
    private Language laguage;
    private LFDecisionTable lfDecisionTable;


    @Override
    public List<String> generate(File decisionTableFile) {
        LFDecisionTable tempDecisionTable = null;
        try {
            tempDecisionTable = new Persister().read(LFDecisionTable.class, decisionTableFile);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Lesen der XML der LFET", e);
        }
        Language language = Language.getEnum(tempDecisionTable.getLanguage());
        List<String> content = new ArrayList<>();
        content.add(String.format(PLANTUML_TAG, decisionTableFile.getName()));
        content.add(PLANTUML_4TIMES_HYPHEN);
        content.add(String.format(PLANTUML_TITLE, tempDecisionTable.getTitle(language).getValue()));
        content.add("");
        content.add(PLANTUML_START);
        content.addAll(generate(tempDecisionTable, language, tempDecisionTable.getRules().get(0)));
        content.add(PLANTUML_STOP);
        content.add(PLANTUML_4TIMES_HYPHEN);
        return content;
    }

    List<String> generate(LFDecisionTable lfDecisionTable, Language language, Rule currentRule) {
        this.lfDecisionTable = lfDecisionTable;
        this.laguage = language;
        setCurrentRule(currentRule);
        this.plantUmlContent = new ArrayList<>();
        new DecisionTableToPlantUMLActivityDiagramRules().execute(this);
        return plantUmlContent;
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
        addPlantUmlContent(PLANTUML_IF_CONDITION_TITLE, getConditionTitle(condition), getActivityLabelTrue());
    }

    @Override
    public void doElseWithLabelNo() {
        addPlantUmlContent(PLANTUML_ELSE_WITH_LABEL, getLaguage().getActivtiyLabelFalse());
    }

    @Override
    public void doElse() {
        addPlantUmlContent(PLANTUML_ELSE);
    }

    @Override
    public void doElseIfConditionTitleWithOccEntryAndLabelYes() {
        ConditionOccurrence conditionOcc = (ConditionOccurrence) getCurrentCondition().getLinkedModel();
        Condition condition = conditionOcc.getCondition();
        addPlantUmlContent(PLANTUML_ELSEIF_CONDITION_OCC_TITLE, getConditionTitle(condition), getConditionOccSymbol(conditionOcc), getActivityLabelTrue());
    }

    @Override
    public void doIfConditionTitleWithOccEntryAndLabelYes() {
        ConditionOccurrence conditionOcc = (ConditionOccurrence) getCurrentCondition().getLinkedModel();
        Condition condition = conditionOcc.getCondition();
        addPlantUmlContent(PLANTUML_IF_CONDITION_OCC_TITLE, getConditionTitle(condition), getConditionOccSymbol(conditionOcc), getActivityLabelTrue());
    }

    @Override
    public void doAddListOfActions() {
        List<AbstractLink<?>> actionLinks = getCurrentRule().getActionLinks();
        for (AbstractLink<?> actionLink : actionLinks) {
            if (actionLink.isOccurencesLink()) {
                String actionOccTitle = ((ActionOccurrence)actionLink.getLinkedModel()).getSymbol().getValue();
                String actionTitle = ((ActionOccurrence)actionLink.getLinkedModel()).getAction().getTitle(getLaguage()).getValue();
                addPlantUmlContent(PLANTUML_ACTION_OCC, actionTitle, actionOccTitle);
            } else {
                String actionTitle = ((Action)actionLink.getLinkedModel()).getTitle(getLaguage()).getValue();
                addPlantUmlContent(PLANTUML_ACTION, actionTitle);
            }

        }
    }

    @Override
    public void doEndif() {
        addPlantUmlContent(PLANTUML_ENDIF);
    }

    @Override
    public void doNextRule() {
        int indexOf = getLfDecisionTable().getRules().indexOf(getCurrentRule());
        getPlantUmlContent().addAll(new ActivityDiagramGenerator().generate(getLfDecisionTable(), getLaguage(), getLfDecisionTable().getRules().get(indexOf + 1)));
    }

    private void addPlantUmlContent(String formatString, String... args) {
        getPlantUmlContent().add(String.format(formatString, (Object[]) args));
    }

    private String getConditionTitle(Condition condition) {
        return condition.getTitle(getLaguage()).getValue();
    }

    private String getConditionOccSymbol(ConditionOccurrence conditionOccurrence) {
        return conditionOccurrence.getSymbol().getValue();
    }

    private String getActivityLabelTrue() {
        return getLaguage().getActvityLabelTrue();
    }
}
