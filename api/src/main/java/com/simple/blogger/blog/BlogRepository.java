package com.simple.blogger.blog;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class BlogRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Blog> getBlogs() {
        TypedQuery<Blog> q = entityManager.createQuery("Select o from Blog o", Blog.class);
        List<Blog> blogs = q.getResultList();
        if(blogs == null) {
            return Collections.emptyList();
        }

        return blogs;
    }

    @Transactional
    public void persist(Blog blog) {
        entityManager.persist(blog);
    }

    public Blog getByBlogId(Long blogId) {
        return entityManager.find(Blog.class, blogId);
    }

    @Transactional
    public void delete(Blog blog) {
        entityManager.remove(blog);
    }

    @Transactional
    public void merge(Blog blog) {
        entityManager.merge(blog);
    }
}
