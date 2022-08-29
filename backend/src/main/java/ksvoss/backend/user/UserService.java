package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.alphabet.AlphabetService;
import ksvoss.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public String userLogin(UserLoginData userLoginData) {
        Optional<User> userOptional;

        userOptional = userRepository.findUserByMailadress(userLoginData.mailadress());
        if (userOptional.isPresent()) {
            if (userOptional.get().isPasswordCorrect(userLoginData.password())) {
                return userOptional.get().getId();
            } else {
                return "WRONG_PASSWORD";
            }
        }
        return "UNKNOWN_USER";
    }

    private User getUser(String userid){
        Optional<User> user=userRepository.findById(userid);
        if(user.isEmpty ())return null;     // ersetzen durch exception
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
         return user.get().getRandomElement(user.get().getSelectedAlphabetId());

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
        LearnedElement learnedElement=user.get().getRandomElement(user.get().getSelectedAlphabetId());
        userRepository.save(user.get());
        Optional<Alphabet> alphabet=alphabetRepository.findById(learnedElement.getAlphabetID());
        if(alphabet.isEmpty())return null;   // durch exception ersetzen
        Letter letter=alphabet.get().letters().get(learnedElement.getLetterID());
        return new ElementToTrain(letter);
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
        user.saveAnswer(isAnswerCorrect);
        saveUser(user);

    }
}
