import { Component, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ImageResize from "quill-image-resize-module-react";
import ReactQuill, { Quill } from "react-quill";
import '../../Assets/Styles/blogedit.css';
import '../../../node_modules/react-quill/dist/quill.snow.css';
Quill.register('modules/ImageResize', ImageResize);


class BlogEditUtil extends Component {

    constructor() {
        super();
        this.state = {blog: {title: '', description: '', user: '', content: ''}};
    }

    componentDidMount() {
        this.fetchBlog();
    }

    module = {
        ImageResize: {
            parchment: Quill.import('parchment')
        }
    };

    updateBlog(blog) {
        this.setState({...this.state, blog: blog});
    }

    validate() {
        if(this.blog.title === '') {
            return 'Title is mandatory';
        }
        if(this.blog.description === '') {
            return 'Description is Mandatory';
        }

        return undefined;
    }

    fetchBlog() {
        if(!this.props.blogId) {
            return;
        }

        this.setState({...this.state, blog: {
            id: this.props.blogId,
            title: 'Sample Title of the Blog',
            description: 'React is a free and open-source front-end JavaScript library for building user interfaces based on UI components. It is maintained by Meta and a community of individual developers and companies.',
            content: '<p>The trail to the left had a "Danger! Do Not Pass" sign telling people to take the trail to the right. This wasn\'t the way Zeke approached his hiking. Rather than a warning, Zeke read the sign as an invitation to explore an area that would be adventurous and exciting. As the others in the group all shited to the right, Zeke slipped past the danger sign to begin an adventure he would later regret.</p><br><br>' +
                '<p>She nervously peered over the edge. She understood in her mind that the view was supposed to be beautiful, but all she felt was fear. There had always been something about heights that disturbed her, and now she could feel the full force of this unease. She reluctantly crept a little closer with the encouragement of her friends as the fear continued to build. She couldn\'t help but feel that something horrible was about to happen.</p>',
            username: 'editableUser'
        }});
    }
    
    render() {

        const autoResize = (e) => {
            e.target.style.height = 'auto';
            e.target.style.height = e.target.scrollHeight + 'px';
        };

        const saveBlog = () => {
            const invalidMsg = this.validate();
            if(invalidMsg) {
                alert(invalidMsg);
                return;
            }
            this.updateBlog({...this.state.blog, user: this.props.user});
            console.log('will save blog ', this.blog);
        }

        return (
            <div className="blogEditContainer">
                <input placeholder='Title' className='title-edit' value={this.state.blog.title} onChange={(e) => {this.updateBlog({...this.state.blog, title: e.target.value})}} />
                <textarea placeholder='Description' id='description-box' style={{resize: 'none'}} onInput={autoResize} className='description-edit' value={this.state.blog.description} onChange={(e) => {this.updateBlog({...this.state.blog, description: e.target.value})}} />
                <div>
                    <ReactQuill preserveWhitespace="true" theme="snow" modules={this.module} value={this.state.blog.content} onChange={(e) => this.updateBlog({...this.state.blog, content: e})} />
                </div>
                <button className='save-btn' onClick={saveBlog}>Save</button>
            </div>
        );
    };
}

export default function BlogEdit(props) {
    const navigate = useNavigate();

    const { blogId } = useParams();

    useEffect(() => {
        if(props.user === undefined) {
            console.log('navigating');
            navigate('/auth/login');
        }
    }, [navigate, props.user]);

    return <BlogEditUtil {...props} blogId={blogId} />;

}