package com.uniqueck.asciidoctorj.lfet.puml.activity;

import com.uniqueck.asciidoctorj.lfet.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Getter(AccessLevel.PACKAGE)
class ActivityDiagramGenerator extends AbstractLFETTraceLogging implements IDecisionTableToPlantUMLActivityDiagram, IActivityDiagramGenerator {

    private List<String> sb;
    private File lfetFile;
    private Rule currentRule;
    private AbstractLink<?> currentCondition;
    private Iterator<Rule> rulesIt;
    private Language laguage;
    private LFDecisionTable lfDecisionTable;

    ActivityDiagramGenerator(File lfetFile) {
        this.lfetFile = lfetFile;
    }

    @Override
    public List<String> generate() {
        this.sb = new ArrayList<>();
        new DecisionTableToPlantUMLActivityDiagramRules().execute(this);
        return sb;
    }

    @Override
    public void doExtractLanguage() {
        this.laguage = Language.valueOf(getLfDecisionTable().getLanguage());
    }



    @Override
    public boolean isLastConditionOfRule() {
        AbstractLink<?> abstractConditionLink = getCurrentRule().getConditionLinks().get(getCurrentRule().getConditionLinks().size() - 1);
        if (abstractConditionLink.isOccurencesLink()) {
            ConditionOccurrenceLink link = (ConditionOccurrenceLink) abstractConditionLink;
            return getCurrentCondition().equals(link.getLinkedModel().getCondition());
        } else {
            ConditionLink link = (ConditionLink) abstractConditionLink;
            return getCurrentCondition().getLinkedModel().equals(link.getLinkedModel());
        }
    }

    @Override
    public boolean isHasMoreRules() {
        return getRulesIt().hasNext();
    }

    @Override
    public boolean isHasNextRuleSameCondition() {
        int indexOfCurrentRule = getLfDecisionTable().getRules().indexOf(getCurrentRule());
        Rule nextRule = getLfDecisionTable().getRules().get(indexOfCurrentRule + 1);
        int indexOfCurrentCondition = getCurrentRule().getConditionLinks().indexOf(getCurrentCondition());
        AbstractLink<?> conditionOfNextRule = nextRule.getConditionLinks().get(indexOfCurrentCondition);
        if (getCurrentCondition().isOccurencesLink() ) {
            return ((ConditionOccurrence)getCurrentCondition().getLinkedModel()).getCondition().equals(((ConditionOccurrence)conditionOfNextRule.getLinkedModel()).getCondition());
        } else {
            return getCurrentCondition().getLinkedModel().equals(conditionOfNextRule.getLinkedModel());
        }

    }



    @Override
    public void doParseDecisionTable() {
        try {
            lfDecisionTable = new Persister().read(LFDecisionTable.class, this.lfetFile);
            rulesIt = getLfDecisionTable().getRules().iterator();
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Lesen der XML der LFET", e);
        }
    }

    @Override
    public void doAddPlantumlTag() {
        sb.add("[plantuml, " +  getLfetFile().getName() + "]");
    }

    @Override
    public void doAddStop() {
        sb.add("stop");
    }

    @Override
    public void doAdd4TimesHyphen() {
        sb.add("----");
    }

    @Override
    public void doAddTitle() {
        sb.add("title " + getLfDecisionTable().getTitle().getValue());
        sb.add("");
    }

    @Override
    public void doAddStart() {
        sb.add("start");
    }

    @Override
    public void doGetFirstConditionOfFirstRule() {
        this.currentCondition = getCurrentRule().getConditionLinks().get(0);
        if (!this.currentCondition.isOccurencesLink()) {
            boolean conditionState = ((ConditionLink)this.currentCondition).getConditionState();
            String conditionTitle = ((ConditionLink)currentCondition).getLinkedModel().getTitle().getValue();
            sb.add("if (" + conditionTitle + ") then (" + getLaguage().getActivtyLabel(conditionState)+ ")");
        } else {
            String conditionTitle = ((ConditionOccurrence)currentCondition.getLinkedModel()).getCondition().getTitle().getValue();
            String conditionOccTitle = ((ConditionOccurrence)currentCondition.getLinkedModel()).getTitle().getValue();
            sb.add("if (" + conditionTitle + "\\n" + conditionOccTitle + ") then (" + getLaguage().getActvityLabelTrue()+ ")");
        }



    }

    @Override
    public void doGetFirstRule() {
        currentRule = getRulesIt().next();
    }

    @Override
    public void doAddActionsOfRule() {
        List<AbstractLink<?>> actionLinks = getCurrentRule().getActionLinks();
        for (AbstractLink<?> actionLink : actionLinks) {
            if (actionLink.isOccurencesLink()) {
                String actionOccTitle = ((ActionOccurrence)actionLink.getLinkedModel()).getSymbol().getValue();
                String actionTitle = ((ActionOccurrence)actionLink.getLinkedModel()).getAction().getTitle().getValue();
                sb.add("-" + actionTitle +"\\n" + actionOccTitle);
            } else {
                String actionTitle = ((Action)actionLink.getLinkedModel()).getTitle().getValue();
                sb.add("-" + actionTitle);
            }

        }
    }

    @Override
    public void doAddEndif() {
        sb.add("endif");
    }

    @Override
    public void doAddElse() {
        sb.add("else ("+getLaguage().getActivtiyLabelFalse() +")");
    }

    @Override
    public void doNextRule() {
        currentRule = rulesIt.next();
    }



}
