import { useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import '../../Assets/Styles/auth.css'
import Login from "./Login";
import Signup from "./SignUp";

const Auth = (props) => {

    const LOGIN_STATE = 'login';
    const SIGNUP_STATE = 'signup';
    const navigate = useNavigate();

    let [formState, changeFormState] = useState(() => LOGIN_STATE);

    const changeHandler = (e) => {
        if(e.target.id === LOGIN_STATE) {
            changeFormState(LOGIN_STATE);
            navigate('/auth/login');
        } else if(e.target.id === SIGNUP_STATE) {
            changeFormState(SIGNUP_STATE);
            navigate('/auth/signup');
        } else {
            console.log(e.target.id, SIGNUP_STATE);
        }
    };
    
    return (
        <div className="auth-container">
            <div className="wrapper">
            <div className="form-container">
                <div className="slide-controls">
                <input type="radio" name="slide" id="login" checked={formState === LOGIN_STATE} onChange={changeHandler} />
                <input type="radio" name="slide" id="signup" checked={formState === SIGNUP_STATE} onChange={changeHandler} />
                <label htmlFor="login" className="slide login">Login</label>
                <label htmlFor="signup" className="slide signup">Signup</label>
                <div className="slider-tab"></div>
                </div>
            </div>
            <Routes>
                <Route path="/login" exact 
                    element={<Login updateUser={props.updateUser} updateToken={props.updateToken} />}
                />
                <Route path="/signup" exact
                    element={<Signup changeTab={changeFormState} />}
                />
            </Routes>
            </div>
        </div>
    );
};

export default Auth;