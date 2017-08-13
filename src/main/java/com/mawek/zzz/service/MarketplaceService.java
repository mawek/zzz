package com.mawek.zzz.service;

import com.google.common.collect.Lists;
import com.mawek.zzz.model.Loan;
import com.mawek.zzz.model.Loan.FilterableField;
import com.mawek.zzz.model.Loan.SortableField;
import com.mawek.zzz.rest.FilterOperation;
import com.mawek.zzz.rest.ZRequestBuilder;
import com.mawek.zzz.rest.ZResponse;
import com.mawek.zzz.rest.ZRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Service for handling Zonky marketplace.
 */

@Component
public class MarketplaceService {

    private final ZRestTemplate zrestTemplate;

    @Autowired
    public MarketplaceService(ZRestTemplate zrestTemplate) {
        notNull(zrestTemplate, "zrestTemplate can't be null");

        this.zrestTemplate = zrestTemplate;
    }


    /**
     * Return all loans that datePublished field is newer than provided parameter.
     * Used for fetching 'new' loans in marketplace.
     *
     * @param fromDatePublished lower boundary of datePublished field of returned loans
     * @return list of loans
     */
    public List<Loan> getLoans(ZonedDateTime fromDatePublished) {


        // TODO hostname configurable
        final ZRequestBuilder requestBuilder = zrestTemplate.createGet("https://api.zonky.cz/loans/marketplace")
                .addSortField(SortableField.DATE_PUBLISHED.getDescOrder())
                .addFilterField(FilterableField.DATE_PUBLISHED.getFieldFilter(FilterOperation.GT), "2017-07-20T23:59:59.000+02:00")
                .setPageIndex(0);

        final List<Loan> loans = Lists.newLinkedList();
        for (int i = 0; ; i++) {
            final ZResponse<Loan[]> response = requestBuilder.execute(Loan[].class);

            loans.addAll(asList(response.getEntity()));

            if (loans.size() < response.getPagingTotal()) {
                requestBuilder.setPageIndex(i);
            } else {
                return loans;
            }
        }
    }
}
