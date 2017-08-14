package com.mawek.zzz.service

import com.mawek.zzz.BaseIT
import com.mawek.zzz.JsonFileHelper
import com.mawek.zzz.model.Loan
import com.mawek.zzz.rest.ZRequestBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner.class)
@SpringBootTest
class MarketplaceServiceIT extends BaseIT {

    @Autowired
    private MarketplaceService marketplaceService

    @Test
    void testGetLoans() {

        zonkyServer
                .expect(requestTo("https://api.zonky.cz/loans/marketplace?datePublished__gt=2017-07-20T23%3A59%3A59.000%2B02%3A00"))
                .andExpect(header(ZRequestBuilder.ORDER_HEADER, Loan.SortableField.DATE_PUBLISHED.descOrder))
                .andExpect(header(ZRequestBuilder.PAGE_INDEX_HEADER, "0"))
                .andExpect(queryParam("datePublished__gt", "2017-07-20T23%3A59%3A59.000%2B02%3A00"))
                .andRespond(withSuccess(JsonFileHelper.getJsonAsString("json/loans.json"), MediaType.APPLICATION_JSON))

        marketplaceService.getLoans(null)

        zonkyServer.verify()

    }


}