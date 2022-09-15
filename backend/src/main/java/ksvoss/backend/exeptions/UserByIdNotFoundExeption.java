package ksvoss.backend.exeptions;

public class UserByIdNotFoundExeption extends RuntimeException{
    public UserByIdNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message,null,false,false);
    }
    public UserByIdNotFoundExeption()
    {
        this("",null,false,false);
    }
}



