package com.uniqueck.asciidoctorj.lfet;

import com.uniqueck.asciidoctorj.DecisionTableExtensionRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@Getter(AccessLevel.PACKAGE)
class LFETBlockMacroProcessorTest {

    @TempDir
    File tempDir;

    protected File getBaseDir() {
        return new File("src/test/resources");
    }

    private Asciidoctor asciidoctor;

    protected Options createOptions() {
        return OptionsBuilder.options().baseDir(getBaseDir()).inPlace(true).safe(SafeMode.UNSAFE).backend("html5").toFile(false).destinationDir(tempDir.getAbsoluteFile()).get();
    }

    protected String convert(String content) {
        return convert(content, createOptions());
    }

    protected String convert(String content, Options options) {
        return getAsciidoctor().convert(content, options);
    }

    @BeforeEach
    void setup() {
        asciidoctor = Asciidoctor.Factory.create();
        new DecisionTableExtensionRegistry().register(asciidoctor);
    }


    @ParameterizedTest
    @ValueSource(strings = {"smallestDecisionTable.lfet", "smallestDecisionTable_German.lfet", "smallestDecisionTableWithActionOcc.lfet", "smallestDecisionTableWithConditionOcc.lfet", "decisionTableWithMultipleConditions.lfet"})
    void process(String lfetFileName) {
        String content = convert("lfet::" + lfetFileName + "[style=table]");
        assertNotNull(content);
        assertFalse(content.trim().isEmpty());
    }

}