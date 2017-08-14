package com.mawek.zzz.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mawek.zzz.rest.FilterOperation;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Immutable Loan DTO. Use LoanBuilder for construction.
 * <p>
 * Ignore 'photos' field for model simplicity.
 */
@JsonDeserialize(builder = Loan.LoanBuilder.class)
public final class Loan {

    /* Required fields */
    private final long id;
    private final String name;
    private final String purpose;
    private final String nickName;
    private final int termInMonths;
    private final BigDecimal interestRate;
    private final String rating;
    private final BigDecimal amount;
    private final BigDecimal remainingInvestment;
    private final BigDecimal investmentRate;
    private final boolean covered;
    private final ZonedDateTime datePublished;
    private final boolean published;
    private final ZonedDateTime deadline;
    private final int investmentsCount;
    private final int questionsCount;
    private final String region;
    private final String mainIncomeType;

    /* Optional fields */
    private final String url;
    private final String story;
    private final boolean topped;

    // only LoanBuilder can create Loan
    private Loan(LoanBuilder builder) {
        this.id = builder.id;
        this.name = notEmpty(builder.name, "name can't be empty");
        this.purpose = notEmpty(builder.purpose, "purpose can't be empty");
        this.nickName = notEmpty(builder.nickName, "nickName can't be empty");
        this.termInMonths = builder.termInMonths;
        this.interestRate = notNull(builder.interestRate, "interestRate can't be null");
        this.rating = notEmpty(builder.rating, "rating can't be empty");
        this.amount = notNull(builder.amount, "amount can't be null");
        this.remainingInvestment = notNull(builder.remainingInvestment, "remainingInvestment can't be null");
        this.investmentRate = notNull(builder.investmentRate, "investmentRate can't be null");
        this.covered = builder.covered;
        this.datePublished = notNull(builder.datePublished, "datePublished can't be empty");
        this.published = builder.published;
        this.deadline = notNull(builder.deadline, "deadline can't be empty");
        this.investmentsCount = builder.investmentsCount;
        this.questionsCount = builder.questionsCount;
        this.region = notEmpty(builder.region, "region can't be empty");
        this.mainIncomeType = notEmpty(builder.mainIncomeType, "mainIncomeType can't be empty");

        this.url = builder.url;
        this.story = builder.story;
        this.topped = builder.topped;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getNickName() {
        return nickName;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public String getRating() {
        return rating;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRemainingInvestment() {
        return remainingInvestment;
    }

    public BigDecimal getInvestmentRate() {
        return investmentRate;
    }

    public boolean isCovered() {
        return covered;
    }

    public ZonedDateTime getDatePublished() {
        return datePublished;
    }

    public boolean isPublished() {
        return published;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public int getInvestmentsCount() {
        return investmentsCount;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public String getRegion() {
        return region;
    }

    public String getMainIncomeType() {
        return mainIncomeType;
    }

    public String getUrl() {
        return url;
    }

    public String getStory() {
        return story;
    }

    public boolean isTopped() {
        return topped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id &&
                termInMonths == loan.termInMonths &&
                covered == loan.covered &&
                published == loan.published &&
                investmentsCount == loan.investmentsCount &&
                questionsCount == loan.questionsCount &&
                topped == loan.topped &&
                Objects.equals(name, loan.name) &&
                Objects.equals(purpose, loan.purpose) &&
                Objects.equals(nickName, loan.nickName) &&
                Objects.equals(interestRate, loan.interestRate) &&
                Objects.equals(rating, loan.rating) &&
                Objects.equals(amount, loan.amount) &&
                Objects.equals(remainingInvestment, loan.remainingInvestment) &&
                Objects.equals(investmentRate, loan.investmentRate) &&
                Objects.equals(datePublished, loan.datePublished) &&
                Objects.equals(deadline, loan.deadline) &&
                Objects.equals(region, loan.region) &&
                Objects.equals(mainIncomeType, loan.mainIncomeType) &&
                Objects.equals(url, loan.url) &&
                Objects.equals(story, loan.story);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                name,
                purpose,
                nickName,
                termInMonths,
                interestRate,
                rating,
                amount,
                remainingInvestment,
                investmentRate,
                covered,
                datePublished,
                published,
                deadline,
                investmentsCount,
                questionsCount,
                region,
                mainIncomeType,
                url,
                story,
                topped
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("purpose", purpose)
                .append("nickName", nickName)
                .append("termInMonths", termInMonths)
                .append("interestRate", interestRate)
                .append("rating", rating)
                .append("amount", amount)
                .append("remainingInvestment", remainingInvestment)
                .append("investmentRate", investmentRate)
                .append("covered", covered)
                .append("datePublished", datePublished)
                .append("published", published)
                .append("deadline", deadline)
                .append("investmentsCount", investmentsCount)
                .append("questionsCount", questionsCount)
                .append("region", region)
                .append("mainIncomeType", mainIncomeType)
                .append("url", url)
                .append("story", story)
                .append("topped", topped)
                .toString();
    }

    // TODO might be splitted to own classes (maybe create separate package for loan? )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoanBuilder {
        /* Required fields */
        private long id;
        private String name;
        private String purpose;
        private String nickName;
        private int termInMonths;
        private BigDecimal interestRate;
        private String rating;
        private BigDecimal amount;
        private BigDecimal remainingInvestment;
        private BigDecimal investmentRate;
        private boolean covered;
        private ZonedDateTime datePublished;
        private boolean published;
        private ZonedDateTime deadline;
        private int investmentsCount;
        private int questionsCount;
        private String region;
        private String mainIncomeType;

        /* Optional fields */
        private String url;
        private String story;
        private boolean topped;

        @JsonCreator
        public LoanBuilder(@JsonProperty("id") long id) {
            this.id = id;
        }

        public LoanBuilder witId(long id) {
            this.id = id;
            return this;
        }

        public LoanBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public LoanBuilder withPurpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public LoanBuilder withNickName(String nickName) {
            this.nickName = nickName;
            return this;
        }


        public LoanBuilder withTermInMonths(int termInMonths) {
            this.termInMonths = termInMonths;
            return this;
        }

        public LoanBuilder withInterestRate(BigDecimal interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public LoanBuilder withRating(String rating) {
            this.rating = rating;
            return this;
        }

        public LoanBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public LoanBuilder withRemainingInvestment(BigDecimal remainingInvestment) {
            this.remainingInvestment = remainingInvestment;
            return this;
        }

        public LoanBuilder withInvestmentRate(BigDecimal investmentRate) {
            this.investmentRate = investmentRate;
            return this;
        }

        public LoanBuilder withCovered(boolean covered) {
            this.covered = covered;
            return this;
        }

        public LoanBuilder withDatePublished(ZonedDateTime datePublished) {
            this.datePublished = datePublished;
            return this;
        }

        public LoanBuilder withPublished(boolean published) {
            this.published = published;
            return this;
        }

        public LoanBuilder withDeadline(ZonedDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public LoanBuilder withInvestmentsCount(int investmentsCount) {
            this.investmentsCount = investmentsCount;
            return this;
        }

        public LoanBuilder withQuestionsCount(int questionsCount) {
            this.questionsCount = questionsCount;
            return this;
        }

        public LoanBuilder withRegion(String region) {
            this.region = region;
            return this;
        }

        public LoanBuilder withMainIncomeType(String mainIncomeType) {
            this.mainIncomeType = mainIncomeType;
            return this;
        }

        public LoanBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public LoanBuilder withStory(String story) {
            this.story = story;
            return this;
        }

        public LoanBuilder withTopped(boolean topped) {
            this.topped = topped;
            return this;
        }

        public Loan build() {
            return new Loan(this);
        }
    }

    /**
     * Represent field that can be used for sorting on Rest API.
     */
    public enum SortableField {
        DATE_PUBLISHED("datePublished");

        private final String fieldName;

        SortableField(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getAscOrder() {
            return fieldName;
        }

        public String getDescOrder() {
            return "-" + fieldName;
        }
    }

    /**
     * Represent field that can be used for filtering on Rest API.
     */
    public enum FilterableField {
        DATE_PUBLISHED("datePublished");

        private final String fieldName;

        FilterableField(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldFilter(FilterOperation operation) {
            notNull(operation, "filter operation can't be null");
            return String.join("__", fieldName, operation.toString().toLowerCase());
        }
    }
}
