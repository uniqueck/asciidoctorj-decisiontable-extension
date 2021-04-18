package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.puml.activity;

import de.uniqueck.asciidoctorj.extensions.decisiontable.exceptions.AsciiDoctorDecisionTableRuntimeException;
import de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model.*;
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
    private IConditionEntryLink currentCondition;
    private Language laguage;
    private LFDecisionTable lfDecisionTable;


    @Override
    public List<String> generate(File decisionTableFile) {
        LFDecisionTable tempDecisionTable = null;
        try {
            tempDecisionTable = new Persister().read(LFDecisionTable.class, decisionTableFile);
        } catch (Exception e) {
            throw new AsciiDoctorDecisionTableRuntimeException("Error on reading decision table file '"+ decisionTableFile.getPath()+"'", e);
        }
        Language language = Language.getEnum(tempDecisionTable.getLanguage());
        List<String> content = new ArrayList<>();
        content.add(String.format(PLANTUML_TAG, decisionTableFile.getName()));
        content.add(PLANTUML_4TIMES_HYPHEN);
        content.add(String.format(PLANTUML_TITLE, tempDecisionTable.getTitle(language).getValue()));
        content.add("");
        content.add(PLANTUML_START);
        content.addAll(generate(tempDecisionTable, language, tempDecisionTable.getRules().get(0), null));
        content.add(PLANTUML_STOP);
        content.add(PLANTUML_4TIMES_HYPHEN);
        return content;
    }

    List<String> generate(LFDecisionTable lfDecisionTable, Language language, Rule currentRule, IConditionEntryLink currentCondition) {
        this.lfDecisionTable = lfDecisionTable;
        this.laguage = language;
        setCurrentRule(currentRule);
        setCurrentCondition(currentCondition);
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
        return ((IConditionOccurrenceLink)getCurrentCondition()).getSymbol().equals("*");
    }

    @Override
    public boolean isCurrentOccEntryIsLast() {
        return ((IConditionOccurrenceLink)getCurrentCondition()).isLastOccurrence();
    }

    @Override
    public boolean isCurrentOccEntryIsFirst() {
        return ((IConditionOccurrenceLink)getCurrentCondition()).isFirstOccurrence();
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
    public void doIfWithConditionTitleAndLabelYes() {
        Condition condition = getCurrentCondition().getCondition();
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
        Condition condition = getCurrentCondition().getCondition();
        addPlantUmlContent(PLANTUML_ELSEIF_CONDITION_OCC_TITLE, getConditionTitle(condition), ((IConditionOccurrenceLink)getCurrentCondition()).getSymbol(), getActivityLabelTrue());
    }

    @Override
    public void doIfConditionTitleWithOccEntryAndLabelYes() {
        Condition condition = getCurrentCondition().getCondition();
        addPlantUmlContent(PLANTUML_IF_CONDITION_OCC_TITLE, getConditionTitle(condition), ((IConditionOccurrenceLink)getCurrentCondition()).getSymbol(), getActivityLabelTrue());
    }

    @Override
    public void doAddListOfActions() {
        List<IActionEntryLink> actionLinks = getLfDecisionTable().getSortedListOfActionLinksBasedOnActions(getCurrentRule().getActionLinks());
        for (IActionEntryLink actionLink : actionLinks) {
            if (actionLink.isOccurencesLink()) {
                String actionOccTitle = ((ActionOccurrenceLink)actionLink).getSymbol();
                String actionTitle = actionLink.getAction().getTitle(getLaguage()).getValue();
                addPlantUmlContent(PLANTUML_ACTION_OCC, actionTitle, actionOccTitle);
            } else {
                String actionTitle = actionLink.getAction().getTitle(getLaguage()).getValue();
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
        getPlantUmlContent().addAll(new ActivityDiagramGenerator().generate(getLfDecisionTable(), getLaguage(), getLfDecisionTable().getRules().get(indexOf + 1), getCurrentCondition()));
    }

    private void addPlantUmlContent(String formatString, String... args) {
        getPlantUmlContent().add(String.format(formatString, (Object[]) args));
    }

    private String getConditionTitle(Condition condition) {
        return condition.getTitle(getLaguage()).getValue();
    }

    private String getActivityLabelTrue() {
        return getLaguage().getActvityLabelTrue();
    }

    @Override
    public boolean isConditionIsAlreadySet() {
        return getCurrentCondition() != null;
    }

    @Override
    public void doSetCondition_FCR() {
        setCurrentCondition(getCurrentRule().getConditionLinks().get(0));
    }

    @Override
    public void doSetCondition_CCE() {
        setCurrentCondition(getCurrentRule().getConditionLinkBasedOnCondition(getCurrentCondition().getCondition()));
    }
}
