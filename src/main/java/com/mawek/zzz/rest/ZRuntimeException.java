package com.mawek.zzz.rest;

/**
 * Common Zonky exception.
 */
public class ZRuntimeException extends RuntimeException {

    public ZRuntimeException(String message, Throwable t) {
        super(message, t);
    }
}
