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
public class QuizQuestionOption implements Serializable {

    private static final long serialVersionUID = 928471822;

    private Integer   id;
    private Integer   quizQuestionId;
    private String    title;
    private Timestamp stored;
    private Short     score;
    private Short     sequenceNumber;

    public QuizQuestionOption() {}

    public QuizQuestionOption(QuizQuestionOption value) {
        this.id = value.id;
        this.quizQuestionId = value.quizQuestionId;
        this.title = value.title;
        this.stored = value.stored;
        this.score = value.score;
        this.sequenceNumber = value.sequenceNumber;
    }

    public QuizQuestionOption(
        Integer   id,
        Integer   quizQuestionId,
        String    title,
        Timestamp stored,
        Short     score,
        Short     sequenceNumber
    ) {
        this.id = id;
        this.quizQuestionId = quizQuestionId;
        this.title = title;
        this.stored = stored;
        this.score = score;
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getId() {
        return this.id;
    }

    public QuizQuestionOption setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getQuizQuestionId() {
        return this.quizQuestionId;
    }

    public QuizQuestionOption setQuizQuestionId(Integer quizQuestionId) {
        this.quizQuestionId = quizQuestionId;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public QuizQuestionOption setTitle(String title) {
        this.title = title;
        return this;
    }

    public Timestamp getStored() {
        return this.stored;
    }

    public QuizQuestionOption setStored(Timestamp stored) {
        this.stored = stored;
        return this;
    }

    public Short getScore() {
        return this.score;
    }

    public QuizQuestionOption setScore(Short score) {
        this.score = score;
        return this;
    }

    public Short getSequenceNumber() {
        return this.sequenceNumber;
    }

    public QuizQuestionOption setSequenceNumber(Short sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("QuizQuestionOption (");

        sb.append(id);
        sb.append(", ").append(quizQuestionId);
        sb.append(", ").append(title);
        sb.append(", ").append(stored);
        sb.append(", ").append(score);
        sb.append(", ").append(sequenceNumber);

        sb.append(")");
        return sb.toString();
    }
}
