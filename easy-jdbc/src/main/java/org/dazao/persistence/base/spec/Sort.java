package org.dazao.persistence.base.spec;

import java.util.ArrayList;
import java.util.List;

import org.dazao.persistence.base.util.Sqls;

/**
 * 排序规则<br>
 * 排序的构建规则是：如果排序字段以“_D”结尾，则倒序；否则，就是正序
 */
public class Sort {
    private List<String> sorts = new ArrayList<String>();
    /** 不计入排序的字段 */
    public static final String DEFAULT = "default";
    /** 倒序后缀 */
    private static final String DESC_SUFFIX = "_D";

    private Spec spec;

    public Sort(Spec spec) {
        super();
        this.spec = spec;
    }

    /**
     * 正序
     */
    public Sort asc(String field) {
        if (!field.equals(DEFAULT) && !sorts.contains(field)) {
            sorts.add(field);
        }
        return this;
    }

    /**
     * 倒序
     */
    public Sort desc(String field) {
        String sort = field + DESC_SUFFIX;
        if (!field.equals(DEFAULT) && !sorts.contains(sort)) {
            sorts.add(sort);
        }
        return this;
    }

    public Spec end() {
        return spec;
    }

    /**
     * 构建联合查询排序规则
     */
    public String buildSqlSort() {
        if (sorts.size() != 0) {
            StringBuffer sql = new StringBuffer(" order by ");
            // 排序的构建规则是：如果排序字段以“_D”结尾，则倒序；否则，就是正序
            for (String s : sorts) {
                if (s.endsWith(DESC_SUFFIX)) {
                    sql.append(Sqls.getSecurityFieldName(removeSuffix(s)) + " desc,");
                } else {
                    sql.append(Sqls.getSecurityFieldName(s) + ",");
                }
            }
            return sql.toString().substring(0, sql.length() - 1);
        }
        return "";
    }

    private String removeSuffix(String field) {
        return field.substring(0, field.length() - 2);
    }

    public void cleanValues() {
        sorts.clear();
    }

    public boolean isEmpty() {
        return sorts.isEmpty();
    }

    public Sort clone(Spec spec) {
        Sort sort = new Sort(spec);
        sort.sorts = new ArrayList<String>(this.sorts);
        return sort;
    }

    @Override
    public String toString() {
        return "Sort [" + buildSqlSort() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sorts == null) ? 0 : sorts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sort other = (Sort) obj;
        if (sorts == null) {
            if (other.sorts != null)
                return false;
        } else if (!sorts.equals(other.sorts))
            return false;
        return true;
    }

}