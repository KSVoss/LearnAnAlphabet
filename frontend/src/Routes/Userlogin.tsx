import { useNavigate } from "react-router-dom";

export default function Userlogin(){
    const navigate = useNavigate();

    return(
        <>
            <h2>Login</h2>
            <p>Nickname</p><input/>
            <p>Password</p><input/>
            <button onClick={()=>navigate("/newuser")}>anlegen</button>
            <button onClick={()=>navigate("/training")}>login</button>
            <button onClick={()=>navigate("/forgotpassword")}>Password vergessen</button>
        </>
    )
}