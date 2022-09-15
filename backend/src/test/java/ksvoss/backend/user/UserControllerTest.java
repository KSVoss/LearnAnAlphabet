package ksvoss.backend.user;

import ksvoss.backend.exeptions.EmptyAlphabetDatabaseException;
import ksvoss.backend.exeptions.SelectedAlphabetIsNullException;
import ksvoss.backend.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private final UserService testUserService = mock(UserService.class);
    private final UserController testUserController = new UserController(testUserService);

    @Test
    void addUserTestPass() {
        // given
        NewUser addedUser = new NewUser("a@b.com", "Anton", "wird nicht verraten");
        User testUser = new User("a@b.com", "Anton", "wird nicht verraten");
        // when
        when(testUserService.addUser(addedUser)).thenReturn(testUser);
        User actual = testUserController.addUser(addedUser);
        // then
        assertAll(
                () -> assertEquals(testUser.getNickname(), actual.getNickname()),
                () -> assertEquals(testUser.getMailadress(), actual.getMailadress())
        );
    }

    @Test
    void addUserTestFail() {
        // given
        NewUser addedUser = new NewUser("a@b.com", "Anton", "wird nicht verraten");
        User testUser = new User("a@b.com", "Anton", "wird nicht verraten");
        User differentUser = new User("a@b.de", "Anton", "wird nicht verraten");
        // when
        when(testUserService.addUser(addedUser)).thenReturn(differentUser);
        User actual = testUserController.addUser(addedUser);
        // then
        Assertions.assertNotEquals(testUser.getMailadress(), actual.getMailadress());
    }

    @Test
    void getNamesOfAlphabetsTest() {
        // given
        NameOfAlphabet griechisch = new NameOfAlphabet(0, "griechisch");
        NameOfAlphabet hiragana = new NameOfAlphabet(1, "hiragana");
        List<NameOfAlphabet> listOfAlphabetTest = List.of(griechisch, hiragana);
        NameOfAlphabetsAndSelectedAlphabet testObject = new NameOfAlphabetsAndSelectedAlphabet(listOfAlphabetTest, 1);
        when(testUserService.getNamesOfAlphabet("testuser")).thenReturn(testObject);
        // when
        NameOfAlphabetsAndSelectedAlphabet actual = testUserController.getNamesOfAlphabets("testuser");
        // then
        Assertions.assertEquals(testObject, actual);
    }

    @Test
    void getNamesOfAlphabetsTestFailed() {
        // given
        NameOfAlphabet griechisch = new NameOfAlphabet(0, "griechisch");
        NameOfAlphabet hiragana = new NameOfAlphabet(1, "hiragana");
        List<NameOfAlphabet> listOfAlphabetTest = List.of(griechisch, hiragana);
        NameOfAlphabetsAndSelectedAlphabet testObject = new NameOfAlphabetsAndSelectedAlphabet(listOfAlphabetTest, 1);
        NameOfAlphabetsAndSelectedAlphabet actual2 = new NameOfAlphabetsAndSelectedAlphabet(List.of(), 0);
        when(testUserService.getNamesOfAlphabet("testuser1")).thenThrow(EmptyAlphabetDatabaseException.class);
        when(testUserService.getNamesOfAlphabet("testuser2")).thenReturn(testObject);
        // when
        try {
            NameOfAlphabetsAndSelectedAlphabet actual1 = testUserController.getNamesOfAlphabets("testuser1");
            fail();
        } catch (EmptyAlphabetDatabaseException e) {
        }
        try {
            actual2 = testUserController.getNamesOfAlphabets("testuser2");

        } catch (EmptyAlphabetDatabaseException e) {
            fail();
        }
        Assertions.assertEquals(testObject, actual2);
    }

    @Test
    void selectAlphabetTest() {
        // given
        LetterToSelect a = new LetterToSelect(new Letter(0, "a", null, "ah", 0, 0, null));
        LetterToSelect b = new LetterToSelect(new Letter(1, "b", null, "be", 0, 0, null));
        List<LetterToSelect> letterToSelectList = List.of(a, b);
        doNothing().when(testUserService).selectAlphabet(anyString(), anyInt());
        List<LetterToSelect> actualList1 = List.of();
        List<LetterToSelect> actualList2 = List.of();
        List<LetterToSelect> actualList3 = List.of();
        boolean actualBoolean1 = false;
        boolean actualBoolean2 = false;
        when(testUserService.getListOfLettersNew("user", 3)).thenReturn(letterToSelectList);
        try {
            actualList1 = testUserController.selectAlphabet("user", "3");
        } catch (SelectedAlphabetIsNullException e) {
            fail();
        }
        // when
        try {
            actualList2 = testUserController.selectAlphabet("user", "drei");
            fail();
        } catch (SelectedAlphabetIsNullException e) {
            actualBoolean1 = true;
        }
        try {
            actualList3 = testUserController.selectAlphabet("user", null);
            fail();
        } catch (SelectedAlphabetIsNullException e) {
            actualBoolean2 = true;
        }
        List<LetterToSelect> finalActualList = actualList1;
        // then
        boolean finalActualBoolean = actualBoolean1;
        boolean finalActualBoolean1 = actualBoolean2;
        assertAll(
                () -> assertEquals(letterToSelectList, finalActualList),
                () -> assertTrue(finalActualBoolean),
                () -> assertTrue(finalActualBoolean1)
        );
    }

    @Test
    void nextElementControllerTest() {
        ElementToTrain elementToTrainInput = new ElementToTrain("a", "ah", 0, 1, true);
        ElementToTrain elementToTrainOutput = new ElementToTrain("b", "be", 0, 2, false);
        when(testUserService.saveResultAndGetNextElement("user", elementToTrainInput)).thenReturn(elementToTrainOutput);
        ElementToTrain actual = testUserController.nextElement("user", elementToTrainInput);
        assertAll(
                () -> assertEquals(elementToTrainOutput, actual),
                () -> assertNotEquals(elementToTrainInput, actual)
        );
    }

    @Test
    void getLettersFromAlphabetControllerTest() {
        // given
        LetterToSelect a = new LetterToSelect(new Letter(0, "a", null, "ah", 0, 0, null));
        LetterToSelect b = new LetterToSelect(new Letter(1, "b", null, "be", 0, 0, null));
        List<LetterToSelect> letterToSelectList = List.of(a, b);
        when(testUserService.getListOfLetters("user")).thenReturn(letterToSelectList);
        // when
        List<LetterToSelect> actual = testUserController.getLettersFromAlphabet("user");
        // then
        Assertions.assertEquals(letterToSelectList, actual);
    }

    @Test
    void selectElementNewControllerTest() {
        LetterToSelect a = new LetterToSelect(new Letter(0, "a", null, "ah", 0, 0, null));
        LetterToSelect b = new LetterToSelect(new Letter(1, "b", null, "be", 0, 0, null));
        List<LetterToSelect> letterToSelectList = List.of(a, b);
        when(testUserService.selectElementNew(new SelectedElement(0, 1, false), "user"))
                .thenReturn(letterToSelectList);
        List<LetterToSelect> actual = testUserController.selectElementNew("user", new SelectedElement(0, 1, false));
        Assertions.assertEquals(letterToSelectList, actual);
    }
}