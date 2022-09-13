package ksvoss.backend.models;

import lombok.Data;

@Data
public class LetterToSelect{
    String signAsText;
    int letterId;
    int timesShowed;
    int timesPassed;
    int timesPassedLast;
    boolean isSelected;


    public LetterToSelect(Letter letter){
        this.signAsText= letter.signAsText();
        this.letterId=letter.id();
    }

    public void addlearnedStatus(LearnedElement learnedElement){
        this.isSelected=learnedElement.isSelected();
        this.timesPassed=learnedElement.getTimesPassed();
        this.timesShowed=learnedElement.getTimesShowed();
        this.timesPassedLast=learnedElement.timesPassedLast50();

    }


}
