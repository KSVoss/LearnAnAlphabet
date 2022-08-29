import { useNavigate } from "react-router-dom";

export default function Newuser(){
    const navigate=useNavigate();
    return(
        <>
            <h2>Neuen Nutzer anlegen</h2>
            <p>Nickname</p><input/>
            <p>Mailadresse</p><input/>
            <p>Password</p><input/>
            <p>Password wiederholen</p><input/>
            <button onClick={()=>navigate("/selectalphabet")}>CreateUser</button>
        </>
    )
}