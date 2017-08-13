package com.mawek.zzz.rest;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.http.client.methods.RequestBuilder.copy;

/**
 * Builder for zonky requests - it wraps logic about setting proper header/query params
 * for available operations (filtering, sortgin, paging,...)
 */
public final class ZRequestBuilder {

    static final String ORDER_HEADER = "X-Order";
    static final String PAGE_INDEX_HEADER = "X-Page";
    static final String PAGE_SIZE_HEADER = "X-Size";

    private final ZRestTemplate zRestTemplate;

    private HttpUriRequest request;
    private List<String> sortFields = newLinkedList();
    private List<NameValuePair> filters = newLinkedList();

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
     * Requests sorting capabilities for this request.
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
     * Requests filtering capabilities for this request. Name of filter field
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

    public ZRequestBuilder setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public ZRequestBuilder setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }


    /**
     * Only positive numbers are accepted. Negative numbers are ignored.
     *
     * @param pageIndex - index of page
     * @param pageSize  - size of page
     * @return this
     */
    public ZRequestBuilder withPaging(int pageIndex, int pageSize) {

        // TODO separate class for paging so no (special)negative index is not necessary ?
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Issues pre-configured request using {@link ZRestTemplate} and and return {@link ZResponse}
     * with deserialized content of requested type.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZRequestBuilder that = (ZRequestBuilder) o;
        return pageIndex == that.pageIndex &&
                pageSize == that.pageSize &&
                Objects.equals(zRestTemplate, that.zRestTemplate) &&
                Objects.equals(request.getMethod(), that.request.getMethod()) &&
                Objects.equals(request.getURI(), that.request.getURI()) &&
                Objects.equals(sortFields, that.sortFields) &&
                Objects.equals(filters, that.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zRestTemplate, request, sortFields, filters, pageIndex, pageSize);
    }
}
