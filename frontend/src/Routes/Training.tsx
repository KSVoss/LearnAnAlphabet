import { useNavigate } from "react-router-dom";
import Alphabet from "../Models/Alphabet";
import Letter from "../Models/Letter";
import {FormEvent, FormEventHandler, useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'


    export default function Training(props: { signToTrain: Letter; giveSign(): any }) {



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
        const [inputValue, setInputValue] = useState("");
        let richtig:boolean;
        const submitInput = (event: FormEvent<HTMLFormElement>) => {
            event.preventDefault();
            setInputValue("");
            if (inputValue === props.signToTrain.spelling) {
                answerWasCorrect();
            } else {
                answerWasIncorrect(props.signToTrain.signAsText,props.signToTrain.spelling);
            }


        }


        return (
            <>
                <button onClick={() => navigate("/changeuserdata")}>User</button>
                <button onClick={() => navigate("/selectalphabet")}>Alphabet</button>
                 <h2>Alphabet üben</h2>
                {props.giveSign()}


                <p>{props.signToTrain.signAsText}</p>
                <form onSubmit={submitInput}>
                    <input value={inputValue} onChange={event => setInputValue(event.target.value)}/>
                    <button>Eingabe</button>
                </form>
                <ToastContainer />
            </>
        )
    }
