package com.erp.Utility;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class FieldErrorResponse extends SimpleErrorResponse{

    private List<CustomFieldError> errorList;

    public static CustomFieldError createFieldError(String message, Object rejectValue,String field){
        CustomFieldError customFieldError =  new CustomFieldError();
        customFieldError.message=message;
        customFieldError.rejectValue=rejectValue;
        customFieldError.field=field;
        return customFieldError;
    }

    @Getter
    public static class CustomFieldError{
        private String message;
        private Object rejectValue;
        private String field;

    }


}
