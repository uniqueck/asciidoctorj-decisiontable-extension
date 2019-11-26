package com.uniqueck.asciidoctorj.lfet.puml.activity;

import com.uniqueck.asciidoctorj.lfet.model.AbstractLink;
import com.uniqueck.asciidoctorj.lfet.model.LFDecisionTable;
import com.uniqueck.asciidoctorj.lfet.model.Rule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.simpleframework.xml.core.Persist;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Getter(AccessLevel.PACKAGE)
class ActivityDiagramGenerator extends AbstractLFETTraceLogging implements IDecisionTableToPlantUMLActivityDiagram, IActivityDiagramGenerator {

    private List<String> sb;
    private File lfetFile;
    private Document document;
    private Rule currentRule;
    private Element currentCondition;
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
        this.laguage = Language.valueOf(getRootElement().getAttributeValue("language"));
    }


    private List<String> getConditionLinksOrConditionsOccurencesLinks(Element rule) {
        return rule.getChildren().stream().filter(e -> e.getName().equals("ConditionLink") || e.getName().equals("ConditionOccurrenceLink")).map(e -> e.getAttributeValue("link")).collect(Collectors.toList());
    }

    @Override
    public boolean isLastConditionOfRule() {
        AbstractLink<?> abstractConditionLink = getCurrentRule().getConditionLinks().get(getCurrentRule().getConditionLinks().size());
//        if (abstractConditionLink.)
//        List<String> conditionLinks = getConditionLinksOrConditionsOccurencesLinks(getFirstRule());
//        return conditionLinks.get(conditionLinks.size()-1).equals(getCurrentCondition().getAttributeValue("uId"));
        return false;
    }

    @Override
    public boolean isHasMoreRules() {
        return getRulesIt().hasNext();
    }

    @Override
    public boolean isHasNextRuleSameCondition() {
//        List<Element> rules = getRootElement().getChild("Rules").getChildren("Rule");
//        Optional<Element> founded = rules.stream().filter(r -> r.getAttributeValue("id").equalsIgnoreCase(firstRule.getAttributeValue("id"))).findFirst();
//        if (founded.isPresent()) {
//            Element nextRule = rules.get(rules.indexOf(founded.get()) + 1);
//            List<Element> conditionLink = nextRule.getChildren("ConditionLink");
//            return conditionLink.stream().anyMatch(cl -> cl.getAttributeValue("link").equalsIgnoreCase(currentCondition.getAttributeValue("uId")));
//        }
        return false;
    }

    private Element getRootElement() {
        return this.document.getRootElement();
    }

    private String getLastRuleId() {
        return getRootElement().getChild("Rules").getAttributeValue("lastId");
    }

    private String getRuleId() {

//        return firstRule.getAttributeValue("id");
            return "";
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
//        List<Element> conditions = getConditions();
//        List<Element> conditionsOfFirstRule = getFirstRule().getChildren().stream().filter(e -> e.getName().equals("ConditionLink") || e.getName().equals("ConditionOccurrenceLink")).collect(Collectors.toList());
//        Element conditionOrOccLink = conditionsOfFirstRule.get(0);
//        String link2Condition = conditionOrOccLink.getAttributeValue("link");
//        if (conditionOrOccLink.getName().equals("ConditionLink")) {
//            boolean conditionState = Boolean.parseBoolean(conditionOrOccLink.getAttributeValue("conditionState"));
//            Optional<Element> founded = conditions.stream().filter(c -> c.getAttributeValue("uId").equals(link2Condition)).findFirst();
//            if (founded.isPresent()) {
//                currentCondition = founded.get();
//                String conditionTitle = currentCondition.getChild("Title").getAttributeValue("value");
//                sb.add("if (" + conditionTitle + ") then (" + getLaguage().getActivtyLabel(conditionState)+ ")");
//            }
//        } else {
//            Optional<Element> founded = conditions.stream().filter(c -> c.getChild("ConditionOccurrences") != null).flatMap(c -> c.getChild("ConditionOccurrences").getChildren("ConditionOccurrence").stream()).filter(c -> c.getAttributeValue("uId").equals(link2Condition)).findFirst();
//            if (founded.isPresent()) {
//                currentCondition = founded.get();
//                String conditionTitle = currentCondition.getParentElement().getParentElement().getChild("Title").getAttributeValue("value");
//                String conditionOccTitle = currentCondition.getChild("Symbol").getAttributeValue("value");
//                sb.add("if (" + conditionTitle + "\\n" + conditionOccTitle + ") then (" + getLaguage().getActvityLabelTrue()+ ")");
//            }
//        }



    }

    @Override
    public void doGetFirstRule() {
        currentRule = rulesIt.next();
    }

    @Override
    public void doAddActionsOfRule() {
//        List<Element> actions = this.document.getRootElement().getChild("Actions").getChildren("Action");
//        for (Element eachElement : firstRule.getChildren()) {
//            if (eachElement.getName().equals("ActionLink")) {
//                Optional<Element> founded = actions.stream().filter(a -> a.getAttributeValue("uId").equalsIgnoreCase(eachElement.getAttributeValue("link"))).findFirst();
//                founded.ifPresent(element -> sb.add("-" + element.getChild("Title").getAttributeValue("value")));
//            } else if (eachElement.getName().equals("ActionOccurrenceLink")) {
//                String link = eachElement.getAttributeValue("link");
//                Optional<Element> optionalElement = actions.stream().filter(a -> a.getChild("ActionOccurrences") != null).flatMap(a -> a.getChild("ActionOccurrences").getChildren("ActionOccurrence").stream()).filter(a -> a.getAttributeValue("uId").equals(link)).findFirst();
//                if (optionalElement.isPresent()) {
//                    // TODO ggf. auch nur Text von ActionOcc als Anzeige verwenden
//                    String actionOccTitle = optionalElement.get().getChild("Symbol").getAttributeValue("value");
//                    String actionTitle = optionalElement.get().getParentElement().getParentElement().getChild("Title").getAttributeValue("value");
//                    sb.add("-" + actionTitle +"\\n" + actionOccTitle);
//                }
//            }
//        }
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

    private List<Element> getConditions() {
        return getRootElement().getChild("Conditions").getChildren("Condition");
    }


}
