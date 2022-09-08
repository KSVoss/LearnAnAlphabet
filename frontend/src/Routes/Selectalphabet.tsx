import { useNavigate } from "react-router-dom";
import {SetStateAction, useEffect, useState} from "react";
import {NameOfAlphabet} from "../Models/NameOfAlphabet";
import axios from "axios";
import {toast} from "react-toastify";
import {NameOfAlphabetsAndSelectedAlphabet} from "../Models/NameOfAlphabetsAndSelectedAlphabet";
import {LetterToSelect} from "../Models/LetterToSelect";
import {SelectedAlphabetBody} from "../Models/SelectedAlphabetBody";
import {User} from "../Models/User";
import {ElementToTrain} from "../Models/ElementToTrain";



export default function Selectalphabet(props: {
    userId:string
    , selectedAlphabet:number
    , setSelectedAlphabet:any
    , setTrainingLetter:any
    ,user:User
    ,setUser:any}) {
    const navigate = useNavigate();
    const [nameOfAlphabets, setNameOfAlphabets] = useState<NameOfAlphabet[]>([]);
    const [nameOfLetters, setNameOfLetters] = useState<LetterToSelect[]>([]);

    const goToTraining=()=>{
        axios.put("/user/nextElement/"+props.user.id ,
            {letterAsString:""
                ,spelling:""
                ,alphabetId:-1
                ,letterId:-1
                ,correctAnswer:'true'}
        ).then((response)=>{
            console.log(response.data.letterAsString);
            return response.data})
            .then((data)=>props.setTrainingLetter(data))






        navigate("/training");


    }

    const getAllAlphabets = () => {
        axios.get("/getallalphabetnames/"+props.user.id )
            .then(response => {
                return response.data
            })
            .then(data => {
                    setNameOfAlphabets(data.nameOfAlphabetList);
                    props.setSelectedAlphabet(data.alphabetId);
                    getAllLettersOfAlphabet( );
                }
            )

            .catch(error => console.error(error))
    }
    const getAllLettersOfAlphabet = ( ) => {
         axios.get("/getletters/"+props.user.id)
            .then(response => {
                return response.data
            })
            .then(data => setNameOfLetters(data))
            .catch(error => console.error(error));
    }

    const selectLetterEvent=(event:any)=>{
        console.log("key:"+event.target.key);
        console.log("value"+event.target.value);
        console.log("name"+event.target.name);
        axios.put("/user/selectElement/"+props.user.id,
            {alphabetId:props.selectedAlphabet,letterId:event.target.name,selected:'true' })
            .then((response)=>{
                setNameOfLetters(response.data)
            })

    }
    const selectAnotherAlphabet = (event:any)=>{
        console.log("Auswahl Alphabet:"+event.target.value);
        props.setSelectedAlphabet(event.target.value);

        axios.put( "/selectAlphabet/"+ props.user.id,'selectedAlphabet='+event.target.value)

        .then((response)=>{
            setNameOfLetters(response.data)})


       // getAllLettersOfAlphabet( );
    }
    useEffect(


        () => getAllAlphabets(), [ ]
    );
    useEffect(
        ()=>getAllLettersOfAlphabet(),[ ]
    );




    return(
        <>
            <h2>SelectAlphabet</h2>

            <label>

                Alphabete<br/>
            <select onChange={selectAnotherAlphabet}>
                {nameOfAlphabets
                    .map(nameOfAlpha =>
                        <option value={nameOfAlpha.id}
                        key={nameOfAlpha.name}>{nameOfAlpha.name}

                         </option>
                    )}
            </select>
            </label>

            <p>Ausgewählter AlphabetId:{props.selectedAlphabet}</p>





                 {nameOfLetters
                    .map((nameOfLetter) => <label>

                        <input
                            type="checkbox"
                            key={nameOfLetter.letterId}
                            checked={nameOfLetter.selected}
                            name={""+nameOfLetter.letterId}
                            onChange={selectLetterEvent}


                        /> {nameOfLetter.signAsText}({nameOfLetter.letterId})
                        {nameOfLetter.timesPassed} von {nameOfLetter.timesShowed} letzte:{nameOfLetter.timesPassedLast}
                        <br/>



                    </label>)}



            <button onClick={goToTraining}>OK</button>

         </>)
}