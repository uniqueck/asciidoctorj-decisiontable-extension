package com.uniqueck.asciidoctorj.lfet.puml.activity;

import com.uniqueck.asciidoctorj.lfet.puml.activity.ActivityDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDiagramGeneratorTest {

    @Test
    void testGenerate() throws IOException {
        List<String> generate = new ActivityDiagramGenerator(new File("src/test/resources/smallestDecisionTable.lfet")).generate();
        String expectedPlantumlContent = FileUtils.readFileToString(new File("src/test/resources/expectedActivityDiagram_smallestDecisionTable.puml.adoc"), Charset.defaultCharset());
        assertEquals(expectedPlantumlContent, generate.stream().collect(Collectors.joining(System.lineSeparator())));
    }

}