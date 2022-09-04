package ksvoss.backend.models;

import java.util.Objects;

public class LearnedElement {
    private int alphabetID;
    private int letterID;
    private boolean selected=false;
    private int timesShowed=0;
    private int timesPassed=0;

    private long isPassedLast50TimesAsBooleanArray=0;

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

    int getTimesShowed(){
        return timesShowed;
    }
    int getTimesPassed(){
        return timesPassed;
    }
    public long getIsPassedLast50TimesAsBooleanArray(){
        return isPassedLast50TimesAsBooleanArray;
    }

    public LearnedElement() {
    }

    public void setAlphabetID(int alphabetID) {
        this.alphabetID = alphabetID;
    }

    public void setLetterID(int letterID) {
        this.letterID = letterID;
    }

    public void setTimesShowed(int timesShowed) {
        this.timesShowed = timesShowed;
    }

    public void setTimesPassed(int timesPassed) {
        this.timesPassed = timesPassed;
    }

    public void setIsPassedLast50TimesAsBooleanArray(long isPassedLast50TimesAsBooleanArray) {
        this.isPassedLast50TimesAsBooleanArray = isPassedLast50TimesAsBooleanArray;
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

    public LearnedElement(int alphabetID, int letterID) {
        this.alphabetID = alphabetID;
        this.letterID = letterID;

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


    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected(){
        return this.selected;
    }
    public int getAlphabetID(){
        return this.alphabetID;
    }
    public int getLetterID(){
        return this.letterID;
    }

    public boolean isEqual(int alphabetId, int letterId) {
        if(this.alphabetID!=alphabetId)return false;
        if(this.letterID!=letterId)return false;
        return true;
    }
}
