package com.company.seed.basic.web.common.validation.annotation;

import java.lang.annotation.*;

/**
 * 标注校验参数form
 *
 * <p>处理方式</p>
 * <ui>
 *     <li>·返回json信息 {@link ValidationAnnotation.DealType#JSON}</li>
 *     <li>·抛出异常 {@link ValidationAnnotation.DealType#EXCEPTION}</li>
 * </ui>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidationAnnotation {
    /** 处理方式 **/
    DealType dealType() default DealType.JSON;

    enum DealType {JSON,EXCEPTION}
}
