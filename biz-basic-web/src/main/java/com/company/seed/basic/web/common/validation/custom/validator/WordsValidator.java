package com.company.seed.basic.web.common.validation.custom.validator;

import com.company.seed.basic.web.common.validation.custom.Words;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验 {@link Words}
 * Created by yoara on 2016/12/27.
 */
public class WordsValidator implements ConstraintValidator<Words,String> {
    @Override
    public void initialize(Words constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isAlpha(value);
    }
}
