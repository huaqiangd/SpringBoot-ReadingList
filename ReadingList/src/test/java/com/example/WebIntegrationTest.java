package com.example;

import com.example.chapter02.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

public class WebIntegrationTest extends BasicTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void readerBooks() {
        String reader = "newReader";
        ResponseEntity<String> forEntity = restTemplate.getForEntity("/readingList/newReader", String.class);
        System.out.println(forEntity.getBody());
        System.out.println(forEntity.getHeaders());
        System.out.println(forEntity.getStatusCode());
    }

    @Test
    public void readerBooks2() throws Exception {

    }

    @Test
    @SqlGroup({
            @Sql("/sql/readinglistcontroller.addtoreadinglist/clear.sql")
    })
    public void addToReadingList() throws Exception {
        Book book = new Book();
        book.setTitle("222");
        book.setDescription("222");
        book.setIsbn("222");
        book.setAuthor("222");
        ResponseEntity<String> forEntity = restTemplate.postForEntity("/readingList/newReader", book, String.class);
        System.out.println(forEntity.getHeaders().getLocation());
        System.out.println(forEntity.getHeaders());
        System.out.println(forEntity.getStatusCode());
    }

}
