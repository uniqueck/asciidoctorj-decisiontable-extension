package org.uniqueck.asciidoctorj.extension.decisiontable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class AbstractLFETTraceLogging {


    public void doTrace(String dtName, String version, int rules, int rule) {
        log.debug("doTrace({}.{} - {} / {})", dtName, version, rule, rules);
    }

    public void doTraceAfterRule(String dtName, String version, int rules, int rule) {
        log.debug("doTraceAfterRule({}.{} - {} / {})", dtName, version, rule, rules);
    }
}
