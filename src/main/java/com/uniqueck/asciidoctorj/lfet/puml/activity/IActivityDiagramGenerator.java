package com.uniqueck.asciidoctorj.lfet.puml.activity;

public interface IActivityDiagramGenerator {

    static IActivityDiagramGenerator newGenerator() {
        return new ActivityDiagramGenerator();
    }

    String generate(String lfetFile);
}
