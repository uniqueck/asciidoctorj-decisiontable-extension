package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.puml.activity;

import java.io.File;
import java.util.List;

public interface IActivityDiagramGenerator {

    static IActivityDiagramGenerator newGenerator() {
        return new ActivityDiagramGenerator();
    }

    List<String> generate(File decisionTableFile);
}
