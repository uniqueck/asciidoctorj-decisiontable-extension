package com.uniqueck.asciidoctorj.lfet.puml.activity;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConditionDiagramGeneratorTest {


    private Element parseLFET(File lfetFile) {
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try {
            document = builder.build(lfetFile);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Lesen der XML der LFET", e);
        }
        return document.getRootElement();
    }

    /**
     * @startuml
     * if (Condition1) then (yes)
     * -Action1
     * endif
     * @enduml
     */
    @Test
    void testGenerate() {

        Element parseLFET = parseLFET(new File("src/test/resources/smallestDecisionTable.lfet"));
        IConditionDiagramGenerator underTest = IConditionDiagramGenerator.newInstance(parseLFET, parseLFET.getChild("Rules").getChildren("Rule").get(0), parseLFET.getChild("Conditions").getChildren("Condition").get(0));
        List<String> generate = underTest.generate();
        assertNotNull(generate);
        assertFalse(generate.isEmpty());
        assertEquals(3, generate.size());

        assertEquals("if (Condition1) then (yes)", generate.get(0));
        assertEquals("-Action1", generate.get(1));
        assertEquals("endif", generate.get(2));

    }

}