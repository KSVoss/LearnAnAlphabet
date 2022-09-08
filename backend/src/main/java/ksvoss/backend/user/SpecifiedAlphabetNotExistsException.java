package ksvoss.backend.user;

public class SpecifiedAlphabetNotExistsException extends RuntimeException{


    public  SpecifiedAlphabetNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }

    public  SpecifiedAlphabetNotExistsException(String message){
        this(message,null,false,false);
    }

}
