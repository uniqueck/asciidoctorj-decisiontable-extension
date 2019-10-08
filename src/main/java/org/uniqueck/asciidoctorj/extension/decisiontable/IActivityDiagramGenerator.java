package org.uniqueck.asciidoctorj.extension.decisiontable;

public interface IActivityDiagramGenerator {

    static IActivityDiagramGenerator newGenerator() {
        return new ActivityDiagramGenerator();
    }

    String generate(String lfetFile);
}
