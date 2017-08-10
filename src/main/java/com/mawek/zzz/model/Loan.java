package com.mawek.zzz.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Loan {

    private final long id;
    private final String url;
    private final String name;
    private final String story;
    private final String purpose;

    // no need for photos field
    //    private final List<Photo> photos;
//    private final BigDecimal interestRate;
//    private final String rating;
//    private final boolean topped;
//    private final BigDecimal amount;
//    private final BigDecimal remainingInvestment;
//    private final BigDecimal investmentRate;
//    private final boolean covered;
//    private final String datePublished;
//    private final boolean published;
//    private final String deadline;
//    private final int investmentsCount;
//    private final int questionsCount;
//    private final String region;
//    private final String mainIncomeType;


    @JsonCreator
    public Loan(@JsonProperty("id") long id,
                @JsonProperty("url") String url,
                @JsonProperty("name") String name,
                @JsonProperty("story") String story,
                @JsonProperty("purpose") String purpose) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.story = story;
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
