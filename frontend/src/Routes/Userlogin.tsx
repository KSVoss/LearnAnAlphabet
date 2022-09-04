import { useNavigate } from "react-router-dom";
import {ChangeEvent, FormEvent, useState} from "react";
import {UserLogin} from "../Models/UserLogin";
import axios from "axios";
import {ResponseDataStringAsJSON} from "../Models/ResponseDataStringAsJSON";
import Letter from "../Models/Letter";
import {toast, ToastContainer} from "react-toastify";
import {User} from "../Models/User";
import {ElementToTrain} from "../Models/ElementToTrain";

export default function Userlogin(
    props: { userId: String;
            setUserId:any;
            setSelectedAlphabet:any;
            user:User;
            setUser:any;
            trainingLetter:ElementToTrain;
            setTrainingLetter:any
            }){
    const navigate = useNavigate();
    const [inputMailadress,setInputMailadress]=useState<string>("");
    const [inputPassword,setInputPassword]=useState<string>("");
    const [userIdJASON,setUserIdJSON]=useState<ResponseDataStringAsJSON>();
    const onMailadressChange  = (event: ChangeEvent<HTMLInputElement>) => {
        setInputMailadress(event.target.value)
    }
    const onPasswordChange   = (event: ChangeEvent<HTMLInputElement>) => {
        setInputPassword(event.target.value)
    }




    const onLoginSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (inputMailadress === "") return;
        if (inputPassword === "") return;

        /* axios.get("/user/login",{params:{mailadress:inputMailadress,password:inputPassword}})
            .then(response => {
                 return response.data
            })
            .then((data) => setUserIdJSON(data))
             // .then(data=>console.log("data:"+data))
            .catch(error => console.error(error))*/
        try {
            const request = await axios.get("/user/login", {
                params: {
                    mailadress: inputMailadress,
                    password: inputPassword
                }
            });
             //setUserIdJSON(request.data);
            props.setUser(request.data.userLoginBody);
            props.setTrainingLetter(request.data.elementToTrain);
            console.log("data.user:"+request.data.userLoginBody);
            console.log("nickname:"+request.data.userLoginBody.nickname);
            console.log("id:"+request.data.userLoginBody.id);
            console.log("selectedAlphabet:"+request.data.userLoginBody.selectedAlphabet);
            console.log("weightedRandomize:"+request.data.userLoginBody.weightedRandomize);
            console.log("letterAsString:"+request.data.elementToTrain.letterAsString);
            console.log("spelling:"+request.data.elementToTrain.spelling);
            console.log("alphabetId:"+request.data.elementToTrain.alphabetId);
            console.log("letterId:"+request.data.elementToTrain.correctAnswer);



            navigate("/training");

            //console.log("In login userId:"+request.data.userId);
            //props.setUserId(request.data.userId);
            //console.log("userid:" + props.userId);
            //props.setSelectedAlphabet(request.data.userId);
            //navigate("/training");




        }catch (error){
            setInputPassword("");
            setInputMailadress("");
            toast.error(<>
                Die Kombination aus<br/>Passwort und Mailadresse<br/>ist unbekannt


           </>, {position:toast.POSITION.TOP_CENTER}

            );
            console.log(error);
        }



    }

    return(
        <>

            <h2>Login</h2>
            <form onSubmit={onLoginSubmit}>
                <label>Mailadress<input type="text" onChange={onMailadressChange} value={inputMailadress}/></label>
                <label>Password <input type="text" onChange={onPasswordChange} value={inputPassword}/></label>
                <button>login</button>
            </form>
            <button onClick={()=>navigate("/newuser")}>anlegen</button>
            <button onClick={()=>navigate("/forgotpassword")}>Password vergessen</button>
            <ToastContainer/>
        </>
    )
}