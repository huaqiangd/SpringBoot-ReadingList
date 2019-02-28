package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.chapter02.Book;
import com.example.chapter02.ReadingListService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// @ContextConfiguration  //加载上下文(SpringBootTest已经包括了？，但是单独使用，可以设置配置类或者配置文件的所在位置)
//@WebAppConfiguration      //开启Web上下文测试（MockMvc,SpringBootTest已经生成一个WebApplicationContext）
public class MockMvcTest extends BasicTest {

    @Autowired
    WebApplicationContext webContext;     //
    @Autowired
    ReadingListService readingListServicel;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void readerBooks() {
        List<Book> books = readingListServicel.readerBooks("11");
        books.forEach(book -> System.out.println(book));
        System.out.println(books);
    }

    @Test
    @SqlGroup({
            @Sql("/sql/readinglistcontroller.addtoreadinglist/clear.sql"),
            @Sql("/sql/readinglistcontroller.readerbook2/init.sql")

    })
    public void readerBooks2() throws Exception {
        mockMvc.perform(get("/readingList/reader001"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", Matchers.hasSize(1)));
    }

    @Test
    @SqlGroup({
            @Sql("/sql/readinglistcontroller.addtoreadinglist/clear.sql")
    })
    public void addToReadingList() throws Exception {
        ////逐个添加 请求参数，不需要添加 @RequestBody，否则报错
        // mockMvc.perform(post("/readingList/newReader")
        //         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        //         .param("title", "book title")
        //         .param("author", "book author")
        //         .param("isbn", "book isbn")
        //         .param("description", "book description"))
        //         .andExpect(status().is3xxRedirection())
        //         .andExpect(header().string("Location", "/readingList/newReader"));

        //已对象的形式添加 请求参数，controller 方法需要 添加 @RequestBody，否则报错
        Book book = new Book();
        book.setAuthor("book author");
        book.setIsbn("book isbn");
        book.setDescription("book description");
        book.setTitle("book title");
        String requestJson = JSONObject.toJSONString(book);
        mockMvc.perform(post("/readingList/newReader")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList/newReader"));

        Book expectedBook = new Book();
        expectedBook.setReader("newReader");
        expectedBook.setAuthor("book author");
        expectedBook.setTitle("book title");
        expectedBook.setIsbn("book isbn");
        expectedBook.setDescription("book description");

        mockMvc.perform(get("/readingList/newReader"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", Matchers.hasSize(1)));
        //.andExpect(model().attribute("books", Matchers.contains(Matchers.samePropertyValuesAs(expectedBook))));

    }

}
