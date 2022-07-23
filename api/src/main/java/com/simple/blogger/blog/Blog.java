package com.simple.blogger.blog;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.simple.blogger.user.User;

import lombok.Data;

@Entity
@Data
public class Blog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    private User user;

    private String title;

    private String description;

    private String fileName;
}
