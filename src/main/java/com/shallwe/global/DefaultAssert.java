package com.shallwe.global;

import java.util.List;
import java.util.Optional;

import com.shallwe.domain.user.exception.InvalidUserException;
import com.shallwe.global.error.DefaultAuthenticationException;


import com.shallwe.global.error.DefaultNullPointerException;
import com.shallwe.global.error.InvalidParameterException;
import com.shallwe.global.payload.ErrorCode;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public class DefaultAssert extends Assert{

    public static void isTrue(boolean value){
        if(!value){
            throw new InvalidUserException();
        }
    }

    public static void isTrue(boolean value, String message){
        if(!value){
            throw new InvalidUserException();
        }
    }

    public static void isValidParameter(Errors errors){
        if(errors.hasErrors()){
            throw new InvalidParameterException(errors);
        }
    }

    public static void isObjectNull(Object object){
        if(object == null){
            throw new DefaultNullPointerException(ErrorCode.INVALID_CHECK);
        }
    }

    public static void isListNull(List<Object> values){
        if(values.isEmpty()){
            throw new InvalidUserException();
        }
    }

    public static void isListNull(Object[] values){
        if(values == null){
            throw new InvalidUserException();
        }
    }

    public static void isOptionalPresent(Optional<?> value){
        if(!value.isPresent()){
            throw new InvalidUserException();
        }
    }

    public static void isAuthentication(String message){
        throw new DefaultAuthenticationException(message);
    }

    public static void isAuthentication(boolean value){
        if(!value){
            throw new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }
}
