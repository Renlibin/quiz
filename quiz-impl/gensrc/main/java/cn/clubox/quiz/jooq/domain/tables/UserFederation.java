/*
 * This file is generated by jOOQ.
*/
package cn.clubox.quiz.jooq.domain.tables;


import cn.clubox.quiz.jooq.domain.Keys;
import cn.clubox.quiz.jooq.domain.Quiz;
import cn.clubox.quiz.jooq.domain.tables.records.UserFederationRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class UserFederation extends TableImpl<UserFederationRecord> {

    private static final long serialVersionUID = -1400892838;

    /**
     * The reference instance of <code>quiz.user_federation</code>
     */
    public static final UserFederation USER_FEDERATION = new UserFederation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserFederationRecord> getRecordType() {
        return UserFederationRecord.class;
    }

    /**
     * The column <code>quiz.user_federation.id</code>.
     */
    public final TableField<UserFederationRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.user_federation.user_id</code>.
     */
    public final TableField<UserFederationRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.user_federation.federation_id</code>.
     */
    public final TableField<UserFederationRecord, String> FEDERATION_ID = createField("federation_id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>quiz.user_federation.status</code>.
     */
    public final TableField<UserFederationRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "");

    /**
     * The column <code>quiz.user_federation.stored</code>.
     */
    public final TableField<UserFederationRecord, Timestamp> STORED = createField("stored", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>quiz.user_federation</code> table reference
     */
    public UserFederation() {
        this("user_federation", null);
    }

    /**
     * Create an aliased <code>quiz.user_federation</code> table reference
     */
    public UserFederation(String alias) {
        this(alias, USER_FEDERATION);
    }

    private UserFederation(String alias, Table<UserFederationRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserFederation(String alias, Table<UserFederationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Quiz.QUIZ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserFederationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_FEDERATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserFederationRecord> getPrimaryKey() {
        return Keys.KEY_USER_FEDERATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserFederationRecord>> getKeys() {
        return Arrays.<UniqueKey<UserFederationRecord>>asList(Keys.KEY_USER_FEDERATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserFederationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserFederationRecord, ?>>asList(Keys.FK_USER_FEDERATION_USER_USER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserFederation as(String alias) {
        return new UserFederation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserFederation rename(String name) {
        return new UserFederation(name, null);
    }
}
