package com.mawek.zzz.rest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Common exception signaling error communicating with Zonky Rest API.
 */
public class ZRestException extends ZRuntimeException {

    private final HttpUriRequest request;
    private final Integer responseStatusCode;

    public ZRestException(HttpUriRequest request, String message, Throwable cause) {
        super(message, cause);

        this.request = request;
        this.responseStatusCode = null;
    }

    public ZRestException(HttpUriRequest request, int responseStatusCode, String message) {
        super(message);

        this.request = request;
        this.responseStatusCode = responseStatusCode;
    }

    /**
     * Return returned http status code. Can be null (if response isn't available at all for example)
     */
    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    /**
     * Return request URI.
     */
    public String getRequestUri() {
        return request.getURI().toString();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .append("error message", getLocalizedMessage())
                .append("request method", request.getMethod())
                .append("request uri", request.getURI());

        if (responseStatusCode != null) {
            builder.append("response status", responseStatusCode);
        }

        builder.append("root cause", getCause().getLocalizedMessage());


        return builder.build();
    }
}
