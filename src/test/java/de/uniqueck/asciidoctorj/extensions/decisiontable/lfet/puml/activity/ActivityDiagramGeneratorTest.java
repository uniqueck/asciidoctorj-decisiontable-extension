package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.puml.activity;

import de.uniqueck.asciidoctorj.extensions.decisiontable.exceptions.AsciiDoctorDecisionTableRuntimeException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityDiagramGeneratorTest {

    @ParameterizedTest
    @ValueSource(strings = {"decisionTableWithTwoConditions.lfet","smallestDecisionTable.lfet", "smallestDecisionTable_German.lfet", "smallestDecisionTableWithActionOcc.lfet", "smallestDecisionTableWithConditionOcc.lfet"})
    void testGenerate(String lfetFileName) throws Exception {
        List<String> generate = new ActivityDiagramGenerator().generate(new File("src/test/resources/" +  lfetFileName));
        String expectedPlantumlContent = FileUtils.readFileToString(new File("src/test/resources/" + lfetFileName + ".activity.puml.adoc"), Charset.defaultCharset());
        assertEquals(expectedPlantumlContent, generate.stream().collect(Collectors.joining(System.lineSeparator())));
    }

    @DisplayName("Read corrupted xml file")
    @Test
    void testGenerate_ReadCorruptXMLFile() {
        AsciiDoctorDecisionTableRuntimeException exception = Assertions.assertThrows(AsciiDoctorDecisionTableRuntimeException.class, () -> new ActivityDiagramGenerator().generate(new File("src/test/resources/corrupted.lfet")));
        assertEquals("Error on reading decision table file 'src/test/resources/corrupted.lfet'",exception.getMessage());
    }

}