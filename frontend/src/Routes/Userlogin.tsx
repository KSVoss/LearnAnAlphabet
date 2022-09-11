import { useNavigate } from "react-router-dom";
import {ChangeEvent, FormEvent, useState} from "react";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import {User} from "../Models/User";
import {ElementToTrain} from "../Models/ElementToTrain";
import './Userlogin.css'


export default function Userlogin(
    props: { userId: string;
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

            props.setUser(request.data.userLoginBody);
            props.setTrainingLetter(request.data.elementToTrain);




            navigate("/training");




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
                <label><p className="inputnames">Mailadress</p> <input type="text" onChange={onMailadressChange} value={inputMailadress}/></label>
                <label><p className="inputnames">Password </p> <input type="password" onChange={onPasswordChange} value={inputPassword}/></label>
                <br/><button>login</button>
            </form>
            <br/>
            <button id="createButton" onClick={()=>navigate("/newuser")}>anlegen</button>
            <button id="forgotButton" onClick={()=>navigate("/forgotpassword")}>Password vergessen</button>

            <ToastContainer/>
        </>
    )
}