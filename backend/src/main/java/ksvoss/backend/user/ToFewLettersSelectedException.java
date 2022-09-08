package ksvoss.backend.user;

public class ToFewLettersSelectedException extends RuntimeException{
    public ToFewLettersSelectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }

    public ToFewLettersSelectedException(){
        this("",null,false,false);
    }
}
