package ksvoss.backend.exeptions;

public class SelectedAlphabetIsNullException extends RuntimeException{


    public SelectedAlphabetIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }

    public SelectedAlphabetIsNullException(){
        this("",null,false,false);
    }

    public SelectedAlphabetIsNullException(int id) {
        this("The alphabet with id:"+id+" does not exists",null,false,false);
    }
}
