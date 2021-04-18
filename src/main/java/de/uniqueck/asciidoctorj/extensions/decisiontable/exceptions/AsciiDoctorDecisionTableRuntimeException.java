package de.uniqueck.asciidoctorj.extensions.decisiontable.exceptions;

public class AsciiDoctorDecisionTableRuntimeException extends RuntimeException {

    public AsciiDoctorDecisionTableRuntimeException(String message) {
        super(message);
    }

    public AsciiDoctorDecisionTableRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
