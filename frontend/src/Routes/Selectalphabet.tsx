import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

import {NameOfAlphabet} from "../Models/NameOfAlphabet";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import {LetterToSelect} from "../Models/LetterToSelect";
import {User} from "../Models/User";
import './Selectalphabet.css'


export default function Selectalphabet(props: {
    userId: string, selectedAlphabet: number, setSelectedAlphabet: any, setTrainingLetter: any, user: User, setUser: any, nameOfSelectedAlphabet: string, setNameOfSelectedAlphabet: any
}) {
    const navigate = useNavigate();
    const [nameOfAlphabets, setNameOfAlphabets] = useState<NameOfAlphabet[]>([]);
    const [nameOfLetters, setNameOfLetters] = useState<LetterToSelect[]>([]);

    const goToTraining = () => {
        axios.put("/user/nextElement/" + props.user.id,
            {
                letterAsString: ""
                , spelling: ""
                , alphabetId: -1
                , letterId: -1
                , correctAnswer: 'true'
            }
        ).then((response) => {
            console.log(response.data.letterAsString);
            return response.data
        })
            .then((data) => {
                props.setTrainingLetter(data);
                navigate("/training");
            })
            .catch(() => {
                toast.error(<>
                    <p>In der aktiven Schrift müssen mindestens</p>
                    <p>zwei Zeichen ausgewählt sein</p>
                </>, {position: toast.POSITION.TOP_CENTER});

            })
    }
    const getAllAlphabets = () => {
        axios.get("/getallalphabetnames/" + props.user.id)
            .then(response => {
                return response.data
            })
            .then(data => {
                    setNameOfAlphabets(data.nameOfAlphabetList);
                    props.setSelectedAlphabet(data.alphabetId);
                    props.setNameOfSelectedAlphabet(data.nameOfAlphabetList[data.alphabetId]);
                    getAllLettersOfAlphabet();
                }
            )
            .catch(error => console.error(error))
    }
    const getAllLettersOfAlphabet = () => {
        axios.get("/getletters/" + props.user.id)
            .then(response => {
                return response.data
            })
            .then(data => setNameOfLetters(data))
            .catch(error => console.error(error));
    }
    const selectLetterEvent = (event: any) => {
        axios.put("/user/selectElement/" + props.user.id,
            {alphabetId: props.selectedAlphabet, letterId: event.target.name, selected: 'true'})
            .then((response) => {
                setNameOfLetters(response.data)
            })
    }
    const selectAnotherAlphabet = (event: any) => {
        console.log("Auswahl Alphabet:" + event.target.value);
        props.setSelectedAlphabet(event.target.value);
        axios.put("/selectAlphabet/" + props.user.id, 'selectedAlphabet=' + event.target.value)

            .then((response) => {
                setNameOfLetters(response.data)
            })
    }
    useEffect(
        () => getAllAlphabets(), []
    );
    useEffect(
        () => getAllLettersOfAlphabet(), []
    );
    return (
        <>
            <h2>SelectAlphabet</h2>
            <label id="selectAlphabetDropbox">
                <select onChange={selectAnotherAlphabet}>
                    {nameOfAlphabets
                        .map((nameOfAlpha, index) =>
                            <option value={nameOfAlpha.id}
                                    selected={index === props.selectedAlphabet}
                                    key={index}>{nameOfAlpha.name}

                            </option>
                        )}
                </select>
            </label>
            <div id="selectLetterBox">
                {nameOfLetters
                    .map((nameOfLetter) => <label>
                        <input
                            type="checkbox"
                            key={nameOfLetter.letterId}
                            checked={nameOfLetter.selected}
                            name={"" + nameOfLetter.letterId}
                            onChange={selectLetterEvent}
                        /> {nameOfLetter.signAsText}({nameOfLetter.letterId})
                        {nameOfLetter.timesPassed} von {nameOfLetter.timesShowed} letzte:{nameOfLetter.timesPassedLast}
                        <br/>
                    </label>)}
            </div>
            <button onClick={goToTraining}>OK</button>
            <ToastContainer/>
        </>)
}