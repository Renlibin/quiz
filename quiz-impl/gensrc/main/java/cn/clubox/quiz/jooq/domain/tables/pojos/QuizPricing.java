/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuizPricing implements Serializable {

    private static final long serialVersionUID = 1097600826;

    private Integer    id;
    private Integer    quizId;
    private BigDecimal price;
    private String     status;
    private Timestamp  stored;
    private BigDecimal originalPrice;

    public QuizPricing() {}

    public QuizPricing(QuizPricing value) {
        this.id = value.id;
        this.quizId = value.quizId;
        this.price = value.price;
        this.status = value.status;
        this.stored = value.stored;
        this.originalPrice = value.originalPrice;
    }

    public QuizPricing(
        Integer    id,
        Integer    quizId,
        BigDecimal price,
        String     status,
        Timestamp  stored,
        BigDecimal originalPrice
    ) {
        this.id = id;
        this.quizId = quizId;
        this.price = price;
        this.status = status;
        this.stored = stored;
        this.originalPrice = originalPrice;
    }

    public Integer getId() {
        return this.id;
    }

    public QuizPricing setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getQuizId() {
        return this.quizId;
    }

    public QuizPricing setQuizId(Integer quizId) {
        this.quizId = quizId;
        return this;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public QuizPricing setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public QuizPricing setStatus(String status) {
        this.status = status;
        return this;
    }

    public Timestamp getStored() {
        return this.stored;
    }

    public QuizPricing setStored(Timestamp stored) {
        this.stored = stored;
        return this;
    }

    public BigDecimal getOriginalPrice() {
        return this.originalPrice;
    }

    public QuizPricing setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("QuizPricing (");

        sb.append(id);
        sb.append(", ").append(quizId);
        sb.append(", ").append(price);
        sb.append(", ").append(status);
        sb.append(", ").append(stored);
        sb.append(", ").append(originalPrice);

        sb.append(")");
        return sb.toString();
    }
}
