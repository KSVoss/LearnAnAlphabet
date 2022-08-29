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
        return userService.getNamesOfAlphabet(userid);
    }

    @PutMapping("/wastrined/{userid}")
    public void wasTrained(@PathVariable String userid,@RequestBody boolean isAnswerCorrect){
        userService.wasTrained(userid, isAnswerCorrect);
    }
    @PutMapping("/selectlanguage/{userid}")
    public void selectLanguage(@PathVariable String userid, @RequestBody int alphabetId){
        userService.selectLanguage(userid, alphabetId);
    }

    @GetMapping("/getselectedlanguage/{userid}")
    public String getSelectedLanguage(@PathVariable String userid){
        return userService.getSelectedLanguage(userid);
    }

    @GetMapping("/nextelement/{userid}")
    public ElementToTrain nextelement(@PathVariable String userid){
        return userService.nextElement(userid);

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
    public ResponseEntity<String> login(@RequestBody UserLoginData userLoginData){

        String returnValue=userService.userLogin(userLoginData);

        if(returnValue.equals( "UNKNOWN_USER")|returnValue.equals( "WRONG_PASSWORD"))
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(returnValue);

        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(returnValue);
    }


}
