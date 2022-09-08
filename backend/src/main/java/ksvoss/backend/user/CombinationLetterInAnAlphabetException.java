package ksvoss.backend.user;

public class CombinationLetterInAnAlphabetException extends RuntimeException {



    public  CombinationLetterInAnAlphabetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message,null,false,false);
    }
    public CombinationLetterInAnAlphabetException(int alphabetId, int letterId) {
        this( "the LetterId "+letterId+" does not exist in this alphabetId "+alphabetId,null,false,false);
    }
}
