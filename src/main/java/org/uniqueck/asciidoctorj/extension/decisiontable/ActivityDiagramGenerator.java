package org.uniqueck.asciidoctorj.extension.decisiontable;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.uniqueck.asciidoctorj.extension.decisiontable.lfet.DecisionTableToPlantUMLActivityDiagramRules;
import org.uniqueck.asciidoctorj.extension.decisiontable.lfet.IDecisionTableToPlantUMLActivityDiagram;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
class ActivityDiagramGenerator extends AbstractLFETTraceLogging implements IDecisionTableToPlantUMLActivityDiagram, IActivityDiagramGenerator {

    private List<String> sb;
    private File lfetFile;
    private Document document;
    private Element firstRule;
    private Element currentCondition;
    private Iterator<Element> rulesIt;

    @Override
    public String generate(String lfetFile) {
        this.sb = new ArrayList<>();
        this.lfetFile = new File(lfetFile);
        new DecisionTableToPlantUMLActivityDiagramRules().execute(this);
        return sb.stream().collect(Collectors.joining(System.lineSeparator()));
    }


    @Override
    public boolean isLastConditionOfRule() {
        List<Element> conditionLinks = firstRule.getChildren("ConditionLink");
        return conditionLinks.get(conditionLinks.size()-1).getAttributeValue("link").equals(currentCondition.getAttributeValue("uId"));
    }

    @Override
    public boolean isHasMoreRules() {
        return !getRuleId().equalsIgnoreCase(getLastRuleId());
    }

    @Override
    public boolean isHasNextRuleSameCondition() {
        List<Element> rules = getRootElement().getChild("Rules").getChildren("Rule");
        Optional<Element> founded = rules.stream().filter(r -> r.getAttributeValue("id").equalsIgnoreCase(firstRule.getAttributeValue("id"))).findFirst();
        if (founded.isPresent()) {
            Element nextRule = rules.get(rules.indexOf(founded.get()) + 1);
            List<Element> conditionLink = nextRule.getChildren("ConditionLink");
            return conditionLink.stream().anyMatch(cl -> cl.getAttributeValue("link").equalsIgnoreCase(currentCondition.getAttributeValue("uId")));
        }
        return false;
    }

    private Element getRootElement() {
        return this.document.getRootElement();
    }

    private String getLastRuleId() {
        return getRootElement().getChild("Rules").getAttributeValue("lastId");
    }

    private String getRuleId() {
        return firstRule.getAttributeValue("id");
    }

    @Override
    public void doParseDecisionTable() {
        SAXBuilder builder = new SAXBuilder();
        document = null;
        try {
            document = builder.build(lfetFile);
            this.rulesIt = getRootElement().getChild("Rules").getChildren("Rule").iterator();
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Lesen der XML der LFET", e);
        }

    }

    @Override
    public void doAddPlantumlTag() {
        sb.add("[plantuml, " +  lfetFile.getName() + "]");
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
        // <Title language="English" value="smallestDecisionTable"/>
        String title = getRootElement().getChild("Title").getAttributeValue("value");
        sb.add("title " + title);
        sb.add("");
    }

    @Override
    public void doAddStart() {
        sb.add("start");
    }

    @Override
    public void doGetFirstConditionOfFirstRule() {
        List<Element> conditions = this.document.getRootElement().getChild("Conditions").getChildren("Condition");
        String link2Condition = firstRule.getChildren("ConditionLink").get(0).getAttributeValue("link");
        String conditionState = firstRule.getChildren("ConditionLink").get(0).getAttributeValue("conditionState");
        Optional<Element> founded = conditions.stream().filter(c -> c.getAttributeValue("uId").equals(link2Condition)).findFirst();
        if (founded.isPresent()) {
            currentCondition = founded.get();
            String conditionTitle = currentCondition.getChild("Title").getAttributeValue("value");
            sb.add("if (" + conditionTitle + ") then (" + conditionState + ")");
        }
    }

    @Override
    public void doGetFirstRule() {
        List<Element> rules = getRootElement().getChild("Rules").getChildren("Rule");
        firstRule = rules.get(0);
    }

    @Override
    public void doAddActionsOfRule() {
        List<Element> actionLinks = firstRule.getChildren("ActionLink");
        List<Element> actions = this.document.getRootElement().getChild("Actions").getChildren("Action");
        for (Element eachActionLink : actionLinks) {
            Optional<Element> founded = actions.stream().filter(a -> a.getAttributeValue("uId").equalsIgnoreCase(eachActionLink.getAttributeValue("link"))).findFirst();
            if (founded.isPresent()) {
                sb.add("-" + founded.get().getChild("Title").getAttributeValue("value"));
            }
        }
    }

    @Override
    public void doAddEndif() {
        sb.add("endif");
    }

    @Override
    public void doAddElse() {
        sb.add("else");
    }

    @Override
    public void doNextRule() {
        firstRule = rulesIt.next();
    }


}
