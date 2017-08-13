package com.mawek.zzz.rest

import org.apache.http.client.methods.HttpGet
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner.class)
@SpringBootTest
class ZRestTemplateTest {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private ZRestTemplate zRestTemplate

    private zonkyServer

    @Before
    void setUp() {
        zonkyServer = MockRestServiceServer.bindTo(restTemplate).build()
    }

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