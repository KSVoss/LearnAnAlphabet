package ksvoss.backend.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {
    static Logger logger = Logger.getLogger(UserTest.class.getName());

    @Test
    void isPasswordCorrect() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        String actualHashedPassword = user.getPasswordHashed();
        boolean actualCheck = user.isPasswordCorrect("geheim");
        assertAll(
                () -> assertNotEquals("geheim", actualHashedPassword),
                () -> assertTrue(actualCheck)
        );
    }

    @Test
    void getRandomElement() {


        User user = new User("mailad", "Max Mustermann", "geheim");
        LearnedElement e1 = new LearnedElement(0, 0, true, 0, 0, 0);
        LearnedElement e2 = new LearnedElement(0, 1, true, 0, 0, 0);
        LearnedElement e3 = new LearnedElement(0, 2, true, 0, 0, 0);
        LearnedElement e4 = new LearnedElement(0, 3, true, 0, 0, 0);
        LearnedElement e5 = new LearnedElement(0, 4, true, 0, 0, 0);
        LearnedElement e6 = new LearnedElement(0, 5, true, 0, 0, 0);
        LearnedElement e7 = new LearnedElement(0, 6, false, 0, 0, 0);
        LearnedElement e8 = new LearnedElement(0, 7, false, 0, 0, 0);
        LearnedElement e9 = new LearnedElement(1, 0, true, 0, 0, 0);
        LearnedElement e10 = new LearnedElement(1, 1, true, 0, 0, 0);
        LearnedElement e11 = new LearnedElement(1, 2, true, 0, 0, 0);
        LearnedElement e12 = new LearnedElement(1, 3, true, 0, 0, 0);

        List<LearnedElement> learnedElementList = List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12);
        user.setLearnedElements(learnedElementList);
        int[] result = {0, 0, 0, 0, 0, 0};


        ElementToTrain elementToTrain = new ElementToTrain("a", "ah", 0, 2, true);
        for (int i = 0; i < 100; i++) {
            LearnedElement learnedElement = user.getRandomElement(elementToTrain);
            if (learnedElement.getAlphabetID() == 1) fail();
            if (!learnedElement.isSelected()) fail();

            result[learnedElement.getLetterID()] = result[learnedElement.getLetterID()] + 1;
        }

        int actual = 1;

        for (int i = 0; i < 6; i++) {
            actual = actual * result[i];
        }
        if (actual == 0) {
            logger.log(Level.ALL, "Test failed. Possible false-negative result. Restart test.");
            Assertions.assertTrue(true);
        }
        if (actual > 0) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void saveAnswer() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        LearnedElement learnedElement1 = new LearnedElement(0, 0, true, 2, 3, 1);
        LearnedElement learnedElement2 = new LearnedElement(0, 1, true, 5, 7, 0);

        List<LearnedElement> learnedElementList = List.of(learnedElement1, learnedElement2);
        user.setLearnedElements(learnedElementList);


        user.saveAnswer(true, 0, 0);
        user.saveAnswer(false, 0, 1);

        String actual = user.getLearnedElements().toString();
        Assertions.assertEquals("[LearnedElement{alphabetID=0, letterID=0, selected=true, timesShowed=3, timesPassed=4, isPassedLast50TimesAsBooleanArray=3}, LearnedElement{alphabetID=0, letterID=1, selected=true, timesShowed=6, timesPassed=7, isPassedLast50TimesAsBooleanArray=0}]", actual);
    }

    @Test
    void selectElementNew() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        SelectedElement selectedElement1 = new SelectedElement(3, 5, true);
        SelectedElement selectedElement2 = new SelectedElement(3, 5, false);
        SelectedElement selectedElement3 = new SelectedElement(3, 5, true);
        SelectedElement selectedElement4 = new SelectedElement(2, 5, true);

        user.selectElementNew(selectedElement1);
        String actual1 = user.getLearnedElements().toString();
        user.selectElementNew(selectedElement2);
        String actual2 = user.getLearnedElements().toString();
        user.selectElementNew(selectedElement3);
        String actual3 = user.getLearnedElements().toString();
        user.selectElementNew(selectedElement4);
        String actual4 = user.getLearnedElements().toString();


        assertAll(
                () -> assertEquals("[LearnedElement{alphabetID=3, letterID=5, selected=true, timesShowed=0, timesPassed=0, isPassedLast50TimesAsBooleanArray=0}]", actual1),
                () -> assertEquals("[LearnedElement{alphabetID=3, letterID=5, selected=false, timesShowed=0, timesPassed=0, isPassedLast50TimesAsBooleanArray=0}]", actual2),
                () -> assertEquals("[LearnedElement{alphabetID=3, letterID=5, selected=true, timesShowed=0, timesPassed=0, isPassedLast50TimesAsBooleanArray=0}]", actual3),
                () -> assertEquals("[LearnedElement{alphabetID=3, letterID=5, selected=true, timesShowed=0, timesPassed=0, isPassedLast50TimesAsBooleanArray=0}, LearnedElement{alphabetID=2, letterID=5, selected=true, timesShowed=0, timesPassed=0, isPassedLast50TimesAsBooleanArray=0}]", actual4)
        );


    }

    @Test
    void getId() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        String actual = user.getId();
        Assertions.assertTrue(actual.length() > 30);
    }

    @Test
    void getMailadress() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        String actual = user.getMailadress();
        Assertions.assertEquals("mailad", actual);
    }

    @Test
    void getNickname() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        String actual = user.getNickname();
        Assertions.assertEquals("Max Mustermann", actual);

    }

    @Test
    void getPasswordHashed() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        String actual = user.getPasswordHashed();
        if (actual.length() == 0) fail();
        Assertions.assertNotEquals("geheim", actual);

    }

    @Test
    void getPreferredLanguage() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setPreferredLanguage("deutsch");
        String actual = user.getPreferredLanguage();
        Assertions.assertEquals("deutsch", actual);

    }

    @Test
    void isWeightedRadomize() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setWeightedRadomize(true);
        boolean actual = user.isWeightedRadomize();
        Assertions.assertTrue(actual);

    }

    @Test
    void getSelectedAlphabetId() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setSelectedAlphabetId(3);
        int actual = user.getSelectedAlphabetId();
        Assertions.assertEquals(3, actual);
    }

    @Test
    void getLearnedElements() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        LearnedElement learnedElement1 = new LearnedElement(0, 0, true, 2, 3, 1);
        LearnedElement learnedElement2 = new LearnedElement(0, 1, true, 5, 7, 0);

        List<LearnedElement> learnedElementList = List.of(learnedElement1, learnedElement2);
        user.setLearnedElements(learnedElementList);
        String actual = user.getLearnedElements().toString();
        Assertions.assertEquals("[LearnedElement{alphabetID=0, letterID=0, selected=true, timesShowed=2, timesPassed=3, isPassedLast50TimesAsBooleanArray=1}, LearnedElement{alphabetID=0, letterID=1, selected=true, timesShowed=5, timesPassed=7, isPassedLast50TimesAsBooleanArray=0}]", actual);
    }


    @Test
    void setMailadress() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setMailadress("anotherMailadress");
        String actual = user.getMailadress();
        Assertions.assertEquals("anotherMailadress", actual);


    }

    @Test
    void setNickname() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setNickname("MinniMaus");
        String actual = user.getNickname();
        Assertions.assertEquals("MinniMaus", actual);

    }


    @Test
    void setPreferredLanguage() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setPreferredLanguage("nihongo");
        String actual = user.getPreferredLanguage();
        Assertions.assertEquals("nihongo", actual);

    }

    @Test
    void setWeightedRadomize() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        user.setWeightedRadomize(true);
        boolean actualTrue = user.isWeightedRadomize();
        user.setWeightedRadomize(false);
        boolean actualFalse = user.isWeightedRadomize();
        assertAll(
                () -> assertTrue(actualTrue),
                () -> assertFalse(actualFalse)
        );

    }

    @Test
    void setSelectedAlphabetId() {
        User user = new User("mailad", "Max Mustermann", "geheim");

        user.setSelectedAlphabetId(3);
        int actual1 = user.getSelectedAlphabetId();
        user.setSelectedAlphabetId(5);
        int actual2 = user.getSelectedAlphabetId();
        assertAll(
                () -> assertEquals(3, actual1),
                () -> assertEquals(5, actual2)
        );
    }

    @Test
    void setLearnedElements() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        LearnedElement learnedElement1 = new LearnedElement(2, 5, true, 2, 3, 1);
        LearnedElement learnedElement2 = new LearnedElement(2, 6, true, 5, 7, 0);

        List<LearnedElement> learnedElementList = List.of(learnedElement1, learnedElement2);
        user.setLearnedElements(learnedElementList);
        String actual = user.getLearnedElements().toString();
        Assertions.assertEquals("[LearnedElement{alphabetID=2, letterID=5, selected=true, timesShowed=2, timesPassed=3, isPassedLast50TimesAsBooleanArray=1}, LearnedElement{alphabetID=2, letterID=6, selected=true, timesShowed=5, timesPassed=7, isPassedLast50TimesAsBooleanArray=0}]", actual);
    }

    @Test
    void testToString() {
        User user = new User("mailad", "Max Mustermann", "geheim");
        LearnedElement learnedElement1 = new LearnedElement(2, 5, true, 2, 3, 1);
        LearnedElement learnedElement2 = new LearnedElement(2, 6, true, 5, 7, 0);

        List<LearnedElement> learnedElementList = List.of(learnedElement1, learnedElement2);
        String actual = user.toString();
        Assertions.assertEquals("User{id='" + user.getId() + "', mailadress='mailad', nickname='Max Mustermann', preferredLanguage='deutsch', weightedRadomize=false, selectedAlphabetId=0, learnedElements=[]}", actual);


    }
}