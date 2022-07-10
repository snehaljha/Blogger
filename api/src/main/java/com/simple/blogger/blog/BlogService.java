package com.simple.blogger.blog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.simple.blogger.exception.EmptyBlogException;
import com.simple.blogger.exception.InvalidAuthorException;
import com.simple.blogger.exception.InvalidBlogDescriptionException;
import com.simple.blogger.exception.InvalidBlogTitleException;
import com.simple.blogger.exception.NoBlogFoundException;
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
        return blogRepository.getBlogs().stream().map(BlogDto::new).collect(Collectors.toList());
    }

    public void writeBlog(BlogDto blogDto) throws Exception {
        validate(blogDto);
        User user = userService.getCurrentUser();
        String fileName = "user" + user.getId() + "-" + LocalDateTime.now() + ".blog";
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
        blog.setDescription(blogDto.getDescription());
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

        if(!StringUtils.hasText(blogDto.getDescription())) {
            throw new InvalidBlogDescriptionException(blogDto.getDescription());
        }

        if(!StringUtils.hasText(blogDto.getContent())) {
            throw new EmptyBlogException(blogDto.getContent());
        }
    }

    public BlogDto getBlogWithDetails(Long blogId) throws Exception {
        Blog blog = blogRepository.getByBlogId(blogId);
        String blogContent = readBlog(blog.getFileName());
        return new BlogDto(blog, blogContent);
        
    }

    private String readBlog(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath, fileName)));

        StringBuilder sb = new StringBuilder();

        String temp;
        while((temp = br.readLine()) != null) {
            sb.append(temp+"\n");
        }
        br.close();

        return sb.toString();
    }

    public Blog getBlogFromId(Long blogId) throws Exception {
        Blog blog = blogRepository.getByBlogId(blogId);
        if(blog == null) {
            throw new NoBlogFoundException(blogId);
        }
        return blog;
    }

    public void deleteBlog(Blog blog) {
        String filename = blog.getFileName();
        blogRepository.delete(blog);
        deleteFile(filename);
    }

    public void update(BlogDto blogDto) throws IOException {
        Blog blog = blogRepository.getByBlogId(blogDto.getId());
        User currentUser = userService.getCurrentUser();
        if(!blog.getUser().getUsername().equals(currentUser.getUsername())) {
            throw new InvalidAuthorException(currentUser.getUsername());
        }

        validate(blogDto);
        
        String fileName = "user" + currentUser.getId() + "-" + LocalDateTime.now() + ".blog";
        String prevFileName = blog.getFileName();
        blog.setTitle(blogDto.getTitle());
        blog.setDescription(blogDto.getDescription());
        blog.setFileName(fileName);
        
        writeToFile(fileName, blogDto.getContent());
        try {
            blogRepository.merge(blog);
            deleteFile(prevFileName);
        } catch (Exception ex) {
            deleteFile(fileName);
            throw ex;
        }

    }
}
