package ksvoss.backend.models;

public record ElementToTrain (
    String letterAsString,
    String spelling
){
    public ElementToTrain(Letter letter){
        this(letter.signAsText(), letter.spelling());
    }
}
