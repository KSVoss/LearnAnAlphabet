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
        System.out.println(userid);
        List<LetterToSelect> letterToSelectList=userService.getListOfLetters(userid);
        System.out.println(letterToSelectList.toString());
         return letterToSelectList;
    }



    @PutMapping("/user/selectElement/{userid}")
    public List<LetterToSelect> selectElementNew(@PathVariable String userid,@RequestBody SelectedElement selectedElement){
         return userService.selectElementNew(selectedElement,userid);

    }



    @GetMapping("/user/login")
    public  UserLoginBody login(UserLoginData userLoginData){
        System.out.println(userLoginData.toString());
        UserLoginBody userLoginBody=userService.userLogin(userLoginData);
        System.out.println(userLoginBody);

       // ElementToTrain elementToTrain=userService.getFirstElement(userLoginBody.getId());
        return  userLoginBody ;
     }

     @GetMapping("/user/first/{userid}")
    public ElementToTrain first(@PathVariable String userid){
        System.out.println("vorher:"+userid);
        ElementToTrain elementToTrain=userService.getFirstElement(userid);
        System.out.println("nachher:"+userid);
        System.out.println(elementToTrain);
        return elementToTrain;
     }
}
