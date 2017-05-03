/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.daos;


import cn.clubox.quiz.jooq.domain.tables.QuizPricing;
import cn.clubox.quiz.jooq.domain.tables.records.QuizPricingRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class QuizPricingDao extends DAOImpl<QuizPricingRecord, cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing, Integer> {

    /**
     * Create a new QuizPricingDao without any configuration
     */
    public QuizPricingDao() {
        super(QuizPricing.QUIZ_PRICING, cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing.class);
    }

    /**
     * Create a new QuizPricingDao with an attached configuration
     */
    public QuizPricingDao(Configuration configuration) {
        super(QuizPricing.QUIZ_PRICING, cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing> fetchById(Integer... values) {
        return fetch(QuizPricing.QUIZ_PRICING.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing fetchOneById(Integer value) {
        return fetchOne(QuizPricing.QUIZ_PRICING.ID, value);
    }

    /**
     * Fetch records that have <code>quiz_id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing> fetchByQuizId(Integer... values) {
        return fetch(QuizPricing.QUIZ_PRICING.QUIZ_ID, values);
    }

    /**
     * Fetch records that have <code>price IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing> fetchByPrice(BigDecimal... values) {
        return fetch(QuizPricing.QUIZ_PRICING.PRICE, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing> fetchByStatus(String... values) {
        return fetch(QuizPricing.QUIZ_PRICING.STATUS, values);
    }

    /**
     * Fetch records that have <code>stored IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizPricing> fetchByStored(Timestamp... values) {
        return fetch(QuizPricing.QUIZ_PRICING.STORED, values);
    }
}
