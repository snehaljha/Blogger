import { Component, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ImageResize from "quill-image-resize-module-react";
import ReactQuill, { Quill } from "react-quill";
import api from '../../api/BloggerApi';
import appConfig from '../../config/AppConfig.json';
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
        if(this.state.blog.title === '') {
            return 'Title is mandatory';
        }
        if(this.state.blog.description === '') {
            return 'Description is Mandatory';
        }

        return undefined;
    }

    fetchBlog() {
        if(!this.props.blogId) {
            return;
        }

        const headers = {
            'auth-token': this.props.token
        };

        api.get('/blog/view?blogId='+this.props.blogId, {headers}).then(response => {
            this.setState({...this.state, blog: {
                id: this.props.blogId,
                title: response.data.title,
                description: response.data.description,
                content: response.data.content,
                username: response.data.author
            }});
        }).catch(error => {
            console.error(error);
            alert(error);
        });
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
            const headers = appConfig.headers;
            headers['auth-token'] = this.props.token;
            const request = {
                title: this.state.blog.title,
                content: this.state.blog.content,
                description: this.state.blog.description
            };

            if(!this.props.blogId) {

                api.post("/blog/write", JSON.stringify(request), {headers}).then((response) => {
                    console.log(response.data);
                }).catch((response) => {
                    console.error(response.data);
                    alert('Some unexpected error');
                });
                return;
            }

            api.put("/blog/update?blogId=" + this.props.blogId, JSON.stringify(request), {headers}).then(() => {
                console.log('updated');
            }).catch(error => {
                console.error(error);
                alert(error);
            });

        }

        const deleteBlog = () => {
            if(!this.props.blogId) {
                return;
            }
            const headers = appConfig.headers;
            headers['auth-token'] = this.props.token;

            api.delete('/blog/delete?blogId='+this.props.blogId, {headers}).then(() => {
                this.props.goToHome();
            }).catch(error => {
                console.error(error);
                alert(error);
            });
        }

        return (
            <div className="blogEditContainer">
                <input placeholder='Title' className='title-edit' value={this.state.blog.title} onChange={(e) => {this.updateBlog({...this.state.blog, title: e.target.value})}} />
                <textarea placeholder='Description' id='description-box' style={{resize: 'none'}} onInput={autoResize} className='description-edit' value={this.state.blog.description} onChange={(e) => {this.updateBlog({...this.state.blog, description: e.target.value})}} />
                <div>
                    <ReactQuill preserveWhitespace="true" theme="snow" modules={this.module} value={this.state.blog.content} onChange={(e) => this.updateBlog({...this.state.blog, content: e})} />
                </div>
                <div className='button-container'>
                    <button className='save-btn' onClick={saveBlog} type="button">Save</button>
                    <button className='delete-button' style={this.props.blogId ? {display: 'block'} : {display: 'none'}} onClick={deleteBlog} type="button">Delete</button>
                </div>
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

    const goToHome = () => {
        navigate('/');
    }

    return <BlogEditUtil {...props} blogId={blogId} goToHome={goToHome} />;

}