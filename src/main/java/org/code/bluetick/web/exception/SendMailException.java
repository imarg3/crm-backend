package org.code.bluetick.web.exception;

public class SendMailException extends RuntimeException {
    public SendMailException() {
        super();
    }

    public SendMailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SendMailException(final String message) {
        super(message);
    }

    public SendMailException(final Throwable cause) {
        super(cause);
    }
}



