package com.simple.blogger.blog;

import lombok.Data;

@Data
public class BlogDto {
    private Long id;
    private String title;
    private boolean owned;
    private String content;

    public BlogDto() {}

    public BlogDto(Blog blog, long userId) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.owned = blog.getUser().getId() == userId;
    }

    public BlogDto(Blog blog, long userId, String content) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.owned = blog.getUser().getId() == userId;
        this.content = content;
    } 
}
