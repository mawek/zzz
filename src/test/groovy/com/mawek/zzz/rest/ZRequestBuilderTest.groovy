package com.mawek.zzz.rest

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import org.junit.Test
import org.mockito.ArgumentCaptor

import static org.mockito.Matchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

class ZRequestBuilderTest {

    @Test
    void testCreateRequestBuilder() {
        def httpRequest = prepareRequest { requestBuilder -> requestBuilder }

        assert httpRequest.method == 'GET'
        assert httpRequest.URI.toString() == 'some_uri'
    }

    @Test
    void testRequestBuilderWithSorting() {

        def httpRequest = prepareRequest { requestBuilder -> requestBuilder.addSortField("field1").addSortField("-field2") }

        assert httpRequest.method == 'GET'
        assert httpRequest.URI.toString() == 'some_uri'
        assert httpRequest.getFirstHeader(ZRequestBuilder.ORDER_HEADER).value == 'field1,-field2'
    }

    @Test
    void testRequestBuilderWithFiltering() {

        def httpRequest = prepareRequest { requestBuilder -> requestBuilder.addFilterField("f1", "v1").addFilterField("f2", "v2").addFilterField("f2", "v3") }

        assert httpRequest.method == 'GET'
        assert httpRequest.URI.toString() == '/some_uri?f1=v1&f2=v3'
    }

    @Test
    void testRequestBuilderWithPaging() {

        def httpRequest = prepareRequest { requestBuilder -> requestBuilder.setPageIndex(5).setPageSize(7) }

        assert httpRequest.method == 'GET'
        assert httpRequest.URI.toString() == 'some_uri'
        assert httpRequest.getFirstHeader(ZRequestBuilder.PAGE_INDEX_HEADER).value == '5'
        assert httpRequest.getFirstHeader(ZRequestBuilder.PAGE_SIZE_HEADER).value == '7'
    }

    HttpUriRequest prepareRequest(def additionalSetup) {

        def zRestTemplate = mock(ZRestTemplate.class)

        ZRequestBuilder requestBuilder = new ZRequestBuilder(zRestTemplate, new HttpGet('some_uri'))
        additionalSetup(requestBuilder)

        requestBuilder.execute(Void.class)


        ArgumentCaptor<HttpUriRequest> httpUriRequestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class)
        verify(zRestTemplate).execute(httpUriRequestCaptor.capture(), any(Void.class))

        return httpUriRequestCaptor.getValue()
    }


}