/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.pojos;


import java.io.Serializable;
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

    private static final long serialVersionUID = 709750405;

    private Integer   id;
    private Integer   quizId;
    private Integer   price;
    private String    status;
    private Timestamp stored;

    public QuizPricing() {}

    public QuizPricing(QuizPricing value) {
        this.id = value.id;
        this.quizId = value.quizId;
        this.price = value.price;
        this.status = value.status;
        this.stored = value.stored;
    }

    public QuizPricing(
        Integer   id,
        Integer   quizId,
        Integer   price,
        String    status,
        Timestamp stored
    ) {
        this.id = id;
        this.quizId = quizId;
        this.price = price;
        this.status = status;
        this.stored = stored;
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

    public Integer getPrice() {
        return this.price;
    }

    public QuizPricing setPrice(Integer price) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("QuizPricing (");

        sb.append(id);
        sb.append(", ").append(quizId);
        sb.append(", ").append(price);
        sb.append(", ").append(status);
        sb.append(", ").append(stored);

        sb.append(")");
        return sb.toString();
    }
}