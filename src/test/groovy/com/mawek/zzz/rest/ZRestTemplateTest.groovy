package com.mawek.zzz.rest

import com.mawek.zzz.BaseIT
import org.apache.http.client.methods.HttpGet
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner.class)
@SpringBootTest
class ZRestTemplateTest extends BaseIT {

    @Test
    void testGetLoans() {

        zonkyServer
                .expect(requestTo("some_uri?asdf=tralala"))
                .andExpect(header("x_some_header", "some_header_value"))
                .andExpect(header("x_another_header", "another_header_value"))
                .andExpect(queryParam("asdf", "tralala"))
                .andRespond(withSuccess())

        def request = new HttpGet("some_uri?asdf=tralala");
        request.addHeader("x_some_header", "some_header_value")
        request.addHeader("x_another_header", "another_header_value")

        zRestTemplate.execute(request, Void.class)

        zonkyServer.verify()
    }
}