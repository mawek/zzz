package com.mawek.zzz.rest;

import org.springframework.http.ResponseEntity;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Result of calling Zonky Rest API.
 * It wraps status, entity itself and some additional info like paging etc.
 */
public final class ZResponse<T> {

    private static final String PAGE_TOTAL_HEADER = "X-Total";

    private final ResponseEntity<T> responseEntity;
    private final int pagingTotal;

    // only ZRestTemplate can construct new instances - no need for creating creating instances manually for now.
    ZResponse(ResponseEntity<T> responseEntity) {
        notNull(responseEntity, "responseEntity can't be null");

        this.responseEntity = responseEntity;

        if (responseEntity.getHeaders().containsKey(PAGE_TOTAL_HEADER)) {
            pagingTotal = Integer.valueOf(responseEntity.getHeaders().get(PAGE_TOTAL_HEADER).get(0));
        } else {
            pagingTotal = 0;
        }
    }

    public int getHttpStatus() {
        return responseEntity.getStatusCodeValue();
    }

    public T getEntity() {
        return responseEntity.getBody();
    }

    public int getPagingTotal() {
        return pagingTotal;
    }
}
