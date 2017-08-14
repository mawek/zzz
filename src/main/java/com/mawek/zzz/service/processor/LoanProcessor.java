package com.mawek.zzz.service.processor;

import com.mawek.zzz.model.Loan;

import java.util.List;

/**
 * Interface for processing loans
 */
public interface LoanProcessor {
    void processLoans(List<Loan> loans);
}
