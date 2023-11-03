package org.code.bluetick.web.exception;

public final class LeadNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public LeadNotFoundException() {
        super();
    }

    public LeadNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LeadNotFoundException(final String message) {
        super(message);
    }

    public LeadNotFoundException(final Throwable cause) {
        super(cause);
    }

}
