package com.example.chapter02;

import com.example.chapter02.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by YanMing on 2017/3/14.
 */
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByReader(String reader);
}
