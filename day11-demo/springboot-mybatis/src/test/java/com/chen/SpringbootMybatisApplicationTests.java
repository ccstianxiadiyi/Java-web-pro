package com.chen;

import com.chen.dao.BookDao;
import com.chen.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMybatisApplicationTests {
    @Autowired
    private BookDao bookbao;
    @Test
    void contextLoads() {

    }

}
