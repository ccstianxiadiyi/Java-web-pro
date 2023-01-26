package com.chen;

import com.chen.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootZhengheApplicationTests {
    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        bookService.save();
    }

}
