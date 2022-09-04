import React, {useState} from 'react';
import './App.css';
import { HashRouter, Route, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import Userlogin from "./Routes/Userlogin";
import Newuser from "./Routes/Newuser";
import Training from "./Routes/Training";
import Selectalphabet from "./Routes/Selectalphabet";
import Forgotpassword from "./Routes/Forgotpassword";
import Changeuserdata from "./Routes/Changeuserdata";
import Home from "./Routes/Home";
import useHooks from "./useHooks";
export default function App() {
    const hooks=useHooks();

    return(

          <HashRouter>
              <h1>Learn an Alphabet</h1>
            <Routes>
                <Route path={"/"} element={<Userlogin
                    userId={hooks.userId}
                    setUserId={hooks.setUserId}
                    setSelectedAlphabet={hooks.setSelectedAlphabet}
                    user={hooks.user}
                    setUser={hooks.setUser}
                    trainingLetter={hooks.trainingLetter}
                    setTrainingLetter={hooks.setTrainingLetter}/>}/>
                <Route path={"/userlogin"} element={<Userlogin
                    userId={hooks.userId}
                    setUserId={hooks.setUserId}
                    setSelectedAlphabet={hooks.setSelectedAlphabet}
                    user={hooks.user}
                    setUser={hooks.setUser}
                    trainingLetter={hooks.trainingLetter}
                    setTrainingLetter={hooks.setTrainingLetter}
                />}/>
                <Route path={"/newuser"} element={<Newuser/>}/>
                <Route path={"/training"} element={<Training
                    signToTrain={hooks.signToTrain}
                    giveSign={hooks.giveSign}
                    userId={hooks.userId}
                    trainingLetter={hooks.trainingLetter}
                    setTrainingLetter={hooks.trainingLetter}

                />}/>
                <Route path={"/selectalphabet"} element={<Selectalphabet
                    userId={hooks.userId}
                    selectedAlphabet={hooks.selectedAlphabet}
                    setSelectedAlphabet={hooks.setSelectedAlphabet}/>}/>
                <Route path={"/forgotpassword"} element={<Forgotpassword/>}/>
                <Route path={"/changeuserdata"} element={<Changeuserdata/>}/>
            </Routes>

        </HashRouter>
              )


}


