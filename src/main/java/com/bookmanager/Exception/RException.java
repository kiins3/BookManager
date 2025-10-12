package com.bookmanager.Exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RException extends  RuntimeException{

    private ErrorCode errorCode;

    public RException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
