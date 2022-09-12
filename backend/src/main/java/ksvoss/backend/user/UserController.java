package ksvoss.backend.user;

import ksvoss.backend.exeptions.SelectedAlphabetIsNullException;
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

        int selectedAlphabetId=0;
        try {
            selectedAlphabetId = Integer.parseInt(selectedAlphabet);
        }catch(RuntimeException e){
            throw new SelectedAlphabetIsNullException();
        }
        userService.selectAlphabet(userid, selectedAlphabetId);
        return userService.getListOfLettersNew(userid,selectedAlphabetId);
    }
    @PutMapping("/user/nextElement/{userid}")
    public ElementToTrain nextElement(@PathVariable String userid, @RequestBody ElementToTrain trainedElement){
        return userService.saveResultAndGetNextElement(userid,trainedElement);
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
        return new UserDataAndFirstElement(userLoginBody,elementToTrain);
     }

}
