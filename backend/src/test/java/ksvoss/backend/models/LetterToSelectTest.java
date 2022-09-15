package ksvoss.backend.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterToSelectTest {

    @Test
    void addlearnedStatusTest() {
        LearnedElement learnedElement = new LearnedElement(0, 1, true, 5, 4, 8);
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.addlearnedStatus(learnedElement);
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/a/0/5/4/1/true}", actual);


    }

    @Test
    void testToStringTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/a/0/0/0/0/false}", actual);

    }

    @Test
    void getSignAsTextTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        String actual = letterToSelect.getSignAsText();
        Assertions.assertEquals("a", actual);
    }

    @Test
    void getLetterIdTest() {
        Letter letter = new Letter(5, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        int actual = letterToSelect.getLetterId();
        Assertions.assertEquals(5, actual);
    }

    @Test
    void getTimesShowedTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setTimesShowed(5);
        int actual = letterToSelect.getTimesShowed();
        Assertions.assertEquals(5, actual);

    }

    @Test
    void getTimesPassedTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setTimesPassed(7);
        int actual = letterToSelect.getTimesPassed();
        Assertions.assertEquals(7, actual);
    }

    @Test
    void getTimesPassedLastTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setTimesPassedLast(12);
        int actual = letterToSelect.getTimesPassedLast();
        Assertions.assertEquals(2, actual);
    }

    @Test
    void isSelectedTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setSelected(true);
        boolean actualTrue = letterToSelect.isSelected();
        letterToSelect.setSelected(false);
        boolean actualFalse = letterToSelect.isSelected();
        assertAll(
                () -> assertTrue(actualTrue),
                () -> assertFalse(actualFalse)
        );

    }

    @Test
    void setSignAsTextTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setSignAsText("b");
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/b/0/0/0/0/false}", actual);

    }

    @Test
    void setLetterIdTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setLetterId(7);
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/a/7/0/0/0/false}", actual);
    }

    @Test
    void setTimesShowed() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setTimesShowed(15);
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/a/0/15/0/0/false}", actual);
    }

    @Test
    void setTimesPassedTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setTimesPassed(12);
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/a/0/0/12/0/false}", actual);
    }

    @Test
    void setTimesPassedLastTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setTimesPassed(5);
        String actual = letterToSelect.toString();
        Assertions.assertEquals("LetterToSelect{/a/0/0/5/0/false}", actual);
    }

    @Test
    void setSelectedTest() {
        Letter letter = new Letter(0, "a", null, "ah", 0, 0, null);

        LetterToSelect letterToSelect = new LetterToSelect(letter);
        letterToSelect.setSelected(true);
        String actualTrue = letterToSelect.toString();
        letterToSelect.setSelected(false);
        String actualFalse = letterToSelect.toString();
        assertAll(
                () -> assertEquals("LetterToSelect{/a/0/0/0/0/true}", actualTrue),
                () -> assertEquals("LetterToSelect{/a/0/0/0/0/false}", actualFalse)
        );
    }
}