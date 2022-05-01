package com.simple.blogger.blog;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogService;
    private final Logger logger;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
        this.logger = LoggerFactory.getLogger(BlogController.class);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getBlogList() {
        try {
            logger.info("fetching blog list");
            List<Blog> blogs = blogService.getBlogList();
            ResponseEntity<List<Blog>> responseEntity = new ResponseEntity<>(blogs, HttpStatus.OK);
            logger.info("found " + blogs.size() + " blogs");
            return responseEntity;
        } catch (Exception ex) {
            logger.error(ex.toString());
        }

        return new ResponseEntity<String>("Error fetching blogs", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
