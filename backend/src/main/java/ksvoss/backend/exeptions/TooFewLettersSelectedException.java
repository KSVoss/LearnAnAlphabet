package ksvoss.backend.exeptions;

public class TooFewLettersSelectedException extends RuntimeException{
    public TooFewLettersSelectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }

    public TooFewLettersSelectedException(){
        this("",null,false,false);
    }
}
