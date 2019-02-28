package com.example.chapter02;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by YanMing on 2017/3/14.
 */
@Entity(name = "readinglist_book")
@Data
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDHexGenerator"
    )
    private String id;
    private String reader;
    private String isbn;
    private String title;
    private String author;
    private String description;
}