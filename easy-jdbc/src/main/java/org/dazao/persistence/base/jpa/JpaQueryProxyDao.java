package org.dazao.persistence.base.jpa;

import java.lang.reflect.Method;
import java.util.List;

import org.dazao.persistence.base.BaseDao;
import org.dazao.persistence.base.jpa.strategy.FindAnyByAnyQuery;
import org.dazao.persistence.base.jpa.strategy.FindAnyByIdQuery;
import org.dazao.persistence.base.jpa.strategy.FindByAnyQuery;
import org.dazao.persistence.base.jpa.strategy.GetCountByAnyQuery;
import org.dazao.persistence.base.jpa.strategy.NoRealizeQuery;
import org.dazao.persistence.base.jpa.strategy.Querier;
import org.dazao.persistence.base.spec.Spec;
import org.dazao.support.entity.RecordEntity;
import org.dazao.util.Beans;
import org.springframework.jdbc.core.JdbcTemplate;

/** Jpa查询的代理Dao */
public class JpaQueryProxyDao extends BaseDao<RecordEntity> {

    private BaseDao<?> target;
    private Method curMethod;
    private Object[] methodArgs;

    public JpaQueryProxyDao(BaseDao<?> target, Method curMethod, Object[] methodArgs) {
        super();
        this.target = target;
        this.curMethod = curMethod;
        this.methodArgs = methodArgs;
    }

    @Override
    public String getTableName() {
        Method tmethod = Beans.getDeclaredMethod(target.getClass(), "getTableName");
        if (!tmethod.isAccessible()) {
            tmethod.setAccessible(true);
        }
        return (String) Beans.invoke(tmethod, target);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return target.getJdbcTemplate();
    }

    public Object jpaQuery() {
        return buildQuerier().query(curMethod, methodArgs);
    }

    /** 构建查询上下文 */
    private Querier buildQuerier() {
        Querier querier = new Querier();
        querier.addStrategy(new FindAnyByIdQuery(this));
        querier.addStrategy(new FindByAnyQuery(this));
        querier.addStrategy(new FindAnyByAnyQuery(this));
        querier.addStrategy(new GetCountByAnyQuery(this));
        querier.addStrategy(new NoRealizeQuery(this));
        return querier;
    }

    /** 获得真实Dao的泛型 */
    public Class<?> getClazz() {
        return target.getRealGenericType();
    }

    public RecordEntity findOne(Spec spec) {
        return super.findOne(spec);
    }

    public List<RecordEntity> find(Spec spec) {
        return super.find(spec);
    }

    public RecordEntity findOne(String fields, Spec spec) {
        return super.findOne(fields, spec);
    }

    public List<RecordEntity> find(String fields, Spec spec) {
        return super.find(fields, spec);
    }

    public long getCount(Spec spec) {
        return super.getCount(spec);
    }
}