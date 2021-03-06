/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.daos;


import cn.clubox.quiz.jooq.domain.tables.UserPayment;
import cn.clubox.quiz.jooq.domain.tables.records.UserPaymentRecord;

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
public class UserPaymentDao extends DAOImpl<UserPaymentRecord, cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment, Integer> {

    /**
     * Create a new UserPaymentDao without any configuration
     */
    public UserPaymentDao() {
        super(UserPayment.USER_PAYMENT, cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment.class);
    }

    /**
     * Create a new UserPaymentDao with an attached configuration
     */
    public UserPaymentDao(Configuration configuration) {
        super(UserPayment.USER_PAYMENT, cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment> fetchById(Integer... values) {
        return fetch(UserPayment.USER_PAYMENT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment fetchOneById(Integer value) {
        return fetchOne(UserPayment.USER_PAYMENT.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment> fetchByUserId(Integer... values) {
        return fetch(UserPayment.USER_PAYMENT.USER_ID, values);
    }

    /**
     * Fetch records that have <code>quiz_id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment> fetchByQuizId(Integer... values) {
        return fetch(UserPayment.USER_PAYMENT.QUIZ_ID, values);
    }

    /**
     * Fetch records that have <code>amount IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment> fetchByAmount(BigDecimal... values) {
        return fetch(UserPayment.USER_PAYMENT.AMOUNT, values);
    }

    /**
     * Fetch records that have <code>stored IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment> fetchByStored(Timestamp... values) {
        return fetch(UserPayment.USER_PAYMENT.STORED, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment> fetchByStatus(String... values) {
        return fetch(UserPayment.USER_PAYMENT.STATUS, values);
    }
}
