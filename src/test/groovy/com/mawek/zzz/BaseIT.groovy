package com.mawek.zzz

import com.mawek.zzz.rest.ZRestTemplate
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate


/**
 * Common ancestor for spring 'integration tests' that need mocked zonky backend.
 * */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev, test")
abstract class BaseIT {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    protected ZRestTemplate zRestTemplate

    protected MockRestServiceServer zonkyServer

    @Before
    void setUp() {
        zonkyServer = MockRestServiceServer.bindTo(restTemplate).build()
        zonkyServer.reset()
    }

}