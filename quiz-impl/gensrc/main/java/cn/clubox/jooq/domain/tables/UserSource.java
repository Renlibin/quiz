/*
 * This file is generated by jOOQ.
*/
package cn.clubox.jooq.domain.tables;


import cn.clubox.jooq.domain.Keys;
import cn.clubox.jooq.domain.Quiz;
import cn.clubox.jooq.domain.tables.records.UserSourceRecord;

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
public class UserSource extends TableImpl<UserSourceRecord> {

    private static final long serialVersionUID = 572136608;

    /**
     * The reference instance of <code>quiz.user_source</code>
     */
    public static final UserSource USER_SOURCE = new UserSource();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSourceRecord> getRecordType() {
        return UserSourceRecord.class;
    }

    /**
     * The column <code>quiz.user_source.id</code>.
     */
    public final TableField<UserSourceRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.user_source.user_id</code>.
     */
    public final TableField<UserSourceRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.user_source.source</code>.
     */
    public final TableField<UserSourceRecord, String> SOURCE = createField("source", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

    /**
     * The column <code>quiz.user_source.ip_address</code>.
     */
    public final TableField<UserSourceRecord, String> IP_ADDRESS = createField("ip_address", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "");

    /**
     * The column <code>quiz.user_source.channel</code>.
     */
    public final TableField<UserSourceRecord, String> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

    /**
     * The column <code>quiz.user_source.stored</code>.
     */
    public final TableField<UserSourceRecord, Timestamp> STORED = createField("stored", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>quiz.user_source</code> table reference
     */
    public UserSource() {
        this("user_source", null);
    }

    /**
     * Create an aliased <code>quiz.user_source</code> table reference
     */
    public UserSource(String alias) {
        this(alias, USER_SOURCE);
    }

    private UserSource(String alias, Table<UserSourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSource(String alias, Table<UserSourceRecord> aliased, Field<?>[] parameters) {
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
    public Identity<UserSourceRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserSourceRecord> getPrimaryKey() {
        return Keys.KEY_USER_SOURCE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserSourceRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSourceRecord>>asList(Keys.KEY_USER_SOURCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserSourceRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserSourceRecord, ?>>asList(Keys.FK_USER_SOURCE_USER_USER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSource as(String alias) {
        return new UserSource(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserSource rename(String name) {
        return new UserSource(name, null);
    }
}
