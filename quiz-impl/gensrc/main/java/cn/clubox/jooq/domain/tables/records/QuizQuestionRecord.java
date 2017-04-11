/*
 * This file is generated by jOOQ.
*/
package cn.clubox.jooq.domain.tables.records;


import cn.clubox.jooq.domain.tables.QuizQuestion;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


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
public class QuizQuestionRecord extends UpdatableRecordImpl<QuizQuestionRecord> implements Record6<Integer, Integer, String, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = 1161396292;

    /**
     * Setter for <code>quiz.quiz_question.id</code>.
     */
    public QuizQuestionRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_question.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>quiz.quiz_question.quiz_id</code>.
     */
    public QuizQuestionRecord setQuizId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_question.quiz_id</code>.
     */
    public Integer getQuizId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>quiz.quiz_question.title</code>.
     */
    public QuizQuestionRecord setTitle(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_question.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>quiz.quiz_question.stored</code>.
     */
    public QuizQuestionRecord setStored(Timestamp value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_question.stored</code>.
     */
    public Timestamp getStored() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>quiz.quiz_question.updated</code>.
     */
    public QuizQuestionRecord setUpdated(Timestamp value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_question.updated</code>.
     */
    public Timestamp getUpdated() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>quiz.quiz_question.sequnce_number</code>.
     */
    public QuizQuestionRecord setSequnceNumber(Integer value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_question.sequnce_number</code>.
     */
    public Integer getSequnceNumber() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return QuizQuestion.QUIZ_QUESTION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return QuizQuestion.QUIZ_QUESTION.QUIZ_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return QuizQuestion.QUIZ_QUESTION.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return QuizQuestion.QUIZ_QUESTION.STORED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return QuizQuestion.QUIZ_QUESTION.UPDATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return QuizQuestion.QUIZ_QUESTION.SEQUNCE_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getQuizId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getStored();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpdated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getSequnceNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord value2(Integer value) {
        setQuizId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord value3(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord value4(Timestamp value) {
        setStored(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord value5(Timestamp value) {
        setUpdated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord value6(Integer value) {
        setSequnceNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizQuestionRecord values(Integer value1, Integer value2, String value3, Timestamp value4, Timestamp value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuizQuestionRecord
     */
    public QuizQuestionRecord() {
        super(QuizQuestion.QUIZ_QUESTION);
    }

    /**
     * Create a detached, initialised QuizQuestionRecord
     */
    public QuizQuestionRecord(Integer id, Integer quizId, String title, Timestamp stored, Timestamp updated, Integer sequnceNumber) {
        super(QuizQuestion.QUIZ_QUESTION);

        set(0, id);
        set(1, quizId);
        set(2, title);
        set(3, stored);
        set(4, updated);
        set(5, sequnceNumber);
    }
}