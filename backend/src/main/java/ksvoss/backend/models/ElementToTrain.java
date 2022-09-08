package ksvoss.backend.models;

public record ElementToTrain (
    String letterAsString,
    String spelling,
    int alphabetId,
    int letterId,
    boolean correctAnswer)



{

}
