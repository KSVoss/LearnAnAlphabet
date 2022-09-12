package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.exeptions.*;
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

    public UserService(UserRepository userRepository, AlphabetRepository alphabetRepository) {
        this.userRepository = userRepository;
        this.alphabetRepository = alphabetRepository;
    }


    public UserLoginBody userLogin(UserLoginData userLoginData) {
        Optional<User> userOptional;

        userOptional = userRepository.findUserByMailadress(userLoginData.mailadress());
        if (userOptional.isEmpty()) throw new UserNotFoundException();

        if (userOptional.get().isPasswordCorrect(userLoginData.password())) {
            return new UserLoginBody(
                    userOptional.get().getId()
                    , userOptional.get().getSelectedAlphabetId()
                    , userOptional.get().getNickname()
                    , userOptional.get().isWeightedRadomize());
        }
        throw new UserNotFoundException();
    }

    public User loadUser(String userid) {
        Optional<User> user = userRepository.findById(userid);
        if (user.isEmpty()) throw new UserByIdNotFoundExeption();
        return user.get();
    }

    public void saveUser(User user) {
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
        User user = loadUser(userId);
        String preferredLanguage = user.getPreferredLanguage();
        List<Alphabet> alphabets = alphabetRepository.findAll();
        if (alphabets.isEmpty()) throw new EmptyAlphabetDatabaseException();
        for (int i = 0; i < alphabets.size(); i++) {
            nameOfAlphabetList.add(new NameOfAlphabet(i, alphabets.get(i).name(preferredLanguage)));
        }
        return new NameOfAlphabetsAndSelectedAlphabet(nameOfAlphabetList, user.getSelectedAlphabetId());
    }


    public List<LetterToSelect> getListOfLetters(String userId) {
        List<LetterToSelect> letterToSelectList;
        User user = loadUser(userId);
        int selectedAlphabet = user.getSelectedAlphabetId();
        Optional<Alphabet> alphabet = alphabetRepository.findById(selectedAlphabet);
        if (alphabet.isEmpty())
            throw new SpecifiedAlphabetNotExistsException("Alphabet with ID=" + selectedAlphabet + " does not exists");
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

    public Alphabet findAlphabetById(int id) {
        Optional<Alphabet> alphabet = alphabetRepository.findById(id);
        if (alphabet.isEmpty()) throw new SelectedAlphabetIsNullException(id);  // throw exception
        return alphabet.get();
    }

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

    public ElementToTrain saveResultAndGetNextElement(String userid, ElementToTrain trainedElement) {
        User user = loadUser(userid);
        if (trainedElement.alphabetId() > -1)
            user.saveAnswer(trainedElement.correctAnswer(), trainedElement.alphabetId(), trainedElement.letterId());


        LearnedElement randomElement = user.getRandomElement(trainedElement);
        ElementToTrain nextElement = learnedElementToElementToTrain(randomElement);

        saveUser(user);
        return nextElement;

    }

    public ElementToTrain getFirstElement(String userid) {
        User user = loadUser(userid);
        LearnedElement randomElement = user.getRandomElement(
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
}
