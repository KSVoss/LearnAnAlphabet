package ksvoss.backend.user;

import ksvoss.backend.models.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getallalphabetnames/{userid}")
    public NameOfAlphabetsAndSelectedAlphabet getNamesOfAlphabets(@PathVariable String userid){
         return userService.getNamesOfAlphabet(userid);
    }

    @PutMapping("/selectAlphabet/{userid}")
    public List<LetterToSelect> selectAlphabet(@PathVariable String userid, String selectedAlphabet){
        if(selectedAlphabet==null)
            throw new SelectedAlphabetIsNullException();
        userService.selectAlphabet(userid,Integer.parseInt(selectedAlphabet));
        return userService.getListOfLettersNew(userid,Integer.parseInt(selectedAlphabet));
    }
    @PutMapping("/user/nextElement/{userid}")
    public ElementToTrain nextElement(@PathVariable String userid, @RequestBody ElementToTrain trainedElement){
        ElementToTrain response=userService.saveResultAndGetNextElement(userid,trainedElement);
         return response;
    }
    @PostMapping("/user/newuser")
    public User addUser(@RequestBody NewUser userToAdd){
        return userService.addUser(userToAdd);
    }




    @GetMapping("/getletters/{userid}")
    public List<LetterToSelect> getLettersFromAlphabet(@PathVariable String userid){
         return userService.getListOfLetters(userid);
    }



    @PutMapping("/user/selectElement/{userid}")
    public List<LetterToSelect> selectElementNew(@PathVariable String userid,@RequestBody SelectedElement selectedElement){
         return userService.selectElementNew(selectedElement,userid);

    }



    @GetMapping("/user/login")
    public  UserDataAndFirstElement login(UserLoginData userLoginData){
        UserLoginBody userLoginBody=userService.userLogin(userLoginData);

        ElementToTrain elementToTrain=userService.getFirstElement(userLoginBody.getId());
        UserDataAndFirstElement userDataAndFirstElement=new UserDataAndFirstElement(userLoginBody,elementToTrain);
         return  userDataAndFirstElement;
    }
    /*ungenutzt

       @PutMapping("/trainedelement/{userid}")
    public ResponseEntity<TrainedElement> trainedElement(@PathVariable String userid,@RequestBody TrainedElement  trainedElement ){
        boolean isOk=userService.trainElement(trainedElement , userid );
        if(isOk)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(trainedElement );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(trainedElement );
    }

        @GetMapping("/g/{userid}")
    public List<LetterToSelect> getLettersAlphabet(@PathVariable String userid){
     List<LetterToSelect> result=userService.getListOfLetters(userid);
         return userService.getListOfLetters(userid);
    }
        @PutMapping("/wastrained/{userid}")
    public void wasTrained(@PathVariable String userid,String  isAnswerCorrect){
        System.out.println("/wastrained");

        System.out.println("Wert:"+isAnswerCorrect);
        System.out.println("wastrained:"+isAnswerCorrect.equals("true"));
        userService.wasTrained(userid, isAnswerCorrect.equals( "true"));
    }


        @PutMapping("/selectlanguage/{userid}")
    public void selectLanguage(@PathVariable String userid, @RequestBody int alphabetId){
        System.out.println("/selectlanguage");
        userService.selectLanguage(userid, alphabetId);
    }

        @GetMapping("/getselectedlanguage/{userid}")
    public String getSelectedLanguage(@PathVariable String userid){
        System.out.println("getselectedlanguage");
        return userService.getSelectedLanguage(userid);
    }
        @GetMapping("/nextelement/{userid}")
    public ElementToTrain nextelement(@PathVariable String userid){
        System.out.println("nextelement");
        return userService.nextElement(userid);

    }
       //@GetMapping("/user/firstElement/{userid}")
    public ElementToTrain firstElement(@PathVariable String userid){
        return userService.nextElement(userid);
    }

    @GetMapping("/user/firstElement/{userid}")
    public ElementToTrain newFirstElement(@PathVariable String userid){
        return userService.getFirstElement(userid);
    }

        @GetMapping("/testrepo")
    public void testRepo(){
        userService.testRepo();


    }
       @PutMapping("/selectelement/{userid}")
    public ResponseEntity<SelectedElement> selectElement(@PathVariable String userid
            ,@RequestBody SelectedElement selectedElement){

        System.out.println("Userid:"+userid);
        System.out.println(selectedElement);
        boolean isNew= userService.selectElement(selectedElement , userid );
        if(isNew)
            return  ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(selectedElement );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( selectedElement);
    }



     */


}
