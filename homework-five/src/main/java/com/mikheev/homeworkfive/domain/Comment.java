package com.mikheev.homeworkfive.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }
}
