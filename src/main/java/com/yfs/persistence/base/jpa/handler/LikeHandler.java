package com.yfs.persistence.base.jpa.handler;

import java.util.List;

import com.yfs.persistence.base.spec.Spec;

/** Like Handler */
public class LikeHandler implements Handler {
    private static final String LIKE = "Like";

    @Override
    public void handler(Spec spec, String methodName, List<Object> args, HandlerChain chain) {
        if (methodName.endsWith(LIKE)) {
            String field = methodName.replace(LIKE, EMPTY);
            field = Handler.realField(field);
            spec.like(field, args.get(0));
            args.remove(0);
        } else {
            chain.handler(spec, methodName, args);
        }
    }

}
