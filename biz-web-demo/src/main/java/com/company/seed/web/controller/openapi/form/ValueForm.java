package com.company.seed.web.controller.openapi.form;

import com.company.seed.basic.web.common.validation.ValidationForm;
import com.company.seed.basic.web.common.validation.custom.Words;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by yoara on 2016/6/13.
 */
public class ValueForm extends ValidationForm implements Serializable{
    @Min(1)
    private int valueInt;
    @Words
    private String valueString;
    @Email
    private String email;

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
