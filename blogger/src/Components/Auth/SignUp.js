import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../Assets/Styles/signup.css'

const Signup = (props) => {

    const navigate = useNavigate();

    let [form, updateForm] = useState(() => ({
        username: '',
        name: '',
        password: '',
        password2: ''
    }));

    const formChange = (newForm) => {
        updateForm(newForm);
    };

    const validate = () => {
        if(form.name === '' || form.username === '' || form.password === '' || form.password2 === '') {
            alert('All fields are mandatory');
            return false;
        }
        if(form.password !== form.password2) {
            alert('Passwords not matched');
            return false;
        }

        return true;
    };

    const signup = () => {
        let val = validate();
        if(val === true) {
            navigate('/auth/login');
            props.changeTab('login');
        }
    };

    return (
        <div className="signup-container">
            <form>
                <input className="signup-input" type='text' placeholder='Username' value={form.username} onChange={(e) => formChange({...form, username: e.target.value})} />
                <input className="signup-input" type='text' placeholder='Name' value={form.name} onChange={(e) => formChange({...form, name: e.target.value})} />
                <input className="signup-input" type='password' placeholder='Password' value={form.password} onChange={(e) => formChange({...form, password: e.target.value})} />
                <input className="signup-input" type='password' placeholder='Confirm Password' value={form.password2} onChange={(e) => formChange({...form, password2: e.target.value})} />
                <button className='signup-button' onClick={signup}>Sign Up</button>
            </form>
        </div>
    );
};

export default Signup;