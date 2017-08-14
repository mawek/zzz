package com.mawek.zzz.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mawek.zzz.BaseIT
import com.mawek.zzz.JsonFileHelper
import com.mawek.zzz.model.Loan
import com.mawek.zzz.rest.ZRequestBuilder
import com.mawek.zzz.rest.ZResponse
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner

import java.time.ZonedDateTime

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner.class)
@SpringBootTest
class MarketplaceServiceIT extends BaseIT {

    @Autowired
    private MarketplaceService marketplaceService

    @Autowired
    private ObjectMapper objectMapper

    @Test
    void testGetLoans() {

        def loansAsString = JsonFileHelper.getJsonAsString("json/loans.json")
        zonkyServer
                .expect(requestTo("https://api.zonky.cz/loans/marketplace?datePublished__gt=2017-08-09T17%3A51%3A58.977%2B02%3A00"))
                .andExpect(header(ZRequestBuilder.ORDER_HEADER, Loan.SortableField.DATE_PUBLISHED.descOrder))
                .andExpect(header(ZRequestBuilder.PAGE_INDEX_HEADER, "0"))
                .andExpect(queryParam("datePublished__gt", "2017-08-09T17%3A51%3A58.977%2B02%3A00"))
                .andRespond(withSuccess(loansAsString, MediaType.APPLICATION_JSON))

        def fromDatePublished = ZonedDateTime.parse("2017-08-09T17:51:58.977+02:00")

        List<Loan> loans = marketplaceService.getLoans(fromDatePublished)

        zonkyServer.verify()

        assert loans == objectMapper.readValue(loansAsString, Loan[].class) as List
    }

    @Test
    void testGetPagedLoans() {

        def loansPage1AsString = JsonFileHelper.getJsonAsString("json/loans_page_1.json")
        def headers = new HttpHeaders()
        headers.add(ZResponse.PAGE_TOTAL_HEADER, '20')
        zonkyServer
                .expect(requestTo("https://api.zonky.cz/loans/marketplace?datePublished__gt=2017-08-09T17%3A51%3A58.977%2B02%3A00"))
                .andExpect(header(ZRequestBuilder.ORDER_HEADER, Loan.SortableField.DATE_PUBLISHED.descOrder))
                .andExpect(header(ZRequestBuilder.PAGE_INDEX_HEADER, "0"))
                .andExpect(queryParam("datePublished__gt", "2017-08-09T17%3A51%3A58.977%2B02%3A00"))
                .andRespond(withSuccess(loansPage1AsString, MediaType.APPLICATION_JSON).headers(headers))

        def loansPage2AsString = JsonFileHelper.getJsonAsString("json/loans_page_2.json")
        zonkyServer
                .expect(requestTo("https://api.zonky.cz/loans/marketplace?datePublished__gt=2017-08-09T17%3A51%3A58.977%2B02%3A00"))
                .andExpect(header(ZRequestBuilder.ORDER_HEADER, Loan.SortableField.DATE_PUBLISHED.descOrder))
                .andExpect(header(ZRequestBuilder.PAGE_INDEX_HEADER, "1"))
                .andExpect(queryParam("datePublished__gt", "2017-08-09T17%3A51%3A58.977%2B02%3A00"))
                .andRespond(withSuccess(loansPage2AsString, MediaType.APPLICATION_JSON).headers(headers))

        def fromDatePublished = ZonedDateTime.parse("2017-08-09T17:51:58.977+02:00")

        List<Loan> loans = marketplaceService.getLoans(fromDatePublished)

        zonkyServer.verify()

        assert loans == objectMapper.readValue(loansPage1AsString, Loan[].class) + objectMapper.readValue(loansPage2AsString, Loan[].class)
    }


}