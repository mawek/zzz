package com.mawek.zzz.rest;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.http.client.methods.RequestBuilder.copy;

/**
 * Builder for Zonky requests - it wraps logic of setting proper headers/query params
 * for available operations (filtering, sorting, paging,...)
 */
public final class ZRequestBuilder {

    static final String ORDER_HEADER = "X-Order";
    static final String PAGE_INDEX_HEADER = "X-Page";
    static final String PAGE_SIZE_HEADER = "X-Size";

    private final ZRestTemplate zRestTemplate;

    private final HttpUriRequest request;
    private final List<String> sortFields = newLinkedList();
    private final List<NameValuePair> filters = newLinkedList();

    private int pageIndex = 0;
    private int pageSize = 100;

    // only ZRestTemplate can construct new instances - no need for creating creating instances manually for now.
    ZRequestBuilder(ZRestTemplate zRestTemplate, HttpUriRequest request) {
        notNull(zRestTemplate, "zRestTemplate can't be null");
        notNull(request, "request can't be null");

        this.zRestTemplate = zRestTemplate;
        this.request = request;
    }

    // TODO test null

    /**
     * Add sorting capabilities for this request.
     * Can be called multiple times - each call adds another sort field on top of previously added sort fields.
     * <p>
     *
     * @param sortField - field for sorting (empty sort fields are ignored; repeatable fields are replaced)
     * @return this
     */
    public ZRequestBuilder addSortField(String sortField) {
        if (!isEmpty(sortField)) {
            this.sortFields.add(sortField);
        }
        return this;
    }

    /**
     * Add filtering capabilities for this request. Name of filter field
     * should be a result of {@link com.mawek.zzz.model.Loan.FilterableField#getFieldFilter(FilterOperation)}.
     * <p>
     *
     * @param name  - filter field name for filtering (empty values are ignored)
     * @param value - field filter value for filtering (repeatable values are replaced)
     * @return this
     */
    public ZRequestBuilder addFilterField(String name, String value) {
        if (isEmpty(name)) {
            return this;
        }

        // remove duplicates
        this.filters.removeIf(pair -> name.equals(pair.getName()));
        this.filters.add(new BasicNameValuePair(name, value));

        return this;
    }

    /**
     * Add page index (for pageable resources)
     */
    public ZRequestBuilder setPageIndex(int pageIndex) {
        isTrue(pageIndex >= 0, "pageIndex can't be negative number");
        this.pageIndex = pageIndex;
        return this;
    }

    /**
     * Add page size (for pageable resources)
     */
    public ZRequestBuilder setPageSize(int pageSize) {
        isTrue(pageSize >= 0, "pageSize can't be negative number");

        this.pageSize = pageSize;
        return this;
    }

    /**
     * Trigers pre-configured request and return {@link ZResponse} with deserialized content of requested type.
     *
     * @param clz - type of response entity
     * @return response of rest api call
     */
    public <T> ZResponse<T> execute(Class<T> clz) {
        // create copy of request so we don't tamper with original request (just in case,..)
        final RequestBuilder requestCopy = copy(request);

        if (!sortFields.isEmpty()) {
            requestCopy.addHeader(ORDER_HEADER, String.join(",", sortFields));
        }

        if (pageIndex >= 0) {
            requestCopy.addHeader(PAGE_INDEX_HEADER, String.valueOf(pageIndex));
        }

        if (pageSize > 0) {
            requestCopy.addHeader(PAGE_SIZE_HEADER, String.valueOf(pageSize));
        }

        filters.forEach(filter -> requestCopy.addParameter(filter.getName(), filter.getValue()));

        return zRestTemplate.execute(requestCopy.build(), clz);
    }
}
