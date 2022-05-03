package com.simple.blogger.blog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.simple.blogger.exception.EmptyBlogException;
import com.simple.blogger.exception.InvalidBlogTitleException;
import com.simple.blogger.user.User;
import com.simple.blogger.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BlogService {

    private BlogRepository blogRepository;
    private UserService userService;
    private String filePath;
    
    @Autowired
    public BlogService(BlogRepository blogRepository, UserService userService, @Value("${blog.path}") String filePath) {
        this.blogRepository = blogRepository;
        this.userService = userService;
        this.filePath = filePath;
    }

    public List<BlogDto> getBlogList() {
        User user = userService.getCurrentUser();

        List<BlogDto> blogList = blogRepository.getBlogs().stream().map(i -> new BlogDto(i, user.getId())).collect(Collectors.toList());

        return blogList;
    }

    public void writeBlog(BlogDto blogDto) throws Exception {
        validate(blogDto);
        User user = userService.getCurrentUser();
        String fileName = "user" + user.getId() + "-" + LocalDateTime.now();
        writeToFile(fileName, blogDto.getContent());
        try {
            addBlogEntry(fileName, blogDto, user);
        } catch (Exception ex) {
            deleteFile(fileName);
            throw ex;
        }
    }

    private void deleteFile(String fileName) {
        File file = new File(filePath, fileName);
        file.delete();
    }

    private void addBlogEntry(String fileName, BlogDto blogDto, User user) {
        Blog blog = new Blog();
        blog.setFileName(fileName);
        blog.setTitle(blogDto.getTitle());
        blog.setUser(user);

        blogRepository.persist(blog);
    }

    private void writeToFile(String fileName, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
        for(String s: content.split("\n")) {
            bw.write(s + "\n");
            bw.flush();
        }
    }

    private void validate(BlogDto blogDto) {
        if(!StringUtils.hasText(blogDto.getTitle())) {
            throw new InvalidBlogTitleException(blogDto.getTitle());
        }

        if(!StringUtils.hasText(blogDto.getContent())) {
            throw new EmptyBlogException(blogDto.getContent());
        }
    }
}
