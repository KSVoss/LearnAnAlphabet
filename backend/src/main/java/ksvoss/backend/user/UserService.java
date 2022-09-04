package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.alphabet.AlphabetService;
import ksvoss.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
     public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private AlphabetRepository alphabetRepository;
    public UserLoginBody userLogin(UserLoginData userLoginData) {
        Optional<User> userOptional;

        userOptional = userRepository.findUserByMailadress(userLoginData.mailadress());
        if (userOptional.isPresent()) {
            if (userOptional.get().isPasswordCorrect(userLoginData.password())) {

                return new UserLoginBody(
                        userOptional.get().getId()
                        ,userOptional.get().getSelectedAlphabetId()
                        ,userOptional.get().getNickname()
                        ,userOptional.get().isWeightedRadomize());
            } else {
                return new UserLoginBody("WRONG_PASSWORD",0,null,false);
            }
        }
        return new UserLoginBody("UNKNOWN_USER",0,null,false);
    }
    public User userLogin(UserLoginData userLoginData,int i) {
        Optional<User> userOptional;

        userOptional = userRepository.findUserByMailadress(userLoginData.mailadress());
        if (userOptional.isPresent()) {
            return userOptional.get();}
        return null;

    }

    private User getUser(String userid){
        Optional<User> user=userRepository.findById(userid);
        if(user.isEmpty ())return null;     // ersetzen durch exception
        System.out.println("User geladen");
        return user.get();
    }
    private void saveUser(User user){
        userRepository.save(user);

    }
    public User addUser(NewUser userToAdd){
         if(userRepository.existsUserByMailadress(userToAdd.mailadress()))return null;
         if(userRepository.existsUserByNickname(userToAdd.nickname()))return null;
         User newUser=new User(userToAdd);
         userRepository.save(newUser);
         return newUser;
    }



    public void testRepo(){
        Optional<Alphabet> alphabet=alphabetRepository.findById(0);
        if(!alphabet.isPresent()){System.out.println("geht nicht");return;}
        System.out.println(alphabet.get().letters().get(1));
    }

    public boolean selectElement(SelectedElement selectedElement,String userId) {
          Optional<User> userOptional=userRepository.findById(userId);

         User user;
          if(userOptional.isPresent())
             user=userOptional.get();
         else user=null;        // ersetzen durch throw Exeption

        boolean response=user.selectElement(selectedElement);
         userRepository.save(user);
        return response;

     }



    public boolean trainElement(TrainedElement trainedElement,String userId){
        Optional<User> userOptional=userRepository.findById(userId);

        User user;
        if(userOptional.isPresent())
            user=userOptional.get();
        else user=null;        // ersetzen durch throw Exeption

        user.trainElement(trainedElement);
        userRepository.save(user);



        return true;
    }

    public LearnedElement getRandomElement(String userid){
         Optional<User> user=userRepository.findById(userid);
         if(!user.isPresent())return null;   // durch exeption ersetzen
         return user.get().getRandomElement( );

    }
    public NameOfAlphabetsAndSelectedAlphabet getNamesOfAlphabet(String userId) {
        List<NameOfAlphabet> nameOfAlphabetList=new ArrayList<>();
        Optional<User> user=userRepository.findById(userId);
        if(user.isEmpty())return null;        // durch exception ersetzen
        String preferredlanguage=user.get().getPreferedLanguage();

        List<Alphabet>  alphabets=alphabetRepository.findAll();
        if(alphabets.isEmpty())return null;     // durch exception ersetzen
        for(int i=0;i<alphabets.size();i++)
        {
            nameOfAlphabetList.add(new NameOfAlphabet(i,alphabets.get(i).name(preferredlanguage)));

        }



        return new NameOfAlphabetsAndSelectedAlphabet(nameOfAlphabetList,user.get().getSelectedAlphabetId());
    }
    public ElementToTrain nextElement(String userid) {
        Optional<User> user=userRepository.findById(userid);
        if(user.isEmpty())return null;   // durch exception ersetzen
        LearnedElement learnedElement=user.get().getRandomElement( );
        userRepository.save(user.get());
        Optional<Alphabet> alphabet=alphabetRepository.findById(learnedElement.getAlphabetID());
        if(alphabet.isEmpty())return null;   // durch exception ersetzen
        Letter letter=alphabet.get().letters().get(learnedElement.getLetterID());
        return new ElementToTrain(letter,learnedElement.getAlphabetID(),learnedElement.getLetterID(),true);
     }

    public void selectLanguage(String userid, int alphabetId) {
        Optional<User> user=userRepository.findById(userid);
        if(user.isEmpty())return;           // durch exception ersetzen
        user.get().setSelectedAlphabetId(alphabetId);
        userRepository.save(user.get());
    }

    public String getSelectedLanguage(String userid) {
        Optional<User> user=userRepository.findById(userid);
        if(user.isEmpty())return "";    // durch exception ersetzen
        Optional<Alphabet> alphabet=alphabetRepository.findById(user.get().getSelectedAlphabetId());
        if(alphabet.isEmpty())return "";    // durch exception ersetzen
        return alphabet.get().name(user.get().getPreferedLanguage());

    }

    public void wasTrained(String userid, boolean isAnswerCorrect) {
        User user=getUser(userid);

        user.saveAnswer(isAnswerCorrect,0,0);
        saveUser(user);

    }

    public List<LetterToSelect> getListOfLetters(String userId){
        List<LetterToSelect> letterToSelectList=new ArrayList<>();
        User user=getUser(userId);
        int selectedAlphabet= user.getSelectedAlphabetId();
        Optional<Alphabet> alphabet=alphabetRepository.findById(selectedAlphabet);
        if(alphabet.isEmpty()) return null; // ersetzen durch Exception
        LetterToSelect lettersToSelect[]=new LetterToSelect[alphabet.get().letters().size()];


        for (Letter letter : alphabet.get().letters()
             ) {
            lettersToSelect[letter.id()]=new LetterToSelect(letter);
        }
        for (LearnedElement learnedElement:user.getLearnedElements())
        {
            if(learnedElement.getAlphabetID()==user.getSelectedAlphabetId()){
            lettersToSelect[learnedElement.getLetterID()].addlearnedStatus(learnedElement);}
        }
        letterToSelectList= Arrays.stream(lettersToSelect).toList();

        //System.out.println(letterToSelectList);



        return letterToSelectList;
    }

    public void selectAlphabet(String userid, int selectedAlphabet) {
        User user=getUser(userid);
        user.setSelectedAlphabetId(selectedAlphabet);
        System.out.println("Nach Service"+user.getSelectedAlphabetId());

        saveUser(user);
    }

    private Alphabet findAlphabetById(int id){
        Optional<Alphabet> alphabet = alphabetRepository.findById(id);
        if(alphabet.isEmpty())return null;  // throw exception
        return alphabet.get();
    }

    private ElementToTrain learnedElementToElementToTrain(LearnedElement learnedElement){
        String letterAsString;
        String spelling;
        int alphabetId;
        int letterId;
        boolean correctAnswer;
        alphabetId=learnedElement.getAlphabetID();
        letterId=learnedElement.getLetterID();
        Letter letter=findAlphabetById(alphabetId).findLetterById(letterId);

        letterAsString=letter.signAsText();
        spelling=letter.spelling();

        return new ElementToTrain( letterAsString,spelling,alphabetId,letterId,false);
    }

    public ElementToTrain saveResultAndGetNextElement(String userid, ElementToTrain trainedElement) {
        User user=getUser(userid);
        user.saveAnswer(trainedElement.correctAnswer(),trainedElement.alphabetId(),trainedElement.letterId());



        LearnedElement randomElement=user.getRandomElement2(trainedElement);
        ElementToTrain nextElement=learnedElementToElementToTrain(randomElement);

        saveUser(user);
        return nextElement;

    }

    public ElementToTrain getFirstElement(String userid) {
        User user=getUser(userid);
        System.out.println("Service getFirstElement");

        LearnedElement randomElement=user.getRandomElement2(
                new ElementToTrain("","",0,0,true));

        System.out.println("Service Element to Train:"+randomElement.toString());
        ElementToTrain nextElement=learnedElementToElementToTrain(randomElement);

        return nextElement;
    }
}
