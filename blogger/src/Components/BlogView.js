import { useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import '../Assets/Styles/blogview.css';

const BlogView = (props) => {

    let buttonStyle = {display: 'none'};

    const { blogId } = useParams();
    const navigate = useNavigate();
    const getBlog = () => {
        let blog = {
            id: blogId,
            title: 'Sample Title of the Blog',
            description: 'React is a free and open-source front-end JavaScript library for building user interfaces based on UI components. It is maintained by Meta and a community of individual developers and companies.',
            content: '<p>The trail to the left had a "Danger! Do Not Pass" sign telling people to take the trail to the right. This wasn\'t the way Zeke approached his hiking. Rather than a warning, Zeke read the sign as an invitation to explore an area that would be adventurous and exciting. As the others in the group all shited to the right, Zeke slipped past the danger sign to begin an adventure he would later regret.</p><br><br>' +
                '<p>She nervously peered over the edge. She understood in her mind that the view was supposed to be beautiful, but all she felt was fear. There had always been something about heights that disturbed her, and now she could feel the full force of this unease. She reluctantly crept a little closer with the encouragement of her friends as the fear continued to build. She couldn\'t help but feel that something horrible was about to happen.</p>',
            username: 'editableUser'
        };

        if(props.user && props.user.username === blog.username) {
            buttonStyle = {display: 'block'};
        }

        return blog;
    };

    
    const blog = getBlog();
    
    useEffect(() => {
        if (props.user === undefined) {
            console.log('navigating');
            navigate('/auth/login');
        }
        
        props.updateHeader(blog.title);
        return () => {
            props.resetHeader();
        };
    });

    const goToEdit = () => {
        navigate(`/edit/${blogId}`);
    }

    return (
        <div className="view-container">
            <h3 id="description">{blog.description}</h3>
            <div id="content" dangerouslySetInnerHTML={{ __html: blog.content }}>
            </div>

            <button style={buttonStyle} className="edit-button" onClick={goToEdit}>Edit</button>
        </div>
    );
};

export default BlogView;