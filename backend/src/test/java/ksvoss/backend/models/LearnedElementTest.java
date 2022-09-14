package ksvoss.backend.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LearnedElementTest {

    @Test
    void setTimesPassedLast50IncrementTest() {
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0,  0);
        learnedElement.incrementTimesPassedLast50(true);
        learnedElement.incrementTimesPassedLast50(true);
        for (int i = 1; i < 49; i++) {
            learnedElement.incrementTimesPassedLast50(false);
        }
        long actual50 = learnedElement.getIsPassedLast50TimesAsBooleanArray();
        learnedElement.incrementTimesPassedLast50(false);
        long actual51 = learnedElement.getIsPassedLast50TimesAsBooleanArray();
        assertAll(
                () -> assertEquals(0x3000000000000L, actual50),
                () -> assertEquals(0x2000000000000L, actual51)
        );
    }

    @Test
    void setSelectedTest(){
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0,  0);
        learnedElement.setSelected(true);
        boolean actualSelected=learnedElement.isSelected();
        learnedElement.setSelected(false);
        boolean actualNotSelected=learnedElement.isSelected();
        assertAll(
                ()->assertTrue(actualSelected),
                ()->assertFalse(actualNotSelected)
        );
    }

    @Test
    void timePassedLast50Test(){
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0,  0);
        learnedElement.incrementTimesPassed(false);
        int actual0=learnedElement.timesPassedLast50();
        learnedElement.incrementTimesPassed(true);
        int actual1=learnedElement.timesPassedLast50();
        assertAll(
                ()->assertEquals(0,actual0),
                ()->assertEquals(1,actual1)
        );
    }

    @Test
    void timeShowedTest(){
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0,  0);
        int actual0=learnedElement.getTimesShowed();
        learnedElement.incrementTimesPassed(true);
        learnedElement.incrementTimesPassed(false);
        int actual2=learnedElement.getTimesShowed();
        assertAll(
                ()->assertEquals(0,actual0),
                ()->assertEquals(2,actual2)
        );
    }

    @Test
    void timePassedTest(){
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0,  0);

        learnedElement.incrementTimesPassed(true);
        int actual1_1 = learnedElement.getTimesPassed();
        learnedElement.incrementTimesPassed(false);
        int actual1_2 = learnedElement.getTimesPassed();
        assertAll(
                () -> assertEquals(1, actual1_1),
                () -> assertEquals(1, actual1_2)
        );
    }

    @Test
    void timesPassLast50Test() {
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0, 21);
        int actual = learnedElement.timesPassedLast50();
        Assertions.assertEquals(3, actual);
    }

    @Test
    void changeSelectedTest() {
        LearnedElement learnedElement = new LearnedElement(1, 1, true, 0, 0, 21);

        learnedElement.changeSelected();
        boolean actualUnselected = learnedElement.isSelected();
        learnedElement.changeSelected();
        boolean actualSelected = learnedElement.isSelected();
        assertAll(
                () -> assertFalse(actualUnselected),
                () -> assertTrue(actualSelected)
        );


    }
    /*
     public void changeSelected() {
        selected=!selected;
    }


     */

}

