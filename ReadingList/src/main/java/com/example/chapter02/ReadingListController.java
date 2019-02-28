package com.example.chapter02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by YanMing on 2017/3/14.
 */
@Controller
@RequestMapping("/readingList")
public class ReadingListController {

    @Autowired
    ReadingListService readingListService;

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readerBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = readingListService.readerBooks(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader,@RequestBody  Book book) { //TestRestTemplate 测试，参数需要加上 @Requestbody 映射到Book对象
        book.setReader(reader);
        String id = readingListService.addToReadingList(book);
        System.out.println("ID=" + id);
        return "redirect:/readingList/{reader}";
    }
}
