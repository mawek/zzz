package com.mawek.zzz.rest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Common exception signaling error communicating with Zonky Rest API.
 */
public class ZRestException extends ZRuntimeException {

    private final HttpUriRequest request;

    public ZRestException(HttpUriRequest request, String message, Throwable cause) {
        super(message, cause);

        this.request = request;
    }

    /**
     * Return request URI.
     */
    public String getRequestUri() {
        return request.getURI().toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("error message", getLocalizedMessage())
                .append("request method", request.getMethod())
                .append("request uri", request.getURI())
                .append("root cause", super.toString())
                .build();
    }
}
