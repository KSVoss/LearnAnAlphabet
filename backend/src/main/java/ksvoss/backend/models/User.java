package ksvoss.backend.models;

import java.util.*;

import ksvoss.backend.user.NewUser;
import ksvoss.backend.exeptions.TooFewLettersSelectedException;
import org.springframework.data.annotation.Id;



public class User {


    @Id
    private String id;
    private String mailadress="";        // brauche ich beim Login, Zugriff über Repo
    private String nickname="";
    private String passwordHashed="";
    private String preferredLanguage="";
    private boolean weightedRadomize=false;
    private int selectedAlphabetId;
    private List <LearnedElement>   learnedElements;

    static Random random=new Random();
    public User() {
    }

    public User(String mailadress, String nickname, String passwordHashed) {
        this.mailadress = mailadress;
        this.nickname = nickname;
        this.passwordHashed = passwordHashed;
        this.id= UUID.randomUUID().toString();
        this.learnedElements=new ArrayList<>();
        this.selectedAlphabetId=0;
        this.preferredLanguage ="deutsch";
    }
    public User(NewUser newUser){
        this.mailadress= newUser.mailadress();
        this.nickname=newUser.nickname();
        this.passwordHashed=newUser.password();
        this.id=UUID.randomUUID().toString();
        this.learnedElements=new ArrayList<>();
        this.selectedAlphabetId=0;
        this.preferredLanguage ="deutsch";
     }

    public void setId(String id) {
        this.id = id;
    }


    public String getMailadress() {
        return mailadress;
    }

    public void setMailadress(String mailadress) {
        this.mailadress = mailadress;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public void setLearnedElements(List<LearnedElement> learnedElements) {
        this.learnedElements = learnedElements;
    }

    public static Random getRandom() {
        return random;
    }

    public static void setRandom(Random random) {
        User.random = random;
    }

    public void setSelectedAlphabetId(int selectedAlphabetId){
        this.selectedAlphabetId=selectedAlphabetId;
    }
    public int getSelectedAlphabetId(){
        return this.selectedAlphabetId;
    }

    public boolean isPasswordCorrect(String password){
        // return BCrypt.checkps(password,this.passwordHashed);
        return this.passwordHashed.equals(password);       // Hash muss noch eingebaut werden
    }




    public String getId() {
        return id;
    }




    public String getNickname() {
        return nickname;
    }




    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public boolean isWeightedRadomize() {
        return weightedRadomize;
    }

    public List<LearnedElement>  getLearnedElements() {
        return learnedElements;
    }





    public LearnedElement getRandomElement(ElementToTrain trainedElement) {

        int alphabetIdOfLast=trainedElement.alphabetId();
        int letterIdOfLast= trainedElement.letterId();

        if(learnedElements.isEmpty())
            throw new TooFewLettersSelectedException();

         List<LearnedElementWithProbability> learnedElementWithProbabilityList=new ArrayList<>();

        for (LearnedElement learnedElement : learnedElements) {

            if ((learnedElement.getAlphabetID() == selectedAlphabetId) &&
                    (learnedElement.isSelected())&&(!learnedElement.isEqual(alphabetIdOfLast, letterIdOfLast)))

                    learnedElementWithProbabilityList.add(
                            new LearnedElementWithProbability(learnedElement,
                                    1.3 - learnedElement.getTimesPassed() /
                                            (learnedElement.getTimesShowed() + 0.0001)));
        }

        if(learnedElementWithProbabilityList.isEmpty())
            throw new TooFewLettersSelectedException();


        if(!this.weightedRadomize)return
                learnedElementWithProbabilityList
                        .get(random.nextInt(learnedElementWithProbabilityList.size())).learnedElement();

        double cumulatedProbabilities=0;
        for (LearnedElementWithProbability learnedElementWithProbability : learnedElementWithProbabilityList) {
            cumulatedProbabilities += learnedElementWithProbability.probability();
        }
            double randomNumber= random.nextFloat()*cumulatedProbabilities;
        for (LearnedElementWithProbability learnedElementWithProbability : learnedElementWithProbabilityList) {
            randomNumber -= learnedElementWithProbability.probability();
            if (randomNumber < 0) return learnedElementWithProbability.learnedElement();
        }
            return learnedElementWithProbabilityList.get(learnedElementWithProbabilityList.size()-1).learnedElement();

        }




        public void saveAnswer(boolean isAnswerCorrect,int alphabetId,int letterId) {

            for (LearnedElement learnedElement : learnedElements) {
                if ((learnedElement.getLetterID() == letterId) && (learnedElement.getAlphabetID() == alphabetId))
                    learnedElement.incrementTimesPassed(isAnswerCorrect);
            }
    }








    public void selectElementNew(SelectedElement selectedElement) {
         if(learnedElements==null){
            learnedElements=new ArrayList<>();
        }
        if(learnedElements.isEmpty()){
            learnedElements.add(
                    new LearnedElement(selectedElement.alphabetId(),selectedElement.letterId(),true,0,0,0));
            return;
        }

         for (LearnedElement learnedElement : learnedElements) {
            if (learnedElement.getAlphabetID() == selectedElement.alphabetId()
                    && learnedElement.getLetterID() == selectedElement.letterId()) {
                learnedElement.changeSelected();
                return;
            }
        }
            learnedElements.add(
                    new LearnedElement(selectedElement.alphabetId(),selectedElement.letterId(),selectedElement.selected(),0,0,0));



     }
}
