package com.example.chapter02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YanMing on 2017/3/14.
 */
@Service
public class ReadingListService {
    @Autowired
    private BookRepository readingListRepository;

    public List<Book> readerBooks(String reader) {
        List<Book> readingList = readingListRepository.findByReader(reader);
        return readingList;
    }

    public String addToReadingList( Book book) {
        Book save = readingListRepository.save(book);
        return save.getId();
    }
}
