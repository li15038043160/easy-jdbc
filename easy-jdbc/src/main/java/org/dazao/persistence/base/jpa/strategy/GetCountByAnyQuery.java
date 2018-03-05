package org.dazao.persistence.base.jpa.strategy;

import java.lang.reflect.Method;

import org.dazao.persistence.base.jpa.JpaQueryProxyDao;
import org.dazao.persistence.base.jpa.handler.DefaultHandlerChain;
import org.dazao.persistence.base.jpa.handler.HandlerContext;
import org.dazao.persistence.base.spec.Spec;

import com.google.common.collect.Lists;

public class GetCountByAnyQuery extends QueryStrategy {

    private static final String GET_COUNT_BY = "getCountBy";

    public GetCountByAnyQuery(JpaQueryProxyDao proxyDao) {
        super(proxyDao);
    }

    @Override
    boolean accept(Method method) {
        return method.getName().startsWith(GET_COUNT_BY);
    }

    @Override
    Object query(Method method, Object[] args) {
        String mname = method.getName();
        String tmname = mname.replaceAll(GET_COUNT_BY, EMPTY);
        Spec spec = Spec.newS();
        DefaultHandlerChain selfChain = new DefaultHandlerChain();
        selfChain.addHandlers(HandlerContext.complexHandler);
        selfChain.addHandlers(HandlerContext.simpleHandler);
        selfChain.handler(spec, tmname, Lists.newArrayList(args));
        return proxyDao.getCount(spec);
    }

}