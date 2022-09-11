import { useNavigate } from "react-router-dom";
import Letter from "../Models/Letter";
import {FormEvent,    useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'
import {ElementToTrain} from "../Models/ElementToTrain";
import axios from "axios";
import {User} from "../Models/User";
import './Training.css'


    export default function Training(props: {
        signToTrain: Letter;
        giveSign(): any;
        user:User;
        setUser:any;
        userId:string;
        trainingLetter:ElementToTrain;
        setTrainingLetter:any
    }) {



        const answerWasCorrect=() => {
            toast.success("Das ist richtig!",{position: toast.POSITION.TOP_CENTER});
        }
        const answerWasIncorrect=(shownSign:string,correctSpelling :string) =>{
            toast.error(<>
                <p>Leider Falsch</p>
                <p>richtig ist:{ shownSign} ={ correctSpelling}</p>
                </>,{position:toast.POSITION.TOP_CENTER});
        }


        const navigate = useNavigate();
        const [inputValue, setInputValue] = useState<string>("");
         const submitInput = (event: FormEvent<HTMLFormElement>) => {
            event.preventDefault();
            setInputValue("");
             if(inputValue===props.trainingLetter.spelling){
                answerWasCorrect();
                axios.put("/user/nextElement/"+props.user.id ,
                    {letterAsString:props.trainingLetter.letterAsString
                        ,spelling:props.trainingLetter.spelling
                        ,alphabetId:props.trainingLetter.alphabetId
                        ,letterId:props.trainingLetter.letterId
                        ,correctAnswer:'true'}
            ).then((response)=>{
                    props.setTrainingLetter(response.data)})






            } else {

                 answerWasIncorrect(props.trainingLetter.letterAsString, props.trainingLetter.spelling);
                 axios.put("/user/nextElement/"+props.user.id ,
                    {letterAsString:props.trainingLetter.letterAsString
                        ,spelling:props.trainingLetter.spelling
                        ,alphabetId:props.trainingLetter.alphabetId
                        ,letterId:props.trainingLetter.letterId
                        ,correctAnswer:'false'}
                ).then((response)=>{
                    props.setTrainingLetter(response.data)})


            }


        }







        return (
            <>

                <button className="headbutton" id="userbutton" onClick={() => navigate("/changeuserdata")}>User</button>
                <button className="headbutton" id="alphabetbutton" onClick={() => navigate("/selectalphabet")}>Alphabet</button>
                 <h2>Alphabet üben</h2>
                <p id="elementToTrain"> {props.trainingLetter.letterAsString}</p>



                <form onSubmit={submitInput}>
                    <input autoFocus value={inputValue} onChange={event => setInputValue(event.target.value)}/>
                    <button>Eingabe</button>
                </form>
                <ToastContainer />
            </>
        )
    }
