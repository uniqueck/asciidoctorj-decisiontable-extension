package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet;

import com.uniqueck.asciidoctorj.extension.support.AbstractAsciidoctorjExtensionSupportTestHelper;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Getter(AccessLevel.PACKAGE)
class DecisionTableBlockMacroProcessorTest extends AbstractAsciidoctorjExtensionSupportTestHelper {

    @Test
    void process_no_style_defined_Table_style_is_used() {
        String content = convert("dt::smallestDecisionTable.lfet[]");
        assertNotNull(content);
        assertFalse(content.trim().isEmpty());
    }


    @ParameterizedTest
    @ValueSource(strings = {"smallestDecisionTable.lfet", "smallestDecisionTable_German.lfet", "smallestDecisionTableWithActionOcc.lfet", "smallestDecisionTableWithConditionOcc.lfet", "decisionTableWithMultipleConditions.lfet"})
    void process_Table(String lfetFileName) {
        String content = convert("dt::" + lfetFileName + "[style=table]");
        assertNotNull(content);
        assertFalse(content.trim().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"smallestDecisionTable.lfet", "smallestDecisionTable_German.lfet", "smallestDecisionTableWithActionOcc.lfet", "smallestDecisionTableWithConditionOcc.lfet", "decisionTableWithMultipleConditions.lfet"})
    void process_ActivityDiagram(String lfetFileName) {
        String content = convert("dt::" + lfetFileName + "[style=activity_diagram]");
        assertNotNull(content);
        assertFalse(content.trim().isEmpty());
        assertTempDirectoryContainsDirectory("images");
        assertTempDirectoryContainsFile("images/" + lfetFileName + ".png");
    }

}