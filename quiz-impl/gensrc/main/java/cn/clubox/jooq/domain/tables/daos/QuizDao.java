/*
 * This file is generated by jOOQ.
*/
package cn.clubox.jooq.domain.tables.daos;


import cn.clubox.jooq.domain.tables.Quiz;
import cn.clubox.jooq.domain.tables.records.QuizRecord;

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
public class QuizDao extends DAOImpl<QuizRecord, cn.clubox.jooq.domain.tables.pojos.Quiz, Integer> {

    /**
     * Create a new QuizDao without any configuration
     */
    public QuizDao() {
        super(Quiz.QUIZ_, cn.clubox.jooq.domain.tables.pojos.Quiz.class);
    }

    /**
     * Create a new QuizDao with an attached configuration
     */
    public QuizDao(Configuration configuration) {
        super(Quiz.QUIZ_, cn.clubox.jooq.domain.tables.pojos.Quiz.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.clubox.jooq.domain.tables.pojos.Quiz object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.clubox.jooq.domain.tables.pojos.Quiz> fetchById(Integer... values) {
        return fetch(Quiz.QUIZ_.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.clubox.jooq.domain.tables.pojos.Quiz fetchOneById(Integer value) {
        return fetchOne(Quiz.QUIZ_.ID, value);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<cn.clubox.jooq.domain.tables.pojos.Quiz> fetchByTitle(String... values) {
        return fetch(Quiz.QUIZ_.TITLE, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<cn.clubox.jooq.domain.tables.pojos.Quiz> fetchByDescription(String... values) {
        return fetch(Quiz.QUIZ_.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<cn.clubox.jooq.domain.tables.pojos.Quiz> fetchByStatus(String... values) {
        return fetch(Quiz.QUIZ_.STATUS, values);
    }

    /**
     * Fetch records that have <code>stored IN (values)</code>
     */
    public List<cn.clubox.jooq.domain.tables.pojos.Quiz> fetchByStored(Timestamp... values) {
        return fetch(Quiz.QUIZ_.STORED, values);
    }
}
