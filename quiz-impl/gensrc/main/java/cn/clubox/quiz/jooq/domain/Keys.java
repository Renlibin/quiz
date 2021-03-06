/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain;


import cn.clubox.quiz.jooq.domain.tables.Quiz;
import cn.clubox.quiz.jooq.domain.tables.QuizEngagement;
import cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult;
import cn.clubox.quiz.jooq.domain.tables.QuizPricing;
import cn.clubox.quiz.jooq.domain.tables.QuizQuestion;
import cn.clubox.quiz.jooq.domain.tables.QuizQuestionOption;
import cn.clubox.quiz.jooq.domain.tables.User;
import cn.clubox.quiz.jooq.domain.tables.UserFederation;
import cn.clubox.quiz.jooq.domain.tables.UserPayment;
import cn.clubox.quiz.jooq.domain.tables.UserSource;
import cn.clubox.quiz.jooq.domain.tables.records.QuizEngagementRecord;
import cn.clubox.quiz.jooq.domain.tables.records.QuizEngagementResultRecord;
import cn.clubox.quiz.jooq.domain.tables.records.QuizPricingRecord;
import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionOptionRecord;
import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionRecord;
import cn.clubox.quiz.jooq.domain.tables.records.QuizRecord;
import cn.clubox.quiz.jooq.domain.tables.records.UserFederationRecord;
import cn.clubox.quiz.jooq.domain.tables.records.UserPaymentRecord;
import cn.clubox.quiz.jooq.domain.tables.records.UserRecord;
import cn.clubox.quiz.jooq.domain.tables.records.UserSourceRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>quiz</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<QuizRecord, Integer> IDENTITY_QUIZ_ = Identities0.IDENTITY_QUIZ_;
    public static final Identity<QuizEngagementRecord, Integer> IDENTITY_QUIZ_ENGAGEMENT = Identities0.IDENTITY_QUIZ_ENGAGEMENT;
    public static final Identity<QuizEngagementResultRecord, Integer> IDENTITY_QUIZ_ENGAGEMENT_RESULT = Identities0.IDENTITY_QUIZ_ENGAGEMENT_RESULT;
    public static final Identity<QuizPricingRecord, Integer> IDENTITY_QUIZ_PRICING = Identities0.IDENTITY_QUIZ_PRICING;
    public static final Identity<QuizQuestionRecord, Integer> IDENTITY_QUIZ_QUESTION = Identities0.IDENTITY_QUIZ_QUESTION;
    public static final Identity<QuizQuestionOptionRecord, Integer> IDENTITY_QUIZ_QUESTION_OPTION = Identities0.IDENTITY_QUIZ_QUESTION_OPTION;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<UserFederationRecord, Integer> IDENTITY_USER_FEDERATION = Identities0.IDENTITY_USER_FEDERATION;
    public static final Identity<UserPaymentRecord, Integer> IDENTITY_USER_PAYMENT = Identities0.IDENTITY_USER_PAYMENT;
    public static final Identity<UserSourceRecord, Integer> IDENTITY_USER_SOURCE = Identities0.IDENTITY_USER_SOURCE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<QuizRecord> KEY_QUIZ_PRIMARY = UniqueKeys0.KEY_QUIZ_PRIMARY;
    public static final UniqueKey<QuizEngagementRecord> KEY_QUIZ_ENGAGEMENT_PRIMARY = UniqueKeys0.KEY_QUIZ_ENGAGEMENT_PRIMARY;
    public static final UniqueKey<QuizEngagementResultRecord> KEY_QUIZ_ENGAGEMENT_RESULT_PRIMARY = UniqueKeys0.KEY_QUIZ_ENGAGEMENT_RESULT_PRIMARY;
    public static final UniqueKey<QuizPricingRecord> KEY_QUIZ_PRICING_PRIMARY = UniqueKeys0.KEY_QUIZ_PRICING_PRIMARY;
    public static final UniqueKey<QuizQuestionRecord> KEY_QUIZ_QUESTION_PRIMARY = UniqueKeys0.KEY_QUIZ_QUESTION_PRIMARY;
    public static final UniqueKey<QuizQuestionOptionRecord> KEY_QUIZ_QUESTION_OPTION_PRIMARY = UniqueKeys0.KEY_QUIZ_QUESTION_OPTION_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_NAME_AK = UniqueKeys0.KEY_USER_NAME_AK;
    public static final UniqueKey<UserFederationRecord> KEY_USER_FEDERATION_PRIMARY = UniqueKeys0.KEY_USER_FEDERATION_PRIMARY;
    public static final UniqueKey<UserPaymentRecord> KEY_USER_PAYMENT_PRIMARY = UniqueKeys0.KEY_USER_PAYMENT_PRIMARY;
    public static final UniqueKey<UserSourceRecord> KEY_USER_SOURCE_PRIMARY = UniqueKeys0.KEY_USER_SOURCE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<QuizEngagementRecord, QuizRecord> FK_QUIZ_ENGAGEMENT_QUIZ_QUIZ_ID = ForeignKeys0.FK_QUIZ_ENGAGEMENT_QUIZ_QUIZ_ID;
    public static final ForeignKey<QuizEngagementRecord, UserRecord> FK_QUIZ_ENGAGEMENT_USER_USER_ID = ForeignKeys0.FK_QUIZ_ENGAGEMENT_USER_USER_ID;
    public static final ForeignKey<QuizEngagementResultRecord, QuizEngagementRecord> FK_QUIZ_ENGAGEMENT_RESULT_QUIZ_ENGAGEMENT_ID = ForeignKeys0.FK_QUIZ_ENGAGEMENT_RESULT_QUIZ_ENGAGEMENT_ID;
    public static final ForeignKey<QuizPricingRecord, QuizRecord> FK_QUIZ_PRICING_QUIZ_QUIZ_ID = ForeignKeys0.FK_QUIZ_PRICING_QUIZ_QUIZ_ID;
    public static final ForeignKey<QuizQuestionRecord, QuizRecord> FK_QUIZ_QUESTION_QUIZ_QUIZ_ID = ForeignKeys0.FK_QUIZ_QUESTION_QUIZ_QUIZ_ID;
    public static final ForeignKey<QuizQuestionOptionRecord, QuizQuestionRecord> FK_QUIZ_QUESTION_OPTION_QUIZ_QUESTION = ForeignKeys0.FK_QUIZ_QUESTION_OPTION_QUIZ_QUESTION;
    public static final ForeignKey<UserFederationRecord, UserRecord> FK_USER_FEDERATION_USER_USER_ID = ForeignKeys0.FK_USER_FEDERATION_USER_USER_ID;
    public static final ForeignKey<UserPaymentRecord, UserRecord> FK_USER_PAYMENT_USER_USER_ID = ForeignKeys0.FK_USER_PAYMENT_USER_USER_ID;
    public static final ForeignKey<UserPaymentRecord, QuizRecord> FK_USER_PAYMENT_QUIZ_QUIZ_ID = ForeignKeys0.FK_USER_PAYMENT_QUIZ_QUIZ_ID;
    public static final ForeignKey<UserSourceRecord, UserRecord> FK_USER_SOURCE_USER_USER_ID = ForeignKeys0.FK_USER_SOURCE_USER_USER_ID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<QuizRecord, Integer> IDENTITY_QUIZ_ = createIdentity(Quiz.QUIZ_, Quiz.QUIZ_.ID);
        public static Identity<QuizEngagementRecord, Integer> IDENTITY_QUIZ_ENGAGEMENT = createIdentity(QuizEngagement.QUIZ_ENGAGEMENT, QuizEngagement.QUIZ_ENGAGEMENT.ID);
        public static Identity<QuizEngagementResultRecord, Integer> IDENTITY_QUIZ_ENGAGEMENT_RESULT = createIdentity(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT, QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.ID);
        public static Identity<QuizPricingRecord, Integer> IDENTITY_QUIZ_PRICING = createIdentity(QuizPricing.QUIZ_PRICING, QuizPricing.QUIZ_PRICING.ID);
        public static Identity<QuizQuestionRecord, Integer> IDENTITY_QUIZ_QUESTION = createIdentity(QuizQuestion.QUIZ_QUESTION, QuizQuestion.QUIZ_QUESTION.ID);
        public static Identity<QuizQuestionOptionRecord, Integer> IDENTITY_QUIZ_QUESTION_OPTION = createIdentity(QuizQuestionOption.QUIZ_QUESTION_OPTION, QuizQuestionOption.QUIZ_QUESTION_OPTION.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
        public static Identity<UserFederationRecord, Integer> IDENTITY_USER_FEDERATION = createIdentity(UserFederation.USER_FEDERATION, UserFederation.USER_FEDERATION.ID);
        public static Identity<UserPaymentRecord, Integer> IDENTITY_USER_PAYMENT = createIdentity(UserPayment.USER_PAYMENT, UserPayment.USER_PAYMENT.ID);
        public static Identity<UserSourceRecord, Integer> IDENTITY_USER_SOURCE = createIdentity(UserSource.USER_SOURCE, UserSource.USER_SOURCE.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<QuizRecord> KEY_QUIZ_PRIMARY = createUniqueKey(Quiz.QUIZ_, "KEY_quiz_PRIMARY", Quiz.QUIZ_.ID);
        public static final UniqueKey<QuizEngagementRecord> KEY_QUIZ_ENGAGEMENT_PRIMARY = createUniqueKey(QuizEngagement.QUIZ_ENGAGEMENT, "KEY_quiz_engagement_PRIMARY", QuizEngagement.QUIZ_ENGAGEMENT.ID);
        public static final UniqueKey<QuizEngagementResultRecord> KEY_QUIZ_ENGAGEMENT_RESULT_PRIMARY = createUniqueKey(QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT, "KEY_quiz_engagement_result_PRIMARY", QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.ID);
        public static final UniqueKey<QuizPricingRecord> KEY_QUIZ_PRICING_PRIMARY = createUniqueKey(QuizPricing.QUIZ_PRICING, "KEY_quiz_pricing_PRIMARY", QuizPricing.QUIZ_PRICING.ID);
        public static final UniqueKey<QuizQuestionRecord> KEY_QUIZ_QUESTION_PRIMARY = createUniqueKey(QuizQuestion.QUIZ_QUESTION, "KEY_quiz_question_PRIMARY", QuizQuestion.QUIZ_QUESTION.ID);
        public static final UniqueKey<QuizQuestionOptionRecord> KEY_QUIZ_QUESTION_OPTION_PRIMARY = createUniqueKey(QuizQuestionOption.QUIZ_QUESTION_OPTION, "KEY_quiz_question_option_PRIMARY", QuizQuestionOption.QUIZ_QUESTION_OPTION.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserRecord> KEY_USER_NAME_AK = createUniqueKey(User.USER, "KEY_user_name_ak", User.USER.NAME);
        public static final UniqueKey<UserFederationRecord> KEY_USER_FEDERATION_PRIMARY = createUniqueKey(UserFederation.USER_FEDERATION, "KEY_user_federation_PRIMARY", UserFederation.USER_FEDERATION.ID);
        public static final UniqueKey<UserPaymentRecord> KEY_USER_PAYMENT_PRIMARY = createUniqueKey(UserPayment.USER_PAYMENT, "KEY_user_payment_PRIMARY", UserPayment.USER_PAYMENT.ID);
        public static final UniqueKey<UserSourceRecord> KEY_USER_SOURCE_PRIMARY = createUniqueKey(UserSource.USER_SOURCE, "KEY_user_source_PRIMARY", UserSource.USER_SOURCE.ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<QuizEngagementRecord, QuizRecord> FK_QUIZ_ENGAGEMENT_QUIZ_QUIZ_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_QUIZ_PRIMARY, QuizEngagement.QUIZ_ENGAGEMENT, "fk_quiz_engagement_quiz_quiz_id", QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID);
        public static final ForeignKey<QuizEngagementRecord, UserRecord> FK_QUIZ_ENGAGEMENT_USER_USER_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_USER_PRIMARY, QuizEngagement.QUIZ_ENGAGEMENT, "fk_quiz_engagement_user_user_id", QuizEngagement.QUIZ_ENGAGEMENT.USER_ID);
        public static final ForeignKey<QuizEngagementResultRecord, QuizEngagementRecord> FK_QUIZ_ENGAGEMENT_RESULT_QUIZ_ENGAGEMENT_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_QUIZ_ENGAGEMENT_PRIMARY, QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT, "fk_quiz_engagement_result_quiz_engagement_id", QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT.QUIZ_ENGAGEMENT_ID);
        public static final ForeignKey<QuizPricingRecord, QuizRecord> FK_QUIZ_PRICING_QUIZ_QUIZ_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_QUIZ_PRIMARY, QuizPricing.QUIZ_PRICING, "fk_quiz_pricing_quiz_quiz_id", QuizPricing.QUIZ_PRICING.QUIZ_ID);
        public static final ForeignKey<QuizQuestionRecord, QuizRecord> FK_QUIZ_QUESTION_QUIZ_QUIZ_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_QUIZ_PRIMARY, QuizQuestion.QUIZ_QUESTION, "fk_quiz_question_quiz_quiz_id", QuizQuestion.QUIZ_QUESTION.QUIZ_ID);
        public static final ForeignKey<QuizQuestionOptionRecord, QuizQuestionRecord> FK_QUIZ_QUESTION_OPTION_QUIZ_QUESTION = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_QUIZ_QUESTION_PRIMARY, QuizQuestionOption.QUIZ_QUESTION_OPTION, "fk_quiz_question_option_quiz_question", QuizQuestionOption.QUIZ_QUESTION_OPTION.QUIZ_QUESTION_ID);
        public static final ForeignKey<UserFederationRecord, UserRecord> FK_USER_FEDERATION_USER_USER_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_USER_PRIMARY, UserFederation.USER_FEDERATION, "fk_user_federation_user_user_id", UserFederation.USER_FEDERATION.USER_ID);
        public static final ForeignKey<UserPaymentRecord, UserRecord> FK_USER_PAYMENT_USER_USER_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_USER_PRIMARY, UserPayment.USER_PAYMENT, "fk_user_payment_user_user_id", UserPayment.USER_PAYMENT.USER_ID);
        public static final ForeignKey<UserPaymentRecord, QuizRecord> FK_USER_PAYMENT_QUIZ_QUIZ_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_QUIZ_PRIMARY, UserPayment.USER_PAYMENT, "fk_user_payment_quiz_quiz_id", UserPayment.USER_PAYMENT.QUIZ_ID);
        public static final ForeignKey<UserSourceRecord, UserRecord> FK_USER_SOURCE_USER_USER_ID = createForeignKey(cn.clubox.quiz.jooq.domain.Keys.KEY_USER_PRIMARY, UserSource.USER_SOURCE, "fk_user_source_user_user_id", UserSource.USER_SOURCE.USER_ID);
    }
}
