package com.mawek.zzz.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
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

    // fields are not final, but the Loan class itself is effective immutable - LoanBuilder is guarding invariants

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
    private String datePublished; // TODO date
    private boolean published;
    private String deadline; // TODO date
    private int investmentsCount;
    private int questionsCount;
    private String region;
    private String mainIncomeType;

    /* Optional fields */
    private String url;
    private String story;
    private boolean topped;

    // only LoanBuilder can create Loan
    private Loan() {
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

    public String getDatePublished() {
        return datePublished;
    }

    public boolean isPublished() {
        return published;
    }

    public String getDeadline() {
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
        private String datePublished; // TODO date
        private boolean published;
        private String deadline; // TODO date
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

        public LoanBuilder withDatePublished(String datePublished) {
            this.datePublished = datePublished;
            return this;
        }

        public LoanBuilder withPublished(boolean published) {
            this.published = published;
            return this;
        }

        public LoanBuilder withDeadline(String deadline) {
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
            Loan loan = new Loan();
            loan.id = id;
            loan.name = notEmpty(name, "name can't be empty");
            loan.purpose = notEmpty(purpose, "purpose can't be empty");
            loan.nickName = notEmpty(nickName, "nickName can't be empty");
            loan.termInMonths = termInMonths;
            loan.interestRate = notNull(interestRate, "interestRate can't be null");
            loan.rating = notEmpty(rating, "rating can't be empty");
            loan.amount = notNull(amount, "amount can't be null");
            loan.remainingInvestment = notNull(remainingInvestment, "remainingInvestment can't be null");
            loan.investmentRate = notNull(investmentRate, "investmentRate can't be null");
            loan.covered = covered;
            loan.datePublished = notEmpty(datePublished, "datePublished can't be empty");
            loan.published = published;
            loan.deadline = notEmpty(deadline, "deadline can't be empty");
            loan.investmentsCount = investmentsCount;
            loan.questionsCount = questionsCount;
            loan.questionsCount = questionsCount;
            loan.region = notEmpty(region, "region can't be empty");
            loan.mainIncomeType = notEmpty(mainIncomeType, "mainIncomeType can't be empty");

            loan.url = url;
            loan.story = story;
            loan.topped = topped;

            return loan;
        }
    }
}
