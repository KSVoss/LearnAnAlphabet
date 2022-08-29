package ksvoss.backend.models;

import java.util.*;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.alphabet.AlphabetService;
import ksvoss.backend.user.NewUser;
import org.springframework.data.annotation.Id;



public class User {

    @Id
    private String id;
    private String mailadress;
    private String nickname;
    private String passwordHashed;
    private byte[] picture;
    private String preferedLanguage;
    private boolean weightedRadomize;
    private int selectedAlphabetId;
    private LearnedElement lastOne;
    private ArrayList <LearnedElement>   learnedElements;

    static Random random=new Random();
    public User(String mailadress, String nickname, String passwordHashed) {
        this.mailadress = mailadress;
        this.nickname = nickname;
        this.passwordHashed = passwordHashed;
        this.id= UUID.randomUUID().toString();
        this.preferedLanguage="deutsch";
    }
    public User(NewUser newUser){
        this.mailadress= newUser.mailadress();
        this.nickname=newUser.nickname();
        this.passwordHashed=newUser.password();
        this.id=UUID.randomUUID().toString();
        this.selectedAlphabetId=1;
        this.preferedLanguage="deutsch";
        this.lastOne=new LearnedElement(-1,-1,true,0,0,0);
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {
    }

    public void setSelectedAlphabetId(int selectedAlphabetId){
        this.selectedAlphabetId=selectedAlphabetId;
    }
    public int getSelectedAlphabetId(){
        return this.selectedAlphabetId;
    }

    public boolean isPasswordCorrect(String password){
        if(this.passwordHashed.equals(password))return true;       // Hash muss noch eingebaut werden
        return false;
    }

    public void setMailadress(String mailadress) {
        this.mailadress = mailadress;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setPreferedLanguage(String preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public void setWeightedRadomize(boolean weightedRadomize) {
        this.weightedRadomize = weightedRadomize;
    }

    public void setLearnedElements(ArrayList<LearnedElement> learnedElements) {
        this.learnedElements = learnedElements;
    }

    public static void setRandom(Random random) {
        User.random = random;
    }

    public User(String id, String mailadress, String nickname, String passwordHashed, byte[] picture, String preferedLanguage, boolean weightedRadomize, ArrayList<LearnedElement> learnedElements) {
        this.id = id;
        this.mailadress = mailadress;
        this.nickname = nickname;
        this.passwordHashed = passwordHashed;
        this.picture = picture;
        this.preferedLanguage = preferedLanguage;
        this.weightedRadomize = weightedRadomize;
        this.learnedElements = learnedElements;

    }

    public String getId() {
        return id;
    }


    public String getMailadress() {
        return mailadress;
    }

    public String getNickname() {
        return nickname;
    }



    public byte[] getPicture() {
        return picture;
    }

    public String getPreferedLanguage() {
        return preferedLanguage;
    }

    public boolean isWeightedRadomize() {
        return weightedRadomize;
    }

    public List<LearnedElement>  getLearnedElements() {
        return learnedElements;
    }

    public boolean trainElement(TrainedElement trainedElement) {
        if (learnedElements == null) {
            learnedElements = new ArrayList<>();
        }
        if (learnedElements.size() == 0) {
            LearnedElement newLearnedElement = new LearnedElement(
                    trainedElement.alphabetId(),
                    trainedElement.letterId(),
                    true,
                    0, 0, 0
            );
            newLearnedElement.incrementTimesPassed(trainedElement.correctAnswer());
            return true;
        }
        for (int i = 0; i < learnedElements.size(); i++) {
            if ((learnedElements.get(i).getAlphabetID() == trainedElement.alphabetId())
                    & (learnedElements.get(i).getLetterID() == trainedElement.letterId())) {
                learnedElements.get(i).incrementTimesPassed(trainedElement.correctAnswer());
                return true;
            }
        }

            return false;



    }
    public boolean selectElement(SelectedElement selectedElement){
        if(learnedElements==null)
        {
            System.out.println("Liste nicht vorhanden");
            learnedElements=new ArrayList<LearnedElement>();
         }

        if(learnedElements.size()==0){
            System.out.println("List Länge 0");
            LearnedElement newLearnedElement=new LearnedElement(
                    selectedElement.alphabetId()
                    ,selectedElement.letterId()
                    ,selectedElement.selected()
                    ,0,0,0);

            learnedElements.add(newLearnedElement);
            System.out.println("neue Länge:"+learnedElements.size());
              return true;
        }
        for(int i=0;i<learnedElements.size();i++){
            if((learnedElements.get(i).getAlphabetID()==selectedElement.alphabetId())
                    &(learnedElements.get(i).getLetterID()==selectedElement.letterId())){
                LearnedElement newLearnedElement=new LearnedElement(
                        selectedElement.alphabetId(),
                        selectedElement.letterId(),
                        selectedElement.selected(),
                        learnedElements.get(i).getTimesShowed(),
                        learnedElements.get(i).getTimesPassed(),
                        learnedElements.get(i).getIsPassedLast50TimesAsBooleanArray());

                learnedElements.set(i,newLearnedElement);
                return false;
            }

        }
        LearnedElement newLearnedElement=new LearnedElement(
                selectedElement.alphabetId()
                ,selectedElement.letterId()
                ,selectedElement.selected()
                ,0,0,0);

        learnedElements.add(newLearnedElement);
        return true;
    }



    public LearnedElement getRandomElement(int alphabetId){
        LearnedElement result;
        if(learnedElements.isEmpty())return null;
        List<LearnedElementWithProbability> learnedElementWithProbabilityList=new ArrayList<>();
        for(int i=0;i<learnedElements.size();i++)
        {
            if((learnedElements.get(i).getAlphabetID()==alphabetId)&
                    (learnedElements.get(i).isSelected()))
                if(!learnedElements.get(i).equals(lastOne))
                    learnedElementWithProbabilityList.add(
                        new LearnedElementWithProbability(learnedElements.get(i),
                                1.3-learnedElements.get(i).getTimesPassed()/
                                        (learnedElements.get(i).getTimesShowed()+0.0001)));
        }
        System.out.println("Vor Ausgabe");
        if(lastOne!=null)System.out.println("LastOne Letter:"+lastOne.getLetterID());
        System.out.println(learnedElementWithProbabilityList);
        if(learnedElementWithProbabilityList.isEmpty())return null;

        if(this.weightedRadomize){
            double cumulatedProbabilities=0;
            for(int i=0;i<learnedElementWithProbabilityList.size();i++)
            {
                cumulatedProbabilities+=learnedElementWithProbabilityList.get(i).probability();
            }
            double randomNumber= random.nextFloat()*cumulatedProbabilities;
            for(int i=0;i<learnedElementWithProbabilityList.size();i++)
            {
                randomNumber-=learnedElementWithProbabilityList.get(i).probability();
                if(randomNumber<0)return learnedElementWithProbabilityList.get(i).learnedElement();
            }
            lastOne=learnedElementWithProbabilityList.get(learnedElementWithProbabilityList.size()-1).learnedElement();
            return lastOne;
        }


        lastOne= learnedElementWithProbabilityList.get(random.nextInt(learnedElementWithProbabilityList.size())).learnedElement();
        return lastOne;
        // umschreiben auf List
        /*LearnedElement  results[]= (LearnedElement[]) Arrays.stream(learnedElements)
                .filter(element -> element.getSelected())
                .filter(element -> element.getAlphabetID()== alphabetID)
                .toArray();
        System.out.println(results);
        if(results.length==0)return null;
        return results[random.nextInt(results.length)];*/

    }

    public void saveAnswer(boolean isAnswerCorrect) {

        for(int i=0;i<learnedElements.size();i++)
        {
            if(learnedElements.get(i).equals(lastOne))learnedElements.get(i).incrementTimesPassed(isAnswerCorrect);
        }
    }
}
