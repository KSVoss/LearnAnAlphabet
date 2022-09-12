package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.exeptions.*;
import ksvoss.backend.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AlphabetRepository alphabetRepository = mock(AlphabetRepository.class);
    private final Alphabet alphabetMocked=mock(Alphabet.class);


    private final UserService userService = new UserService(userRepository,alphabetRepository);

    private List<Alphabet> getListOfAlphabetsForMocking(){
        AlphabetNameInDifferentLanguage griechischAufDeutsch=new AlphabetNameInDifferentLanguage("griechisch","deutsch");
        AlphabetNameInDifferentLanguage griechischAufEnglisch=new AlphabetNameInDifferentLanguage("greek","english");
        AlphabetNameInDifferentLanguage hiraganaAufDeusch=new AlphabetNameInDifferentLanguage("hiragane","deutsch");
        AlphabetNameInDifferentLanguage hiraganaAufEnglisch=new AlphabetNameInDifferentLanguage("hiragana","englisch");
        Letter alpha= new Letter(0,"\u03B1",null,"alpha",0,0,null);
        Letter beta=new Letter(1,"\u03B2",null,"beta",0,0,null);
        Letter gamma=new Letter(2,"\u03B3",null,"gamma",0,0,null);
        Letter Delta=new Letter(3,"\u0394",null,"Delta",0,0,null);
        Letter Pi=new Letter(4,"\u03A0",null,"Pi",0,0,null);
        Letter Sigma=new Letter(5,"\u03A3",null,"Sigma",0,0,null);
        Letter Omega=new Letter(6,"\u03A9",null,"Omega",0,0,null);
        Letter a=new Letter(0,"\u3041",null,"a",0,0,null);
        Letter i=new Letter(1,"\u3043",null,"i",0,0,null);
        Letter u=new Letter(2,"\u3045",null,"u",0,0,null);
        Letter e=new Letter(3,"\u3047",null,"e",0,0,null);
        Letter o=new Letter(4,"\u3049",null,"o",0,0,null);
        Alphabet griechisch=new Alphabet(0,
                new AlphabetNameInDifferentLanguage[]{griechischAufDeutsch,griechischAufEnglisch}
                , List.of(alpha,beta,gamma,Delta,Pi,Sigma,Omega));
        Alphabet hiragana=new Alphabet(1,
                new AlphabetNameInDifferentLanguage[]{hiraganaAufDeusch,hiraganaAufEnglisch}
                , List.of(a,i,u,e,o));
        List<Alphabet> alphabetList=new ArrayList<>();
        alphabetList.add(griechisch);
        alphabetList.add(hiragana);
        return alphabetList;

    }
    private Alphabet getAnAlphabetForMocking(){
        List<Alphabet> alphabetList=getListOfAlphabetsForMocking();
        return alphabetList.get(0);
    }

    @Test
    void addUserTestPass() {
        //given
        NewUser newUserToAdd = new NewUser("a@b.com", "Anton", "wird nicht verraten");

        when(userRepository.existsUserByMailadress(newUserToAdd.mailadress())).thenReturn(false);
        when(userRepository.existsUserByNickname(newUserToAdd.nickname())).thenReturn(false);

        User addedUser = new User("a@b.com", "Anton", "wird nicht verraten");
        when(userRepository.save(addedUser)).thenReturn(addedUser);


        //when
        User actual = userService.addUser(newUserToAdd);

        //then
        Assertions.assertEquals(addedUser.getNickname(), actual.getNickname());
    }

    @Test
    void addUserTestFailMailadress() {
        //given
        NewUser newUserToAdd = new NewUser("a@b.com", "Anton", "wird nicht verraten");

        when(userRepository.existsUserByMailadress(newUserToAdd.mailadress())).thenReturn(true);
        when(userRepository.existsUserByNickname(newUserToAdd.nickname())).thenReturn(false);

        User addedUser = new User("a@b.com", "Anton", "wird nicht verraten");
        when(userRepository.save(addedUser)).thenReturn(addedUser);


        try {
            //when
            User actual = userService.addUser(newUserToAdd);
            fail();
        }

        //then
        catch (DoubleRegistrationException e){
            Assertions.assertTrue(true);
        }



    }

    @Test
    void userLoginTestPass(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("testpassword");

        testuser.setId("thisId");
        UserLoginBody expetectedUser= new UserLoginBody("thisId",0,"Paul",false);
        Optional<User> optionaTestUser=Optional.of(testuser);
        UserLoginData userLoginData=new UserLoginData("a@b.com","testpassword");
        when(userRepository.findUserByMailadress("a@b.com")).thenReturn(optionaTestUser);
        UserLoginBody actual=userService.userLogin(userLoginData);
        Assertions.assertEquals(expetectedUser.toString(),actual.toString());
    }

    @Test
    void userLoginTestFailedBecauseNoMailadress(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("testpassword");

        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.empty();
        UserLoginData userLoginData=new UserLoginData("a@b.com","testpassword");
        when(userRepository.findUserByMailadress("a@b.com")).thenReturn(optionaTestUser);

        try {
            //when
            UserLoginBody actual = userService.userLogin(userLoginData);
            fail();
        }

        //then
        catch (UserNotFoundException e){
            Assertions.assertTrue(true);
        }



    }
    @Test
    void userLoginTestFailedBecauseOfPassword(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.of(testuser);

        UserLoginBody expetectedUser= new UserLoginBody("thisId",0,"Paul",false);
        UserLoginData userLoginData=new UserLoginData("a@b.com","testpassword");
        when(userRepository.findUserByMailadress("a@b.com")).thenReturn(optionaTestUser);

        try {
            //when
            UserLoginBody actual = userService.userLogin(userLoginData);
            fail();
        }

        //then
        catch (UserNotFoundException e){
            Assertions.assertTrue(true);

        }


    }

    @Test
    void loadUserTestPass(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.of(testuser);


        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        try {
            User actual = userService.loadUser("thisId");
            Assertions.assertTrue(true);
        }
        catch(UserByIdNotFoundExeption e){
            fail();
        }
    }
    @Test
    void loadUserTestFailed(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.empty();
        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        try {
            User actual = userService.loadUser("thisId");
            fail();        }
        catch(UserByIdNotFoundExeption e){
            Assertions.assertTrue(true);

         }
    }


    @Test
    void getNamesOfAlphabetTestPass(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.of(testuser);
        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        when(alphabetRepository.findAll()).thenReturn(getListOfAlphabetsForMocking());
        NameOfAlphabetsAndSelectedAlphabet actual=userService.getNamesOfAlphabet("thisId");
        Assertions.assertEquals("NameOfAlphabetsAndSelectedAlphabet[nameOfAlphabetList=[NameOfAlphabet[id=0, name=griechisch], NameOfAlphabet[id=1, name=hiragane]], alphabetId=0]",actual.toString());
    }

    @Test
    void getNamesOfAlphabetTestFailedBecauseOfEmptyAlphabetList(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.of(testuser);
        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        when(alphabetRepository.findAll()).thenReturn( new ArrayList<Alphabet>());

        try{
            NameOfAlphabetsAndSelectedAlphabet actual=userService.getNamesOfAlphabet("thisId");
            fail();
        }catch (EmptyAlphabetDatabaseException e){
            Assertions.assertTrue(true);
        }
    }
    @Test
    void getListOfLettersTestPass(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        List<LearnedElement> learnedElementList=new ArrayList<>();
        learnedElementList.add(
                new LearnedElement(0,0,true,1,2,2));
        learnedElementList.add(
                new LearnedElement(0,1,false,5,4,3));
        learnedElementList.add(
                new LearnedElement(1,2,true,4,3,3));


        testuser.setLearnedElements(learnedElementList);
        Optional<User> optionaTestUser=Optional.of(testuser);
        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        Optional<Alphabet> optionalAlphabet=Optional.of(getAnAlphabetForMocking());
        when(alphabetRepository.findById(0)).thenReturn(optionalAlphabet);
        List<LetterToSelect> actual=userService.getListOfLetters("thisId");
        Assertions.assertEquals("""
                [LetterToSelect{sign='α:, letterId=0, Showed=1, Passed=2, PassedLast=1, Selected=true'}
                , LetterToSelect{sign='β:, letterId=1, Showed=5, Passed=4, PassedLast=2, Selected=false'}
                , LetterToSelect{sign='γ:, letterId=2, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Δ:, letterId=3, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Π:, letterId=4, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Σ:, letterId=5, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Ω:, letterId=6, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                ]""",actual.toString());

    }

    @Test
    void getListOfLettersTestException(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        Optional<User> optionaTestUser=Optional.of(testuser);
        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        Optional<Alphabet> optionalAlphabet=Optional.empty();
        when(alphabetRepository.findById(0)).thenReturn(optionalAlphabet);
        try{
            List<LetterToSelect> actual=userService.getListOfLetters("thisId");
            fail();
        }catch (SpecifiedAlphabetNotExistsException e){
            Assertions.assertTrue(true);
        }

    }
    @Test
    void getListOfLettersTestFail(){
        User testuser=new User();
        testuser.setNickname("Paul");
        testuser.setPasswordHashed("anotherPassword");
        testuser.setId("thisId");
        List<LearnedElement> learnedElementList=new ArrayList<>();
        learnedElementList.add(
                new LearnedElement(0,0,true,1,2,2));
        learnedElementList.add(
                new LearnedElement(0,1,false,5,4,3));
        learnedElementList.add(
                new LearnedElement(1,2,true,4,3,3));


        testuser.setLearnedElements(learnedElementList);
        Optional<User> optionaTestUser=Optional.of(testuser);
        when(userRepository.findById("thisId")).thenReturn(optionaTestUser);
        Optional<Alphabet> optionalAlphabet=Optional.of(getAnAlphabetForMocking());
        when(alphabetRepository.findById(0)).thenReturn(optionalAlphabet);
        List<LetterToSelect> actual=userService.getListOfLetters("thisId");
        Assertions.assertNotEquals("""
                [LetterToSelect{sign='α:, letterId=0, Showed=1, Passed=2, PassedLast=1, Selected=true'}
                , LetterToSelect{sign='β:, letterId=1, Showed=5, Passed=4, PassedLast=2, Selected=true'}
                , LetterToSelect{sign='γ:, letterId=2, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Δ:, letterId=3, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Π:, letterId=4, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Σ:, letterId=5, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                , LetterToSelect{sign='Ω:, letterId=6, Showed=0, Passed=0, PassedLast=0, Selected=false'}
                ]""",actual.toString());

    }

    @Test
    void findAlphabetByIdTest(){
        Optional<Alphabet> optionalAlphabet=Optional.of(getAnAlphabetForMocking());
        when(alphabetRepository.findById(0)).thenReturn(optionalAlphabet);
        try{
            Alphabet actual=userService.findAlphabetById(0);
            Assertions.assertEquals(optionalAlphabet.get().toString(),actual.toString());
        }catch(RuntimeException e){
            fail();
        }
    }

    @Test
    void findAlphabetByIdTestFail(){
        Optional<Alphabet> optionalAlphabet=Optional.empty();
        when(alphabetRepository.findById(0)).thenReturn(optionalAlphabet);
        try{
            Alphabet actual=userService.findAlphabetById(0);
            fail();
        }catch(SelectedAlphabetIsNullException e){
            Assertions.assertTrue((true));
        }
    }

    @Test
    void learnedElementToElementToTrainTest(){
        LearnedElement learnedElement=new LearnedElement(0,3,true,5,3,0);
        Optional<Alphabet> optionalAlphabet=Optional.of(alphabetMocked);
        Letter testLetter=new  Letter(3,"mockletter",null,"mockedLetter",0,0,null);
        when(alphabetRepository.findById(0)).thenReturn(optionalAlphabet);
        when(alphabetMocked.findLetterById(3)).thenReturn(testLetter);
        ElementToTrain actual=userService.learnedElementToElementToTrain(learnedElement);
        assertAll(
                ()->assertEquals(0,actual.alphabetId()),
                ()->assertEquals(3,actual.letterId()),
                ()->assertEquals("mockletter",actual.letterAsString()),
                ()->assertEquals("mockedLetter",actual.spelling()),
                ()-> assertFalse(actual.correctAnswer()));
    }

}




/*
     public ElementToTrain learnedElementToElementToTrain(LearnedElement learnedElement) {
        String letterAsString;
        String spelling;
        int alphabetId;
        int letterId;
        alphabetId = learnedElement.getAlphabetID();
        letterId = learnedElement.getLetterID();
        Letter letter = findAlphabetById(alphabetId).findLetterById(letterId);
        letterAsString = letter.signAsText();
        spelling = letter.spelling();
        return new ElementToTrain(letterAsString, spelling, alphabetId, letterId, false);
    }
     */












