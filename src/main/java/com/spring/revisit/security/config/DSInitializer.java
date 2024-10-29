package com.spring.revisit.security.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DSInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Instructing DS whenever it will be started to create
        return new Class[] {ProjectConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        //Anything which starts with / will be handled by our DS.
        return new String[] {"/"};
    }
}
