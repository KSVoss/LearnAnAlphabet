package ksvoss.backend.models;

import java.util.*;

import ksvoss.backend.user.NewUser;
import ksvoss.backend.user.TooFewLettersSelectedException;
import org.springframework.data.annotation.Id;



public class User {

    @Id
    private String id;
    private final String mailadress;        // brauche ich beim Login, Zugriff über Repo
    private final String nickname;
    private final String passwordHashed;
    private final String preferredLanguage;
    private final boolean weightedRadomize=false;
    private int selectedAlphabetId;
    private ArrayList <LearnedElement>   learnedElements;

    static Random random=new Random();
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





    public void setSelectedAlphabetId(int selectedAlphabetId){
        this.selectedAlphabetId=selectedAlphabetId;
    }
    public int getSelectedAlphabetId(){
        return this.selectedAlphabetId;
    }

    public boolean isPasswordCorrect(String password){
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






    public LearnedElement getRandomElement2(int alphabetIdOfLast,int letterIdOfLast ){

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


    public LearnedElement getRandomElement2(ElementToTrain trainedElement) {
         return getRandomElement2(trainedElement.alphabetId(),trainedElement.letterId());



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
