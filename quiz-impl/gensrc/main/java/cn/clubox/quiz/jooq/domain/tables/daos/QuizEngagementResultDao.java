/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables.daos;


import cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult;
import cn.clubox.quiz.jooq.domain.tables.records.QuizEngagementResultRecord;

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
public class QuizEngagementResultDao extends DAOImpl<QuizEngagementResultRecord, cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult, Integer> {

    /**
     * Create a new QuizEngagementResultDao without any configuration
     */
    public QuizEngagementResultDao() {
        super(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT, cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult.class);
    }

    /**
     * Create a new QuizEngagementResultDao with an attached configuration
     */
    public QuizEngagementResultDao(Configuration configuration) {
        super(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT, cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult> fetchById(Integer... values) {
        return fetch(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult fetchOneById(Integer value) {
        return fetchOne(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.ID, value);
    }

    /**
     * Fetch records that have <code>quiz_engagement_id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult> fetchByQuizEngagementId(Integer... values) {
        return fetch(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.QUIZ_ENGAGEMENT_ID, values);
    }

    /**
     * Fetch records that have <code>score IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult> fetchByScore(Short... values) {
        return fetch(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.SCORE, values);
    }

    /**
     * Fetch records that have <code>result_option IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult> fetchByResultOption(String... values) {
        return fetch(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.RESULT_OPTION, values);
    }

    /**
     * Fetch records that have <code>stored IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult> fetchByStored(Timestamp... values) {
        return fetch(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.STORED, values);
    }

    /**
     * Fetch records that have <code>question_id IN (values)</code>
     */
    public List<cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult> fetchByQuestionId(Short... values) {
        return fetch(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.QUESTION_ID, values);
    }
}
