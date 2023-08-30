package org.code.bluetick.web.exception;

public class EmailExistsException extends Throwable {
    public EmailExistsException(final String message) {
        super(message);
    }
}
