import { useNavigate } from "react-router-dom";
import {SetStateAction, useEffect, useState} from "react";
import {NameOfAlphabet} from "../Models/NameOfAlphabet";
import axios from "axios";
import {toast} from "react-toastify";
import {NameOfAlphabetsAndSelectedAlphabet} from "../Models/NameOfAlphabetsAndSelectedAlphabet";
import {LetterToSelect} from "../Models/LetterToSelect";
import {SelectedAlphabetBody} from "../Models/SelectedAlphabetBody";



export default function Selectalphabet(props: {userId:string, selectedAlphabet:number, setSelectedAlphabet:any}) {
    const navigate = useNavigate();
    const [nameOfAlphabets, setNameOfAlphabets] = useState<NameOfAlphabet[]>([]);
    const [nameOfLetters, setNameOfLetters] = useState<LetterToSelect[]>([]);


    const getAllAlphabets = () => {
        axios.get("/getallalphabetnames/"+props.userId )
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
         axios.get("/getletters/"+props.userId)
            .then(response => {
                return response.data
            })
            .then(data => setNameOfLetters(data))
            .catch(error => console.error(error));
    }

    const selectAnotherAlphabet = (event:any)=>{
        console.log("Auswahl Alphabet:"+event.target.value);
        props.setSelectedAlphabet(event.target.value);

        axios.put( "/selectAlphabet/"+ props.userId,'selectedAlphabet='+event.target.value );
        getAllLettersOfAlphabet( );
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
                        <option value=
                            {nameOfAlpha.id}>{nameOfAlpha.name}
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
                            checked={nameOfLetter.isSelected}

                        /> {nameOfLetter.signAsText}



                    </label>)}



            <button onClick={()=>navigate("/training")}>OK</button>

         </>)
}