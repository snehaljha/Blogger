import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from '../api/BloggerApi';
import '../Assets/Styles/blogview.css';

const BlogView = (props) => {

    let [buttonStyle, setButtonStyle] = useState(() => ({display: 'none'}));

    const { blogId } = useParams();
    const navigate = useNavigate();
    // const fetchBlog = () => {

    //     const headers = {
    //         'auth-token': props.token
    //     }

    //     api.get('/blog/view?blogId='+blogId, {headers}).then(response => {
    //         setBlog(response.data);
    //         if(props.user && props.user.username === response.data.author) {
    //             setButtonStyle({display: 'flex'});
    //         }
    //         props.updateHeader(response.data.title);
    //     }).catch(error => {
    //         console.error(error);
    //         alert(error);
    //     });

    // };

    
    let [blog, setBlog] = useState(() => ({
        id: 0,
        title: '',
        description: '',
        content: '',
        username: ''
    }));
    
    useEffect(() => {
        
        const headers = {
            'auth-token': props.token
        }

        api.get('/blog/view?blogId='+blogId, {headers}).then(response => {
            setBlog(response.data);
            if(props.user && props.user.username === response.data.author) {
                setButtonStyle({display: 'flex'});
            }
            props.updateHeader(response.data.title);
        }).catch(error => {
            console.error(error);
            alert(error);
        });
    }, []);

    useEffect(() => {
        if (props.user === undefined) {
            console.log('navigating');
            navigate('/auth/login');
        }
        return () => {
            props.resetHeader();
        };
    }, [props, navigate]);

    const goToEdit = () => {
        navigate(`/edit/${blogId}`);
    }

    const deleteBlog = () => {
        console.log('delete ' + blogId);
    }

    return (
        <div className="view-container">
            <h3 id="description">{blog.description}</h3>
            <div id="content" dangerouslySetInnerHTML={{ __html: blog.content }}>
            </div>

            <div className="button-container" style={buttonStyle}>
                <button type="button" className="edit-button" onClick={goToEdit}>Edit</button>
                <button type="button" className="delete-button" onClick={deleteBlog}>Delete</button>
            </div>
        </div>
    );
};

export default BlogView;