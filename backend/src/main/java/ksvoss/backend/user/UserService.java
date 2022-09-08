package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AlphabetRepository alphabetRepository;

    public UserService(UserRepository userRepository,AlphabetRepository alphabetRepository) {
        this.userRepository = userRepository;
        this.alphabetRepository=alphabetRepository;
    }



    public UserLoginBody userLogin(UserLoginData userLoginData) {
        Optional<User> userOptional;

        userOptional = userRepository.findUserByMailadress(userLoginData.mailadress());
        if(userOptional.isEmpty()) throw new UserNotFoundException();

        if (userOptional.get().isPasswordCorrect(userLoginData.password())) {
            return new UserLoginBody(
                        userOptional.get().getId()
                        , userOptional.get().getSelectedAlphabetId()
                        , userOptional.get().getNickname()
                        , userOptional.get().isWeightedRadomize());
            }
        throw new UserNotFoundException();
    }
    private User loadUser(String userid) {
        Optional<User> user = userRepository.findById(userid);
        if (user.isEmpty()) throw new UserByIdNotFoundExeption();
        return user.get();
    }
    private void saveUser(User user) {
        userRepository.save(user);
    }
    public User addUser(NewUser userToAdd) {
        if (userRepository.existsUserByMailadress(userToAdd.mailadress()))
            throw new DoubleRegistrationException();
        User newUser = new User(userToAdd);
        userRepository.save(newUser);
        return newUser;
    }
    public NameOfAlphabetsAndSelectedAlphabet getNamesOfAlphabet(String userId) {
        List<NameOfAlphabet> nameOfAlphabetList = new ArrayList<>();
        User user=loadUser(userId);
        String preferredLanguage = user.getPreferredLanguage();
        List<Alphabet> alphabets = alphabetRepository.findAll();
        if (alphabets.isEmpty()) throw new EmptyAlphabetDatabaseException();
        for (int i = 0; i < alphabets.size(); i++) {
            nameOfAlphabetList.add(new NameOfAlphabet(i, alphabets.get(i).name(preferredLanguage)));
        }
        return new NameOfAlphabetsAndSelectedAlphabet(nameOfAlphabetList, user.getSelectedAlphabetId());
    }












    public void selectLanguage(String userid, int alphabetId) {
        User user=loadUser(userid);
        user.setSelectedAlphabetId(alphabetId);
        userRepository.save(user);
    }





    public List<LetterToSelect> getListOfLetters(String userId) {
        List<LetterToSelect> letterToSelectList;
        User user = loadUser(userId);
        int selectedAlphabet = user.getSelectedAlphabetId();
        Optional<Alphabet> alphabet = alphabetRepository.findById(selectedAlphabet);
        if (alphabet.isEmpty())
            throw new SpecifiedAlphabetNotExistsException("Alphabet with ID="+selectedAlphabet+" does not exists");
        LetterToSelect[] lettersToSelect = new LetterToSelect[alphabet.get().letters().size()];
        for (Letter letter : alphabet.get().letters()
        ) {
            lettersToSelect[letter.id()] = new LetterToSelect(letter);
        }
        for (LearnedElement learnedElement : user.getLearnedElements()) {
            if (learnedElement.getAlphabetID() == user.getSelectedAlphabetId()) {
                lettersToSelect[learnedElement.getLetterID()].addlearnedStatus(learnedElement);
            }
        }
        letterToSelectList = Arrays.stream(lettersToSelect).toList();
        return letterToSelectList;
    }

    public void selectAlphabet(String userid, int selectedAlphabet) {
        User user = loadUser(userid);
        user.setSelectedAlphabetId(selectedAlphabet);
        saveUser(user);
    }

    private Alphabet findAlphabetById(int id) {
        Optional<Alphabet> alphabet = alphabetRepository.findById(id);
        if (alphabet.isEmpty()) throw new SelectedAlphabetIsNullException(id);  // throw exception
        return alphabet.get();
    }

    private ElementToTrain learnedElementToElementToTrain(LearnedElement learnedElement) {
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

    public ElementToTrain saveResultAndGetNextElement(String userid, ElementToTrain trainedElement) {
        User user = loadUser(userid);
        if (trainedElement.alphabetId() > -1)
            user.saveAnswer(trainedElement.correctAnswer(), trainedElement.alphabetId(), trainedElement.letterId());


        LearnedElement randomElement = user.getRandomElement2(trainedElement);
        ElementToTrain nextElement = learnedElementToElementToTrain(randomElement);

        saveUser(user);
        return nextElement;

    }

    public ElementToTrain getFirstElement(String userid) {
        User user = loadUser(userid);
        LearnedElement randomElement = user.getRandomElement2(
                new ElementToTrain("", "", 0, 0, true));
        return learnedElementToElementToTrain(randomElement);
    }

    public List<LetterToSelect> getListOfLettersNew(String userid, int selectedAlphabet) {
        User user = loadUser(userid);
        user.setSelectedAlphabetId(selectedAlphabet);
        saveUser(user);
        return this.getListOfLetters(userid);

    }

    public List<LetterToSelect> selectElementNew(SelectedElement selectedElement, String userid) {
        User user = loadUser(userid);
        user.selectElementNew(selectedElement);
        saveUser(user);
        return getListOfLettersNew(userid, user.getSelectedAlphabetId());
    }

//------------------------------- unnütz
/*
    public void wasTrained(String userid, boolean isAnswerCorrect) {
        User user = loadUser(userid);

        user.saveAnswer(isAnswerCorrect, 0, 0);
        saveUser(user);

    }

   public ElementToTrain nextElement(String userid) {
        Optional<User> user = userRepository.findById(userid);
        if (user.isEmpty()) return null;   // durch exception ersetzen
        LearnedElement learnedElement = user.get().getRandomElement();
        userRepository.save(user.get());
        Optional<Alphabet> alphabet = alphabetRepository.findById(learnedElement.getAlphabetID());
        if (alphabet.isEmpty()) return null;   // durch exception ersetzen
        Letter letter = alphabet.get().letters().get(learnedElement.getLetterID());
        return new ElementToTrain(letter, learnedElement.getAlphabetID(), learnedElement.getLetterID(), true);
    }
    public LearnedElement getRandomElement(String userid) {
        Optional<User> user = userRepository.findById(userid);
        if (!user.isPresent()) return null;   // durch exeption ersetzen
        return user.get().getRandomElement();

    }

    public String getSelectedLanguage(String userid) {
        Optional<User> user = userRepository.findById(userid);
        if (user.isEmpty()) return "";    // durch exception ersetzen
        Optional<Alphabet> alphabet = alphabetRepository.findById(user.get().getSelectedAlphabetId());
        if (alphabet.isEmpty()) return "";    // durch exception ersetzen
        return alphabet.get().name(user.get().getPreferedLanguage());

    }
   public boolean trainElement(TrainedElement trainedElement, String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        User user;
        if (userOptional.isPresent())
            user = userOptional.get();
        else user = null;        // ersetzen durch throw Exeption

        user.trainElement(trainedElement);
        userRepository.save(user);


        return true;
    }
    public void testRepo() {
        Optional<Alphabet> alphabet = alphabetRepository.findById(0);
        if (!alphabet.isPresent()) {
            System.out.println("geht nicht");
            return;
        }
        System.out.println(alphabet.get().letters().get(1));
    }

    public User userLogin(UserLoginData userLoginData, int i) {
        Optional<User> userOptional;

        userOptional = userRepository.findUserByMailadress(userLoginData.mailadress());
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;

    }
        public boolean selectElement(SelectedElement selectedElement, String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        User user;
        if (userOptional.isPresent())
            user = userOptional.get();
        else user = null;        // ersetzen durch throw Exeption

        boolean response = user.selectElement(selectedElement);
        userRepository.save(user);
        return response;

    }
    */

}
