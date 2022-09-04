package ksvoss.backend.user;

import ksvoss.backend.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        System.out.println("/getallalphabetnames");
        return userService.getNamesOfAlphabet(userid);
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

    @PutMapping("/selectAlphabet/{userid}")
    public void selectAlphabet(@PathVariable String userid, String selectedAlphabet){
        // userService.selectAlphabet(userid,selectedAlphabet);
        System.out.println("selectAlphabet");
        if(selectedAlphabet==null){
            System.out.println("ist null");
            return;
        }

        System.out.println("Controller:"+selectedAlphabet);
        userService.selectAlphabet(userid,Integer.parseInt(selectedAlphabet));

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

    @PutMapping("/user/nextElement/{userid}")
    public ElementToTrain nextElement(@PathVariable String userid, @RequestBody ElementToTrain trainedElement){

        System.out.println("/user/nextuser");
        System.out.println("trainedElement Input:"+trainedElement.toString());
        ElementToTrain response=userService.saveResultAndGetNextElement(userid,trainedElement);
        System.out.println("trainedElement Output"+response.toString());
        return response;
    }

    @GetMapping("/user/firstElement/{userid}")
    public ElementToTrain newFirstElement(@PathVariable String userid){
        return userService.getFirstElement(userid);
    }


    @GetMapping("/g/{userid}")
    public List<LetterToSelect> getLettersAlphabet(@PathVariable String userid){
     List<LetterToSelect> result=userService.getListOfLetters(userid);
         return userService.getListOfLetters(userid);
    }




    @PostMapping("/newuser")
    public ResponseEntity<User> addUser(@RequestBody NewUser userToAdd){
        User newUser=userService.addUser(userToAdd);
         if(newUser!=null)
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(null);
    }



    @GetMapping("/testrepo")
    public void testRepo(){
        userService.testRepo();


    }

    @GetMapping("/getletters/{userid}")
    public List<LetterToSelect> getLettersFromAlphabet(@PathVariable String userid){
        System.out.println("getletters");
        return userService.getListOfLetters(userid);
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

    @GetMapping("/user/login")
    public ResponseEntity<UserDataAndFirstElement> login(UserLoginData userLoginData){

        System.out.println("user/login");
        System.out.println(userLoginData.mailadress()+"  "+userLoginData.password());





        UserLoginBody userLoginBody=userService.userLogin(userLoginData);

        //String userId=userService.userLogin(userLoginData);
        //System.out.println("<"+userId +">");
        System.out.println("Controller UserLoginBody:"+userLoginBody.toString());




        if(userLoginBody.getUserId().equals( "UNKNOWN_USER")|userLoginBody.getUserId().equals( "WRONG_PASSWORD"))
        {
            ElementToTrain elementToTrain=null;
            UserDataAndFirstElement userDataAndFirstElement=new UserDataAndFirstElement(userLoginBody,elementToTrain);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(userDataAndFirstElement
                    );

        }
        ElementToTrain elementToTrain=userService.getFirstElement(userLoginBody.getId());
        UserDataAndFirstElement userDataAndFirstElement=new UserDataAndFirstElement(userLoginBody,elementToTrain);
         return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDataAndFirstElement);
    }


}
