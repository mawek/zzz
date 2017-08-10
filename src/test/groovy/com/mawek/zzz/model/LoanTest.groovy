package com.mawek.zzz.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.mawek.zzz.JsonFileHelper
import org.junit.Test

class LoanTest {

    private ObjectMapper OBJECT_MAPPER = new ObjectMapper()

    @Test
    void testLoanDeserialize() {
        def loanJson = JsonFileHelper.getJsonAsString("json/loan.json")
        Loan loan = OBJECT_MAPPER.readValue(loanJson, Loan.class)

        assert loan.id == 113913
        assert loan.name == "Vyplacení manželky z bytu"
        assert loan.purpose == "7"
        assert loan.nickName == "Martynez"
        assert loan.termInMonths == 84
        assert loan.interestRate == new BigDecimal("0.059900")
        assert loan.rating == "AAA"
        assert loan.amount == new BigDecimal("235000.00")
        assert loan.remainingInvestment == new BigDecimal("217000.00")
        assert loan.investmentRate == new BigDecimal("0.07659574468085106")
        assert !loan.covered
        assert loan.datePublished == "2017-08-09T17:51:58.977+02:00"
        assert loan.published
        assert loan.deadline == "2017-08-11T17:42:55.073+02:00"
        assert loan.investmentsCount == 33
        assert loan.questionsCount == 3
        assert loan.region == "6"
        assert loan.mainIncomeType == "EMPLOYMENT"

        assert loan.url == "https://app.zonky.cz/loan/113913"
        assert loan.story == "Po jednadvaceti letech jsme došli do stádia rozvodu a protože je byt ve kterém jsme společně bydleli v příjemné lokalitě a dostatečně prostorný, chtěl bych vyplatit manželku a zůstat zde společně se synem ve střídavé péči. \nDěkuji Všem Investorům, kteří tímto způsobem pomáhají a zároveň investují..."
        assert !loan.topped

    }

}