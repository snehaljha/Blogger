import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../api/BloggerApi';
import appConfig from '../../config/AppConfig.json';
import '../../Assets/Styles/login.css';

const Login = (props) => {

    const navigate = useNavigate();

    let [form, updateForm] = useState(() => ({
        username: '',
        password: ''
    }));

    const formChange = (newForm) => {
        updateForm(newForm);
    };

    const validate = () => {
        if(form.username === '' || form.password === '') {
            alert('All fields are mandatory');
            return false;
        }

        return true;
    };

    const login = () => {
        let val = validate();
        if(val === false) {
            return;
        }

        const headers = appConfig.headers;
        
        api.post('/user/login', JSON.stringify(form), {headers}).then((response) => {
            const user = {
                username: form.username,
                name: form.username
            };
            
            props.updateUser(user);

            props.updateToken(response.data);
    
            navigate('/');
        }).catch((er) => {
                console.error(er);
                alert('Login Failed');
        });
    };

    return (
        <div className="login-container">
            <form>
                <input className="login-input" type='text' placeholder='Username' value={form.username} onChange={(e) => formChange({...form, username: e.target.value})} />
                <input className="login-input" type='password' placeholder='Password' value={form.password} onChange={(e) => formChange({...form, password: e.target.value})} />
                <button className='login-button' onClick={login} type="button">Login</button>
            </form>
        </div>
    );
};

export default Login;