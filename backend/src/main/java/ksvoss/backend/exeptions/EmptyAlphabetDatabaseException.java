package ksvoss.backend.exeptions;

public class EmptyAlphabetDatabaseException extends RuntimeException{


    public  EmptyAlphabetDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }

    public  EmptyAlphabetDatabaseException(){
        this("",null,false,false);
    }

}
