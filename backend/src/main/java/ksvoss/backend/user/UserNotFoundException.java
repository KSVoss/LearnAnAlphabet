package ksvoss.backend.user;

public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }

    public UserNotFoundException(){
        this("",null,false,false);
    }
}