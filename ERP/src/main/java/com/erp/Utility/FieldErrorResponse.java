package com.erp.Utility;

import lombok.Getter;
<<<<<<< HEAD
import lombok.Setter;
=======
>>>>>>> 1364881d105d8c5555c89831df4b73eee6afcca7
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
<<<<<<< HEAD
@Setter
@SuperBuilder
public class FieldErrorResponse extends SimpleErrorResponse{

    private List<CustomFieldError> errors;


    public static CustomFieldError createfieldError(String message, Object rejectedvalue, String filed){
        CustomFieldError error = new CustomFieldError();
        error.message = message;
        error.rejectedValue = rejectedvalue;
        error.field = filed;

        return error;
    }

    @Getter
    public static class CustomFieldError {
         private String message;
         private Object rejectedValue;
         private String field;
    }
=======
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


>>>>>>> 1364881d105d8c5555c89831df4b73eee6afcca7
}
