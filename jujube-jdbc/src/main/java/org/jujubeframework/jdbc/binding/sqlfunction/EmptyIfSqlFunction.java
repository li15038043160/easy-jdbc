package org.jujubeframework.jdbc.binding.sqlfunction;

/**
 * @author John Li
 */
public class EmptyIfSqlFunction implements IfSqlFunction {
    @Override
    public String toFreemarker(String originText) {
        return null;
    }
}