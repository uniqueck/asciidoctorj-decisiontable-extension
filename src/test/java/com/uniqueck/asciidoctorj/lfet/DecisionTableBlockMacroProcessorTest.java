package com.uniqueck.asciidoctorj.lfet;

import com.uniqueck.asciidoctorj.DecisionTableExtensionRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.asciidoctor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Getter(AccessLevel.PACKAGE)
class DecisionTableBlockMacroProcessorTest {

    @TempDir
    File tempDir;

    protected File getBaseDir() {
        return new File("src/test/resources");
    }

    private Asciidoctor asciidoctor;

    protected Options createOptions() {
        return OptionsBuilder.options().baseDir(tempDir).inPlace(true).safe(SafeMode.UNSAFE).backend("html5").toFile(false).destinationDir(tempDir.getAbsoluteFile()).attributes(AttributesBuilder.attributes().attribute(Attributes.IMAGESDIR, new File(tempDir, "images").getAbsolutePath())).get();
    }

    protected String convert(String content) {
        return convert(content, createOptions());
    }

    protected String convert(String content, Options options) {
        return getAsciidoctor().convert(content, options);
    }

    @BeforeEach
    void setup() throws IOException {
        asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.requireLibrary("asciidoctor-diagram");
        FileUtils.copyToDirectory(FileUtils.listFiles(new File("src/test/resources"), new String[] {"lfet"}, false),tempDir);
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
        assertTrue(new File(tempDir, "images/" + lfetFileName + ".png").exists());
    }

}