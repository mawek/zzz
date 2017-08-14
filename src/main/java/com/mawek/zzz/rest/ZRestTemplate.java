package com.mawek.zzz.rest;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Synchronous rest helper for calling Zonky Rest API.
 */
@Component
public final class ZRestTemplate {

    private final RestTemplate restTemplate;
    private final String zHostName;

    @Autowired
    public ZRestTemplate(RestTemplate restTemplate, @Value("${rest.zHostName}") String zHostName) {
        notNull(restTemplate, "restTemplate can't be null");
        notEmpty(zHostName, "zHostName can't be null");

        this.restTemplate = restTemplate;
        this.zHostName = zHostName;
    }

    /**
     * Create GET request for Zonky, additional request configuration is performed on returned {@link ZRequestBuilder}.
     *
     * @param url - url to be requested (can't be empty)
     * @return request builder for additional configuration of request (like sorting, filtering,...)
     */
    public ZRequestBuilder createGet(String url) {
        notEmpty(url, "url can't be empty");

        return new ZRequestBuilder(this, new HttpGet(zHostName + url));
    }

    /*
    * Fire http request, wait for for response and return it.
    * */
    <T> ZResponse<T> execute(HttpUriRequest request, Class<T> clz) {
        final HttpHeaders headers = asList(request.getAllHeaders()).stream().collect(HttpHeaders::new, (m, c) -> m.add(c.getName(), c.getValue()), HttpHeaders::putAll);

        try {
            return new ZResponse<>(restTemplate.exchange(request.getURI(), HttpMethod.valueOf(request.getMethod()), new HttpEntity<>(headers), clz));
        } catch (Exception e) {
            throw new ZRestException(request, "Error communicating with Zonky Rest API", e);
        }
    }

}
