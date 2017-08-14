package com.mawek.zzz.service;

/**
 * Error thrown when procesing loans.
 */
public class LoanProcessorException extends RuntimeException {

    public LoanProcessorException(String message, Throwable t) {
        super(message, t);
    }
}
