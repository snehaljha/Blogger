package com.simple.blogger.blog;

import lombok.Data;

@Data
public class BlogDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private String content;

    public BlogDto() {}

    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.author = blog.getUser().getUsername();
        this.description = blog.getDescription();
    }

    public BlogDto(Blog blog, String content) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.author = blog.getUser().getUsername();
        this.content = content;
        this.description = blog.getDescription();
    } 
}
