import { useNavigate } from "react-router-dom";

export default function Changeuserdata(){
    const navigate = useNavigate();
    return(
        <>
            <h2>ChangeUserData</h2>
            <button onClick={()=>navigate("/userlogin")}>Logout</button>
        </>

    )
}