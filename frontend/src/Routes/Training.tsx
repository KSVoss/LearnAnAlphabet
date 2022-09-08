import { useNavigate } from "react-router-dom";
import Alphabet from "../Models/Alphabet";
import Letter from "../Models/Letter";
import {FormEvent, FormEventHandler, useEffect, useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'
import {ElementToTrain} from "../Models/ElementToTrain";
import axios from "axios";
import {User} from "../Models/User";


    export default function Training(props: {
        signToTrain: Letter;
        giveSign(): any;
        user:User;
        setUser:any;
        userId:String;
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
        const [letterToTrain,setLetterToTrain]=useState<ElementToTrain>({letterAsString:"",spelling:"",alphabetId:0,letterId:0,correctAnswer:true});

        const showUserId=()=>{
            console.log(props.userId);

        }

        const navigate = useNavigate();
        const [inputValue, setInputValue] = useState<string>("");
         let richtig:boolean;
        const submitInput = (event: FormEvent<HTMLFormElement>) => {
            event.preventDefault();
            setInputValue("");
            //if (inputValue === letterToTrain.spelling) {
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





            //    axios.put("/wastrained/"+props.userId,'isAnswerCorrect=true');

            } else {

                //answerWasIncorrect(letterToTrain.letterAsString,letterToTrain.spelling);
                answerWasIncorrect(props.trainingLetter.letterAsString, props.trainingLetter.spelling);
                //axios.put("/wastrained/"+ props.userId,'isAnswerCorrect=false');
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
        let elementToTrain:ElementToTrain={letterAsString:"",spelling:"",letterId:0,alphabetId:0,correctAnswer:false};
        const getNewLetterToTrain=()=>{
            if(elementToTrain.letterAsString!=="")return;
            const result=axios.get("/nextelement/"+props.userId)
                .then(response => {
                    console.log(response.data.letterAsString);
                    return response.data;
                })
                .then((data) => setLetterToTrain( data))
                 .catch(error => console.error(error))

        }

            //useEffect(() => getNewLetterToTrain(), []);





        return (
            <>

                <button onClick={() => navigate("/changeuserdata")}>User</button>
                <button onClick={() => navigate("/selectalphabet")}>Alphabet</button>
                 <h2>Alphabet üben</h2>
                Element to Train {props.trainingLetter.letterAsString}



                <form onSubmit={submitInput}>
                    <input value={inputValue} onChange={event => setInputValue(event.target.value)}/>
                    <button>Eingabe</button>
                </form>
                <ToastContainer />
            </>
        )
    }
