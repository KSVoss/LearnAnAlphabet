import {useEffect, useState} from "react";
import Alphabet from "./Models/Alphabet";
import axios from "axios";
import Letter from "./Models/Letter";

export default function useHooks(){
    const [alphabets,setAlphabets]=useState<Alphabet[]>([]);
    const defaultLetter:Letter={id:"0",signAsText:"undefined",spelling:""}

    const [signToTrain,setSignToTrain]=useState<Letter>(defaultLetter); //um undefined zu vermeiden

    const fetchAlphabets = () => {
        axios.get("/alphabets")
            .then((response) => response.data)
            .then((data) => setAlphabets(data))
    }

    const giveSign=()=> {
        setSignToTrain(alphabets[0].letters[17])



    }

    useEffect(
        ()=>fetchAlphabets(),
        []);

return{alphabets,giveSign,signToTrain}
}
