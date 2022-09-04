package ksvoss.backend.models;

public record ElementToTrain (
    String letterAsString,
    String spelling,
    int alphabetId,
    int letterId,
    boolean correctAnswer)



{
    public ElementToTrain {
    }

    public ElementToTrain(Letter letter,int alphabetId,int letterId,boolean correctAnswer){
        this(letter.signAsText(), letter.spelling(), alphabetId,letterId, correctAnswer);
    }

}
