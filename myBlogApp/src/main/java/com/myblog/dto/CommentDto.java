package com.myblog.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
public class CommentDto {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private String email;
    private String quotation;

}
