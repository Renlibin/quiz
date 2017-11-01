/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.daos;


import cn.clubox.quiz.jooq.domain.tables.QuizQuestionOption;
import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionOptionRecord;

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
public class QuizQuestionOptionDao extends DAOImpl<QuizQuestionOptionRecord, cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption, Integer> {

    /**
     * Create a new QuizQuestionOptionDao without any configuration
     */
    public QuizQuestionOptionDao() {
        super(QuizQuestionOption.QUIZ_QUESTION_OPTION, cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption.class);
    }

    /**
     * Create a new QuizQuestionOptionDao with an attached configuration
     */
    public QuizQuestionOptionDao(Configuration configuration) {
        super(QuizQuestionOption.QUIZ_QUESTION_OPTION, cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption> fetchById(Integer... values) {
        return fetch(QuizQuestionOption.QUIZ_QUESTION_OPTION.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption fetchOneById(Integer value) {
        return fetchOne(QuizQuestionOption.QUIZ_QUESTION_OPTION.ID, value);
    }

    /**
     * Fetch records that have <code>quiz_question_id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption> fetchByQuizQuestionId(Integer... values) {
        return fetch(QuizQuestionOption.QUIZ_QUESTION_OPTION.QUIZ_QUESTION_ID, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption> fetchByTitle(String... values) {
        return fetch(QuizQuestionOption.QUIZ_QUESTION_OPTION.TITLE, values);
    }

    /**
     * Fetch records that have <code>stored IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption> fetchByStored(Timestamp... values) {
        return fetch(QuizQuestionOption.QUIZ_QUESTION_OPTION.STORED, values);
    }

    /**
     * Fetch records that have <code>score IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption> fetchByScore(Short... values) {
        return fetch(QuizQuestionOption.QUIZ_QUESTION_OPTION.SCORE, values);
    }

    /**
     * Fetch records that have <code>sequence_number IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizQuestionOption> fetchBySequenceNumber(Short... values) {
        return fetch(QuizQuestionOption.QUIZ_QUESTION_OPTION.SEQUENCE_NUMBER, values);
    }
}
