package com.uniqueck.asciidoctorj.lfet;

import com.uniqueck.asciidoctorj.exceptions.AsciiDoctorDecisionTableRuntimeException;
import com.uniqueck.asciidoctorj.lfet.puml.activity.IActivityDiagramGenerator;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockMacroProcessor;
import org.asciidoctor.extension.Name;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Name("dt")
public class DecisionTableBlockMacroProcessor extends BlockMacroProcessor {

    enum Style {
        UNKNOWN, TABLE, ACTIVITY_DIAGRAM;

        static Style get(String value) {
            for (Style each : values()) {
                if (each.name().equalsIgnoreCase(value)) {
                    return each;
                }
            }
            return UNKNOWN;
        }

        public boolean isValid() {
            return !UNKNOWN.equals(this);
        }
    }

    @Override
    public Object process(StructuralNode parent, String target, Map<String, Object> attributes) {

        List<String> content = new ArrayList<>();

        File decisionTableFile = new File(getAttribute(parent, "docdir", ""), target);

        switch (getStyle(attributes)) {
            case TABLE:
                content.addAll(new LFETAsciiDocTableGenerator(decisionTableFile).generate());
                break;
            case ACTIVITY_DIAGRAM:
                content.addAll(IActivityDiagramGenerator.newGenerator().generate(decisionTableFile));
                break;
            default:
                throw new AsciiDoctorDecisionTableRuntimeException("style '" + getStyle(attributes) + "' is not supported");
        }

        parseContent(parent, content);
        return null;
    }

    private String getAttribute(StructuralNode structuralNode, String attributeName, String defaultValue) {
        String value = (String) structuralNode.getAttribute(attributeName);

        if (value == null || value.trim().isEmpty()) {
            value = defaultValue;
        }

        return value;
    }

    private Style getStyle(Map<String, Object> attributes) {
        return Style.get((String) attributes.getOrDefault("style", Style.TABLE.name()));
    }
}
