package com.company.seed.module.model.manager.annotation;

import com.company.seed.module.model.manager.enums.AuthorityAnnotationEnums;

import java.lang.annotation.*;

/**
 * Created by yoara on 2016/4/25.
 * 权限设定注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorityAnnotation {
    AuthorityAnnotationEnums value();
}
