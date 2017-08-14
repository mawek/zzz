package com.mawek.zzz.rest

import org.junit.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ZResponseTest {

    @Test
    void testResponse() {

        def httpHeaders = new HttpHeaders()
        httpHeaders.add(ZResponse.PAGE_TOTAL_HEADER, '15')

        def responseEntity = new ResponseEntity('hala bala tralala', httpHeaders, HttpStatus.OK)

        ZResponse<String> response = new ZResponse<>(responseEntity)

        assert response.entity == 'hala bala tralala'
        assert response.pagingTotal == 15
        assert response.httpStatus == 200
    }

}