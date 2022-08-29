import { useNavigate } from "react-router-dom";

export default function Forgotpassword(){
    const navigate=useNavigate();
    return(
        <>
            <h2>Passwort vergessen</h2>
            <p>Mailadresse</p><input/>
            <p>NeuesPassword</p><input/>
            <p>Password wiederholen</p><input/>
            <p>Sicherheitsabfrage per Mail</p><input/>
            <button onClick={()=>navigate("/userlogin")}>Password zurücksetzen</button>
        </>
    )
}