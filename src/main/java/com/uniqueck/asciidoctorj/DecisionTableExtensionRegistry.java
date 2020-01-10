package com.uniqueck.asciidoctorj;

import com.uniqueck.asciidoctorj.lfet.DecisionTableBlockMacroProcessor;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

public class DecisionTableExtensionRegistry implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();
        javaExtensionRegistry.blockMacro(DecisionTableBlockMacroProcessor.class);
    }
}
