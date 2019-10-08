package org.uniqueck.asciidoctorj.extension.decisiontable;

import org.jdom2.Element;

import java.util.List;

public interface IConditionDiagramGenerator {

    static IConditionDiagramGenerator newInstance(Element rootElement, Element rule, Element condition) {
        return new ConditionDiagramGenerator(rootElement, rule, condition);
    }

    List<String> generate();
}
