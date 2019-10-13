package com.uniqueck.asciidoctorj.lfet.puml.activity;

import java.io.File;
import java.util.List;

public interface IActivityDiagramGenerator {

    static IActivityDiagramGenerator newGenerator(File lfetFile) {
        return new ActivityDiagramGenerator(lfetFile);
    }

    List<String> generate();
}
