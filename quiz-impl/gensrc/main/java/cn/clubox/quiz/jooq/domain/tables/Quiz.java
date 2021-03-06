/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables;


import cn.clubox.quiz.jooq.domain.Keys;
import cn.clubox.quiz.jooq.domain.tables.records.QuizRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Quiz extends TableImpl<QuizRecord> {

    private static final long serialVersionUID = -1649126552;

    /**
     * The reference instance of <code>quiz.quiz</code>
     */
    public static final Quiz QUIZ_ = new Quiz();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuizRecord> getRecordType() {
        return QuizRecord.class;
    }

    /**
     * The column <code>quiz.quiz.id</code>.
     */
    public final TableField<QuizRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.quiz.title</code>.
     */
    public final TableField<QuizRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>quiz.quiz.description</code>.
     */
    public final TableField<QuizRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>quiz.quiz.status</code>.
     */
    public final TableField<QuizRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "");

    /**
     * The column <code>quiz.quiz.stored</code>.
     */
    public final TableField<QuizRecord, Timestamp> STORED = createField("stored", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>quiz.quiz.quiz_type</code>.
     */
    public final TableField<QuizRecord, String> QUIZ_TYPE = createField("quiz_type", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>quiz.quiz.logo_src</code>.
     */
    public final TableField<QuizRecord, String> LOGO_SRC = createField("logo_src", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

    /**
     * The column <code>quiz.quiz.name</code>.
     */
    public final TableField<QuizRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * Create a <code>quiz.quiz</code> table reference
     */
    public Quiz() {
        this("quiz", null);
    }

    /**
     * Create an aliased <code>quiz.quiz</code> table reference
     */
    public Quiz(String alias) {
        this(alias, QUIZ_);
    }

    private Quiz(String alias, Table<QuizRecord> aliased) {
        this(alias, aliased, null);
    }

    private Quiz(String alias, Table<QuizRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return cn.clubox.quiz.jooq.domain.Quiz.QUIZ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<QuizRecord, Integer> getIdentity() {
        return Keys.IDENTITY_QUIZ_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<QuizRecord> getPrimaryKey() {
        return Keys.KEY_QUIZ_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<QuizRecord>> getKeys() {
        return Arrays.<UniqueKey<QuizRecord>>asList(Keys.KEY_QUIZ_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Quiz as(String alias) {
        return new Quiz(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Quiz rename(String name) {
        return new Quiz(name, null);
    }
}
