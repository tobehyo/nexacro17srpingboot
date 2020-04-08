package com.tobesoft.overseas.nexacro17.demo.test;

public @interface ParamDataSet {
    String name();

    /**
     * Whether the parameter is required.
     * <p>Default is {@code true}, leading to an exception thrown in case
     * of the parameter missing in the request. Switch this to {@code false}
     * if you prefer a {@code null} in case of the parameter missing.
     */
    boolean required() default true;
}

