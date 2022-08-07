package io.letsorganize.aboutretry.exception;

import lombok.Getter;

public class RetryException extends RuntimeException {

    public RetryException(String message) {
        super(message);
    }

}
