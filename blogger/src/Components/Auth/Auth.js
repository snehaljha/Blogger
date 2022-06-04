import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Auth = (props) => {

    const navigate = useNavigate();
    
    useEffect(() => {
        let user = {
            username: 'user',
            name: 'User'
        }

        setTimeout(() => {
            props.updateUser(user);
            navigate('/');
        }, 3000);
    }, []);
    
    return (
        <div>
            Auth Module
        </div>
    );
};

export default Auth;