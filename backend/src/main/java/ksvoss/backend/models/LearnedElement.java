package ksvoss.backend.models;

import lombok.Data;

import java.util.Objects;

@Data

public class LearnedElement {
    private final int alphabetID;
    private final int letterID;
    private boolean selected;
    private int timesShowed;
    private int timesPassed;

    private long isPassedLast50TimesAsBooleanArray;

    @Override
    public String toString() {
        return "LearnedElement{" +
                "alphabetID=" + alphabetID +
                ", letterID=" + letterID +
                ", selected=" + selected +
                ", timesShowed=" + timesShowed +
                ", timesPassed=" + timesPassed +
                ", isPassedLast50TimesAsBooleanArray=" + isPassedLast50TimesAsBooleanArray +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnedElement that = (LearnedElement) o;
        return alphabetID == that.alphabetID && letterID == that.letterID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alphabetID, letterID);
    }


    public boolean isEqual(int alphabetID, int letterID) {
        return ((this.alphabetID == alphabetID) && (this.letterID == letterID));
    }

    int getTimesPassed() {
        return timesPassed;
    }

    public long getIsPassedLast50TimesAsBooleanArray() {
        return isPassedLast50TimesAsBooleanArray;
    }


    public LearnedElement(int alphabetID
            , int letterID
            , boolean selected
            , int timesShowed
            , int timesPassed
            , long isPassedLast50TimesAsBooleanArray) {
        this.alphabetID = alphabetID;
        this.letterID = letterID;
        this.selected = selected;
        this.timesShowed = timesShowed;
        this.timesPassed = timesPassed;
        this.isPassedLast50TimesAsBooleanArray = isPassedLast50TimesAsBooleanArray;
    }


    public void incrementTimesPassed(boolean isPassed){
        this.timesShowed++;
        this.incrementTimesPassedLast50(isPassed);
        if(isPassed)timesPassed++;
    }

    public void incrementTimesPassedLast50 (boolean isPassed){
        isPassedLast50TimesAsBooleanArray = isPassedLast50TimesAsBooleanArray << 1;
        if(isPassed){
            isPassedLast50TimesAsBooleanArray = isPassedLast50TimesAsBooleanArray |1;}
        isPassedLast50TimesAsBooleanArray = isPassedLast50TimesAsBooleanArray &(0x3ffffffffffffL);
    }

    public int timesPassedLast50(){
        return Long.bitCount(isPassedLast50TimesAsBooleanArray);
    }


    public void changeSelected() {
        selected=!selected;
    }
}
