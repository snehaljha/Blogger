import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../api/BloggerApi';
import '../../Assets/Styles/home.scss';
import BlogCard from './BlogCard';

const Home = (props) => {

    const navigate = useNavigate();
    const user = props.user;

    let allBlogs = [];

    useEffect(() => {
        if(user === undefined) {
            console.log('navigating');
            navigate('/auth/login');
        }

        const headers = {
            "auth-token": props.token
        };

        api.get('/blog/list', {headers}).then(response => {
            allBlogs = response.data;
            setBlogs(allBlogs);
        }).catch(er => {
            console.error(er);
        });

    }, [user, navigate]);
    
    let [blogs, setBlogs] = useState(() => allBlogs);

    const renderBlogCards = () => {
        return blogs.map(i => (<BlogCard blog={i} key={i.id} />));
    };

    const filterBlog = (e) => {
        let searchStr = e.target.value;
        let filteredBlogs = allBlogs.filter(i => i.title.toLowerCase().includes(searchStr.toLowerCase()) || i.description.toLowerCase().includes(searchStr.toLowerCase()) || i.author.toLowerCase().includes(searchStr.toLowerCase()));
        setBlogs(filteredBlogs);
    };

    const createBlog = () => {
        navigate('/create');
    };

    return (
        <div>
            <div className='create' onClick={createBlog}>+</div>
            <div className='search-container'>
                <input type='text' placeholder='Search...' onChange={filterBlog} />
                <div className="search"></div>
            </div>
            <div className='container'>
                {renderBlogCards()}
            </div>
        </div>
    )
};

export default Home;