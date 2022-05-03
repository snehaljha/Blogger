package com.simple.blogger.blog;

import java.util.List;

import com.simple.blogger.exception.NoBlogFoundException;
import com.simple.blogger.user.User;
import com.simple.blogger.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogService;
    private UserService userService;
    private final Logger logger;

    @Autowired
    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(BlogController.class);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getBlogList() {
        try {
            logger.info("fetching blog list");
            List<BlogDto> blogs = blogService.getBlogList();
            ResponseEntity<List<BlogDto>> responseEntity = new ResponseEntity<>(blogs, HttpStatus.OK);
            logger.info("found " + blogs.size() + " blogs");
            return responseEntity;
        } catch (Exception ex) {
            logger.error(ex.toString());
        }

        return new ResponseEntity<String>("Error fetching blogs", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeBlog(@RequestBody BlogDto blogDto) {
        try {
            logger.info("Writing Blog: ", blogDto.getTitle());
            blogService.writeBlog(blogDto);
            logger.info("Written Blog: ", blogDto.getTitle());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Failed to save blog " + ex);
        }

        return new ResponseEntity<String>("Failed to save blog " + blogDto.getTitle(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewBlog(@RequestParam(name = "blogId") Long blogId) {
        try {
            BlogDto blogDto = blogService.getBlogWithDetails(blogId);
            logger.info("fetched blog " + blogDto.getTitle());
            return new ResponseEntity<>(blogDto, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlog(@RequestParam(name = "blogId") Long blogId) {
        try {
            Blog blog = blogService.getBlogFromId(blogId);
            User user = userService.getCurrentUser();
            if(user.getId() != blog.getUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            blogService.deleteBlog(blog);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoBlogFoundException ex) {
            logger.warn(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
