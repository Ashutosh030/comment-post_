package com.myblog.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "comments")
public class Comment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private String email;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
