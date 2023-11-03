package org.code.bluetick.web.exception;

public final class LeadAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287263L;

    public LeadAlreadyExistException() {
        super();
    }

    public LeadAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LeadAlreadyExistException(final String message) {
        super(message);
    }

    public LeadAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
