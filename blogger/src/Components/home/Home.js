import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../Assets/Styles/home.scss';
import BlogCard from './BlogCard';

const Home = (props) => {

    const navigate = useNavigate();
    const user = props.user;

    useEffect(() => {
        if(user === undefined) {
            console.log('navigating');
            navigate('/auth/login');
        }
    }, [user, navigate]);
    

    const defaultBlogs = [
        {
            id: '1',
            title: 'React',
            description: 'Khatri React Description',
            author: 'Khatri User'
        },
        {
            id: '2',
            title: 'Angular',
            description: 'Khatri Angular Description',
            author: 'Khatri User'
        },
        {
            id: '3',
            title: 'Electron',
            description: 'Khatri Electron Description',
            author: 'Chhatri User'
        }
    ];
    
    let [blogs, setBlogs] = useState(() => defaultBlogs);

    const renderBlogCards = () => {
        return blogs.map(i => (<BlogCard blog={i} key={i.id} />));
    };

    const filterBlog = (e) => {
        let searchStr = e.target.value;
        let filteredBlogs = defaultBlogs.filter(i => i.title.toLowerCase().includes(searchStr.toLowerCase()) || i.description.toLowerCase().includes(searchStr.toLowerCase()) || i.author.toLowerCase().includes(searchStr.toLowerCase()));
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