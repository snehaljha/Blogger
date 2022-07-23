import { useNavigate } from 'react-router-dom';
import '../../Assets/Styles/blogcard.css';

const BlogCard = (props) => {
    let blog = props.blog;

    const navigate  = useNavigate();

    const navigateToBlog = () => {
        navigate(`/view/${blog.id}`);
    };

    console.log(blog);

    return (
        <div className="blog-container" onClick={navigateToBlog}>
            <div className="blog-body">
                <h3 className="title">
                    {blog.title}
                </h3>
                <div className="description">
                    {blog.description}
                </div>
            </div>
            <h5 className="author-body">
                By: {blog.author}
            </h5>
        </div>
    );
};

export default BlogCard;