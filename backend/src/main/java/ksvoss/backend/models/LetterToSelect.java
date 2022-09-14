package ksvoss.backend.models;


 public class LetterToSelect {
     String signAsText;
     int letterId;
     int timesShowed;
     int timesPassed;
     int timesPassedLast;
     boolean isSelected;


     public LetterToSelect(Letter letter) {
         this.signAsText = letter.signAsText();
         this.letterId = letter.id();
     }

     public String getSignAsText() {
         return signAsText;
     }

     public void setSignAsText(String signAsText) {
         this.signAsText = signAsText;
     }

     public int getLetterId() {
         return letterId;
     }

     public void setLetterId(int letterId) {
         this.letterId = letterId;
     }

     public int getTimesShowed() {
         return timesShowed;
     }

     public void setTimesShowed(int timesShowed) {
         this.timesShowed = timesShowed;
     }

     public int getTimesPassed() {
         return timesPassed;
     }

     public void setTimesPassed(int timesPassed) {
         this.timesPassed = timesPassed;
     }

     public void setTimesPassedLast(int timesPassedLast) {
         this.timesPassedLast = timesPassedLast;
     }

     public boolean isSelected() {
         return isSelected;
     }

     public void setSelected(boolean selected) {
         isSelected = selected;
     }

     public void addlearnedStatus(LearnedElement learnedElement) {
         this.isSelected = learnedElement.isSelected();
         this.timesPassed = learnedElement.getTimesPassed();
         this.timesShowed = learnedElement.getTimesShowed();
         this.timesPassedLast = learnedElement.timesPassedLast50();
     }

     public int getTimesPassedLast() {
         return Long.bitCount(this.timesPassedLast);
     }

    @Override
    public String toString() {
        return "LetterToSelect{" +
                "/" + signAsText +
                "/" + letterId +
                "/" + timesShowed +
                "/" + timesPassed +
                "/" + timesPassedLast +
                "/" + isSelected +
                '}';
    }
}
