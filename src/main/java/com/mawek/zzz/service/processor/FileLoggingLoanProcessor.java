package com.mawek.zzz.service.processor;

import com.mawek.zzz.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * File loan processor that prints loans to the specified file
 */
@Component
@ConditionalOnProperty(value = "loan.processor", havingValue = "file")
public final class FileLoggingLoanProcessor implements LoanProcessor {

    private final String outputFilePath;

    @Autowired
    public FileLoggingLoanProcessor(@Value("${loan.processor.file.path}") String outputFilePath) {
        notEmpty(outputFilePath, "outputFilePath can't be empty");
        this.outputFilePath = outputFilePath;

        try {
            Files.createDirectories(Paths.get(outputFilePath).getParent());
            Files.write(Paths.get(outputFilePath), "ID, DATE_PUBLISHED, NAME \n".getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new LoanProcessorException("Unable to write to the file " + outputFilePath, e);
        }
    }

    @Override
    public void processLoans(List<Loan> loans) {
        notNull(loans, "loans can't be null");

        final String output = loans.stream().map(this::processLoan).collect(joining("\n"));
        try {
            Files.write(Paths.get(outputFilePath), output.getBytes(), APPEND);
        } catch (IOException e) {
            throw new LoanProcessorException("Unable to write loans to the file " + outputFilePath, e);
        }
    }

    private String processLoan(Loan loan) {
        return new StringBuilder()
                .append(loan.getId()).append(",")
                .append(loan.getDatePublished().toString()).append(",")
                .append(loan.getName())
                .toString();
    }
}
