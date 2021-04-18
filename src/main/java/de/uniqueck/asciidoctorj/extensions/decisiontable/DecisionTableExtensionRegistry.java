package de.uniqueck.asciidoctorj.extensions.decisiontable;

import com.uniqueck.asciidoctorj.extension.support.AbstractAsciidoctorjExtensionRegistry;
import de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.DecisionTableBlockMacroProcessor;
import org.asciidoctor.extension.JavaExtensionRegistry;

public class DecisionTableExtensionRegistry extends AbstractAsciidoctorjExtensionRegistry {

    @Override
    protected void registerExtension(JavaExtensionRegistry javaExtensionRegistry) {
        javaExtensionRegistry.blockMacro(DecisionTableBlockMacroProcessor.class);
    }

}
