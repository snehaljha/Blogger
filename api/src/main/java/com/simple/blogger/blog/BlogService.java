package com.simple.blogger.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    private BlogRepository blogRepository;
    
    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getBlogList() {
        List<Blog> blogs = blogRepository.getBlogs();

        blogs.forEach(i -> {
            i.getUser().setPassword(null);
            i.setFileName(null);
        });

        return blogs;
    }
}
