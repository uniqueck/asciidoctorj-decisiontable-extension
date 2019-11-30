package com.uniqueck.asciidoctorj.lfet.puml.activity;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class ActivityDiagramGeneratorTest {

    @ParameterizedTest
    @ValueSource(strings = {"smallestDecisionTable.lfet", "smallestDecisionTable_German.lfet", "smallestDecisionTableWithActionOcc.lfet", "smallestDecisionTableWithConditionOcc.lfet"})
    void testGenerate(String lfetFileName) throws Exception {
        List<String> generate = new ActivityDiagramGenerator(new File("src/test/resources/" +  lfetFileName)).generate();
        String expectedPlantumlContent = FileUtils.readFileToString(new File("src/test/resources/" + lfetFileName + ".activity.puml.adoc"), Charset.defaultCharset());
        assertEquals(expectedPlantumlContent, generate.stream().collect(Collectors.joining(System.lineSeparator())));
    }




}