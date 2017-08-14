package com.mawek.zzz.service.processor;

/**
 * Error thrown when processing loans.
 */
public class LoanProcessorException extends RuntimeException {

    public LoanProcessorException(String message, Throwable t) {
        super(message, t);
    }
}
