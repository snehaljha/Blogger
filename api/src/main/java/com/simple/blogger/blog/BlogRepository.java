package com.simple.blogger.blog;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class BlogRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Blog> getBlogs() {
        TypedQuery<Blog> q = entityManager.createQuery("Select o from Blog", Blog.class);
        List<Blog> blogs = q.getResultList();
        if(blogs == null) {
            return Collections.emptyList();
        }

        return blogs;
    }
}
