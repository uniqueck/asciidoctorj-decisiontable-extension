package org.uniqueck.asciidoctorj.extension.decisiontable;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDiagramGeneratorTest {

    @Test
    void testGenerate() throws IOException {
        String generate = new ActivityDiagramGenerator().generate("src/test/resources/smallestDecisionTable.lfet");
        String expectedPlantumlContent = FileUtils.readFileToString(new File("src/test/resources/expectedActivityDiagram_smallestDecisionTable.puml.adoc"), Charset.defaultCharset());
        assertEquals(expectedPlantumlContent, generate);
    }

}