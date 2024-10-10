package org.hank.exceptions;

public class InvalidCronExpressionException extends Throwable {

    public InvalidCronExpressionException(String message) {
        super(message);
    }
}
