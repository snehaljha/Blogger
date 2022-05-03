package com.simple.blogger.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean isUserExist(User user) {
        TypedQuery<Long> q = entityManager.createQuery("Select count(o) from User o where o.username = :username", Long.class);
        q.setParameter("username", user.getUsername());
        Long result = q.getSingleResult();
        return result != 0l;
    }

    @Transactional
    public void registerUser(User user) {
        entityManager.persist(user);
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> q = entityManager.createQuery("Select o from User o where o.username = :username", User.class);
        q.setParameter("username", username.trim());
        return q.getSingleResult();
    }
}
