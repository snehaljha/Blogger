import { Component, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ImageResize from "quill-image-resize-module-react";
import ReactQuill, { Quill } from "react-quill";
import '../../Assets/Styles/blogedit.css';
import '../../../node_modules/react-quill/dist/quill.snow.css';
Quill.register('modules/ImageResize', ImageResize);


class BlogEditUtil extends Component {

    module = {
        ImageResize: {
            parchment: Quill.import('parchment')
        }
    };

    blog = this.props.blog;

    updateBlog(blog) {
        this.blog = blog;
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
            this.updateBlog({...this.blog, user: this.props.user});
            console.log('will save blog ', this.blog);
        }

        return (
            <div className="blogEditContainer">
                <input placeholder='Title' className='title-edit' onChange={(e) => {this.updateBlog({...this.blog, title: e.target.value})}} />
                <textarea placeholder='Description' id='description-box' style={{resize: 'none'}} onInput={autoResize} className='description-edit' onChange={(e) => {this.updateBlog({...this.blog, description: e.target.value})}} />
                <div>
                    <ReactQuill preserveWhitespace="true" theme="snow" modules={this.module} value={this.blog.content} onChange={(e) => this.updateBlog({...this.blog, content: e})} />
                </div>
                <button className='save-btn' onClick={saveBlog}>Save</button>
            </div>
        );
    };
}

export default function BlogEdit(props) {
    const navigate = useNavigate();

    useEffect(() => {
        if(props.user === undefined) {
            console.log('navigating');
            navigate('/auth/login');
        }
    }, [navigate, props.user]);

    return <BlogEditUtil {...props} />;

}