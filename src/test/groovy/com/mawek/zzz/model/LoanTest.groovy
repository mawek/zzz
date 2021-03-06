package com.mawek.zzz.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.mawek.zzz.JsonFileHelper
import org.junit.Test

import java.time.ZonedDateTime

class LoanTest {

    @Test
    void testLoanDeserialize() {

        final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()

        def loanJson = JsonFileHelper.getJsonAsString("json/loan.json")
        Loan loan = mapper.readValue(loanJson, Loan.class)

        checkRequiredFields(loan)
        checkOptionalFields(loan)
    }

    @Test
    void testLoanEqual() {

        final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()

        Loan loan = mapper.readValue(JsonFileHelper.getJsonAsString("json/loan.json"), Loan.class)
        Loan loan2 = mapper.readValue(JsonFileHelper.getJsonAsString("json/loan_scale.json"), Loan.class)

        assert loan == loan2
    }

    @Test(expected = NullPointerException.class)
    void testLoanBuilderMissingRequiredFields() {
        new Loan.LoanBuilder(15).withName('some name').build()
    }

    @Test
    void testLoanBuilder() {
        def loanBuilder = new Loan.LoanBuilder(113913)
                .withName('Vyplacení manželky z bytu')
                .withPurpose('7')
                .withNickName('Martynez')
                .withTermInMonths(84)
                .withInterestRate(new BigDecimal("0.059900"))
                .withRating('AAA')
                .withAmount(new BigDecimal("235000.00"))
                .withRemainingInvestment(new BigDecimal("217000.00"))
                .withInvestmentRate(new BigDecimal("0.07659574468085106"))
                .withCovered(false)
                .withDatePublished(ZonedDateTime.parse('2017-08-09T17:51:58.977+02:00'))
                .withPublished(true)
                .withDeadline(ZonedDateTime.parse('2017-08-11T17:42:55.073+02:00'))
                .withInvestmentsCount(33)
                .withQuestionsCount(3)
                .withRegion('6')
                .withMainIncomeType('EMPLOYMENT')


        checkRequiredFields(loanBuilder.build())

        loanBuilder.withUrl('https://app.zonky.cz/loan/113913')
                .withStory('Po jednadvaceti letech jsme došli do stádia rozvodu a protože je byt ve kterém jsme společně bydleli v příjemné lokalitě a dostatečně prostorný, chtěl bych vyplatit manželku a zůstat zde společně se synem ve střídavé péči. \n' +
                'Děkuji Všem Investorům, kteří tímto způsobem pomáhají a zároveň investují...')
                .withTopped(false)

        checkOptionalFields(loanBuilder.build())
    }

    private checkRequiredFields(Loan loan) {
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
        assert loan.datePublished.isEqual(ZonedDateTime.parse("2017-08-09T17:51:58.977+02:00"))
        assert loan.published
        assert loan.deadline.isEqual(ZonedDateTime.parse("2017-08-11T17:42:55.073+02:00"))
        assert loan.investmentsCount == 33
        assert loan.questionsCount == 3
        assert loan.region == "6"
        assert loan.mainIncomeType == "EMPLOYMENT"
    }

    private checkOptionalFields(Loan loan) {
        assert loan.url == 'https://app.zonky.cz/loan/113913'
        assert loan.story == 'Po jednadvaceti letech jsme došli do stádia rozvodu a protože je byt ve kterém jsme společně bydleli v příjemné lokalitě a dostatečně prostorný, chtěl bych vyplatit manželku a zůstat zde společně se synem ve střídavé péči. \nDěkuji Všem Investorům, kteří tímto způsobem pomáhají a zároveň investují...'
        assert !loan.topped
    }

}