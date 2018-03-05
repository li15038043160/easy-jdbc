package org.dazao.persistence.base.dialect;

import java.util.List;

import org.dazao.lang.Record;

/** jdbc方言 */
public interface Dialect {

    public static final Dialect DEFAULT = new MysqlDialect();

    static final String ALL_FIELDS = "*";

    String forDbSimpleQuery(String fields, String tableName);

    String forDbSimpleQuery(String fields, String tableName, String filters);

    String forDbFindById(String tableName, String primaryKey, String columns);

    String forDbDeleteById(String tableName, String primaryKey);

    String forDbDelete(String tableName, String filters);

    String forDbSave(String tableName, Record record, List<Object> paras);

    String forDbUpdate(String tableName, String primaryKey, Object id, Record record, List<Object> paras);

    /**
     * 获取分页sql
     */
    String forDbPaginationQuery(String origSql, int start, int size);

}