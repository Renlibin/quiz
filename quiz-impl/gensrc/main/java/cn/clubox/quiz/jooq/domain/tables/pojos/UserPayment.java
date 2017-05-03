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
public class UserPayment implements Serializable {

    private static final long serialVersionUID = 2016619611;

    private Integer    id;
    private Integer    userId;
    private Integer    quizId;
    private BigDecimal amount;
    private Timestamp  stored;
    private String     status;

    public UserPayment() {}

    public UserPayment(UserPayment value) {
        this.id = value.id;
        this.userId = value.userId;
        this.quizId = value.quizId;
        this.amount = value.amount;
        this.stored = value.stored;
        this.status = value.status;
    }

    public UserPayment(
        Integer    id,
        Integer    userId,
        Integer    quizId,
        BigDecimal amount,
        Timestamp  stored,
        String     status
    ) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.amount = amount;
        this.stored = stored;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public UserPayment setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public UserPayment setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getQuizId() {
        return this.quizId;
    }

    public UserPayment setQuizId(Integer quizId) {
        this.quizId = quizId;
        return this;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public UserPayment setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Timestamp getStored() {
        return this.stored;
    }

    public UserPayment setStored(Timestamp stored) {
        this.stored = stored;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public UserPayment setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserPayment (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(quizId);
        sb.append(", ").append(amount);
        sb.append(", ").append(stored);
        sb.append(", ").append(status);

        sb.append(")");
        return sb.toString();
    }
}
