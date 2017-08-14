package com.mawek.zzz.service.processor;

import com.mawek.zzz.model.Loan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Simple loan processor that prints loans to the console
 */

@Component
@ConditionalOnProperty(value = "loan.processor", havingValue = "console")
public class ConsoleLoggingLoanProcessor implements LoanProcessor {

    @Override
    public void processLoans(List<Loan> loans) {
        notNull(loans, "loans can't be null");

        loans.stream().forEach(this::processLoan);
    }

    private void processLoan(Loan loan) {
        final StringBuilder str = new StringBuilder("Loan ");
        str.append("id: [").append(loan.getId()).append("]");
        str.append(", datePublished: [").append(loan.getDatePublished().toString()).append("]");
        str.append(", name short: [").append(StringUtils.abbreviate(loan.getName(), 20)).append("]");

        System.out.println(str);
    }
}
