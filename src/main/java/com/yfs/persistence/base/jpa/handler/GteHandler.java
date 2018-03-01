package com.yfs.persistence.base.jpa.handler;

import java.util.List;

import com.yfs.persistence.base.spec.Spec;

/** Like Handler */
public class GteHandler implements Handler {
    private static final String GTE = "Gte";

    @Override
    public void handler(Spec spec, String methodName, List<Object> args, HandlerChain chain) {
        if (methodName.endsWith(GTE)) {
            String field = methodName.replace(GTE, EMPTY);
            field = Handler.realField(field);
            spec.gte(field, args.get(0));
            args.remove(0);
        } else {
            chain.handler(spec, methodName, args);
        }
    }

}
