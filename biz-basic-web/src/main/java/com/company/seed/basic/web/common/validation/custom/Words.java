package com.company.seed.basic.web.common.validation.custom;

import com.company.seed.basic.web.common.validation.custom.validator.WordsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 被标注的字段必须是纯英文字词，适用于任何类型
 * @author yoara
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {WordsValidator.class})
public @interface Words {
    String message() default "{constraint.default.words.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}
