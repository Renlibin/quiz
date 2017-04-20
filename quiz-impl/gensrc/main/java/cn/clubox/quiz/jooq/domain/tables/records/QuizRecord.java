/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.records;


import cn.clubox.quiz.jooq.domain.tables.Quiz;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class QuizRecord extends UpdatableRecordImpl<QuizRecord> implements Record8<Integer, String, String, String, Timestamp, String, String, String> {

    private static final long serialVersionUID = 1601568383;

    /**
     * Setter for <code>quiz.quiz.id</code>.
     */
    public QuizRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>quiz.quiz.title</code>.
     */
    public QuizRecord setTitle(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.title</code>.
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>quiz.quiz.description</code>.
     */
    public QuizRecord setDescription(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>quiz.quiz.status</code>.
     */
    public QuizRecord setStatus(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.status</code>.
     */
    public String getStatus() {
        return (String) get(3);
    }

    /**
     * Setter for <code>quiz.quiz.stored</code>.
     */
    public QuizRecord setStored(Timestamp value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.stored</code>.
     */
    public Timestamp getStored() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>quiz.quiz.quiz_type</code>.
     */
    public QuizRecord setQuizType(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.quiz_type</code>.
     */
    public String getQuizType() {
        return (String) get(5);
    }

    /**
     * Setter for <code>quiz.quiz.logo_src</code>.
     */
    public QuizRecord setLogoSrc(String value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.logo_src</code>.
     */
    public String getLogoSrc() {
        return (String) get(6);
    }

    /**
     * Setter for <code>quiz.quiz.name</code>.
     */
    public QuizRecord setName(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz.name</code>.
     */
    public String getName() {
        return (String) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, String, String, Timestamp, String, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, String, String, Timestamp, String, String, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Quiz.QUIZ_.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Quiz.QUIZ_.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Quiz.QUIZ_.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Quiz.QUIZ_.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return Quiz.QUIZ_.STORED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Quiz.QUIZ_.QUIZ_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Quiz.QUIZ_.LOGO_SRC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Quiz.QUIZ_.NAME;
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
    public String value2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getStored();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getQuizType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getLogoSrc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value2(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value3(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value4(String value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value5(Timestamp value) {
        setStored(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value6(String value) {
        setQuizType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value7(String value) {
        setLogoSrc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord value8(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizRecord values(Integer value1, String value2, String value3, String value4, Timestamp value5, String value6, String value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuizRecord
     */
    public QuizRecord() {
        super(Quiz.QUIZ_);
    }

    /**
     * Create a detached, initialised QuizRecord
     */
    public QuizRecord(Integer id, String title, String description, String status, Timestamp stored, String quizType, String logoSrc, String name) {
        super(Quiz.QUIZ_);

        set(0, id);
        set(1, title);
        set(2, description);
        set(3, status);
        set(4, stored);
        set(5, quizType);
        set(6, logoSrc);
        set(7, name);
    }
}
