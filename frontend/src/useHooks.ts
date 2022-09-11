import {useEffect, useState} from "react";
import Alphabet from "./Models/Alphabet";
import axios from "axios";
import Letter from "./Models/Letter";
 import {User} from "./Models/User";
import {ElementToTrain} from "./Models/ElementToTrain";

export default function useHooks(){
    const [alphabets,setAlphabets]=useState<Alphabet[]>([]);
    const [userId,setUserId]=useState<string>("")

    const defaultLetter:Letter={id:"0",signAsText:"undefined",spelling:""}
    const [selectedAlphabet,setSelectedAlphabet]=useState(0);
    const [nameOfSelectedAlphabet, setNameOfSelectedAlphabet]=useState<string>("");

    const [signToTrain,setSignToTrain]=useState<Letter>(defaultLetter); //um undefined zu vermeiden

    const [user,setUser]=useState<User>({id:"",nickname:"",selectedAlphabetId:0,weightedRandomize:false});
    const [trainingLetter,setTrainingLetter]=useState<ElementToTrain>(
        {letterAsString:"",spelling:"",alphabetId:-1,letterId:-1,correctAnswer:false})


    const fetchAlphabets = () => {
        axios.get("/getallalphabets")
            .then((response) => response.data)
            .then((data) => setAlphabets(data))
    }



    const giveSign=()=> {
        setSignToTrain(alphabets[0].letters[17])



    }


    useEffect(
        ()=>fetchAlphabets(),
        []);

return{nameOfSelectedAlphabet,setNameOfSelectedAlphabet, alphabets,giveSign,signToTrain,userId,setUserId,selectedAlphabet,setSelectedAlphabet,user,setUser,trainingLetter,setTrainingLetter}
}
