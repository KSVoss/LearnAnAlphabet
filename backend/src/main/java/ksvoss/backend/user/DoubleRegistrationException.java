package ksvoss.backend.user;

public class DoubleRegistrationException extends RuntimeException{



        public DoubleRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message,null,false,false);
        }

        public DoubleRegistrationException(){
            this("",null,false,false);
        }

}
