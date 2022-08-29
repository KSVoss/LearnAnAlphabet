import { useNavigate } from "react-router-dom";

export default function Selectalphabet(){
    const navigate=useNavigate();
    return(
        <>
            <h2>SelectAlphabet</h2>
            <button onClick={()=>navigate("/training")}>OK</button>

        </>
    )
}