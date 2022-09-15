import {useNavigate} from "react-router-dom";
import {FormEvent, useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import axios from "axios";
import {User} from "../Models/User";


export default function Newuser(
    props: {
        user: User;
        setUser: any;
    }
) {
    const navigate = useNavigate();
    const [inputPassword, setInputPassword] = useState<string>("");
    const [repeatPassword, setRepeatPassword] = useState<string>("");
    const [inputNickname, setInputNickname] = useState<string>("");
    const [inputMailadress, setInputMailadress] = useState<string>("");

    const submitNewUser = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (inputPassword !== repeatPassword) {
            toast.error("Die Beiden Passwörter müssen übereinstimmen");
            return;
        }
        if (inputPassword === "") {
            toast.error(<>Passwort entspricht nicht den Vorgaben</>,
                {position: toast.POSITION.TOP_CENTER});
            return;
        }
        console.log("Mailadress:" + inputMailadress);
        console.log("Nickname:" + inputNickname);
        console.log("Password:" + inputPassword);
        try {
            const request = await axios.post("/user/newuser",
                {
                    mailadress: inputMailadress,
                    nickname: inputNickname,
                    password: inputPassword
                });
            props.setUser(request.data);
            navigate("/selectalphabet");
        } catch (error) {
            setInputPassword("");
            setInputMailadress("");
            toast.error(<>
                    Die Mailadresse ist schon vergeben
                </>, {position: toast.POSITION.TOP_CENTER}
            );
        }
    }
    return (
        <>
            <h2>Neuen Nutzer anlegen</h2>
            <form onSubmit={submitNewUser}>
                <p>Nickname</p><input value={inputNickname} onChange={event => setInputNickname(event.target.value)}/>
                <p>Mailadresse</p><input value={inputMailadress}
                                         onChange={event => setInputMailadress(event.target.value)}/>
                <p>Password</p><input value={inputPassword} type="password"
                                      onChange={event => setInputPassword(event.target.value)}/>
                <p>Password wiederholen</p><input value={repeatPassword} type="password"
                                                  onChange={event => setRepeatPassword(event.target.value)}/>
                <button>CreateUser</button>
            </form>
            <ToastContainer/>
        </>
    )
}