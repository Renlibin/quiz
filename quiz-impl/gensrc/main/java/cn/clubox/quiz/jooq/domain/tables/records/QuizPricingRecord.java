/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.records;


import cn.clubox.quiz.jooq.domain.tables.QuizPricing;

import java.math.BigDecimal;
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
public class QuizPricingRecord extends UpdatableRecordImpl<QuizPricingRecord> implements Record6<Integer, Integer, BigDecimal, String, Timestamp, BigDecimal> {

    private static final long serialVersionUID = 21599418;

    /**
     * Setter for <code>quiz.quiz_pricing.id</code>.
     */
    public QuizPricingRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_pricing.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>quiz.quiz_pricing.quiz_id</code>.
     */
    public QuizPricingRecord setQuizId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_pricing.quiz_id</code>.
     */
    public Integer getQuizId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>quiz.quiz_pricing.price</code>.
     */
    public QuizPricingRecord setPrice(BigDecimal value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_pricing.price</code>.
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>quiz.quiz_pricing.status</code>.
     */
    public QuizPricingRecord setStatus(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_pricing.status</code>.
     */
    public String getStatus() {
        return (String) get(3);
    }

    /**
     * Setter for <code>quiz.quiz_pricing.stored</code>.
     */
    public QuizPricingRecord setStored(Timestamp value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_pricing.stored</code>.
     */
    public Timestamp getStored() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>quiz.quiz_pricing.original_price</code>.
     */
    public QuizPricingRecord setOriginalPrice(BigDecimal value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>quiz.quiz_pricing.original_price</code>.
     */
    public BigDecimal getOriginalPrice() {
        return (BigDecimal) get(5);
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
    public Row6<Integer, Integer, BigDecimal, String, Timestamp, BigDecimal> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, BigDecimal, String, Timestamp, BigDecimal> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return QuizPricing.QUIZ_PRICING.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return QuizPricing.QUIZ_PRICING.QUIZ_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return QuizPricing.QUIZ_PRICING.PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return QuizPricing.QUIZ_PRICING.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return QuizPricing.QUIZ_PRICING.STORED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return QuizPricing.QUIZ_PRICING.ORIGINAL_PRICE;
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
    public BigDecimal value3() {
        return getPrice();
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
    public BigDecimal value6() {
        return getOriginalPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord value2(Integer value) {
        setQuizId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord value3(BigDecimal value) {
        setPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord value4(String value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord value5(Timestamp value) {
        setStored(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord value6(BigDecimal value) {
        setOriginalPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizPricingRecord values(Integer value1, Integer value2, BigDecimal value3, String value4, Timestamp value5, BigDecimal value6) {
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
     * Create a detached QuizPricingRecord
     */
    public QuizPricingRecord() {
        super(QuizPricing.QUIZ_PRICING);
    }

    /**
     * Create a detached, initialised QuizPricingRecord
     */
    public QuizPricingRecord(Integer id, Integer quizId, BigDecimal price, String status, Timestamp stored, BigDecimal originalPrice) {
        super(QuizPricing.QUIZ_PRICING);

        set(0, id);
        set(1, quizId);
        set(2, price);
        set(3, status);
        set(4, stored);
        set(5, originalPrice);
    }
}
