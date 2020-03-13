package com.newland.ignite.sqlfunction;


import org.apache.ignite.cache.query.annotations.QuerySqlFunction;

/**
 *
 */
public class ParseServer {

    @QuerySqlFunction
    public static int sqr(int x) {
        return x * x;
    }

}
