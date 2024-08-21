package com.library;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagement {

    public static void main(String[] args) {
        // Load the Spring context from applicationContext.xml
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean
        BookService bookService = context.getBean(BookService.class);

        // Use the BookService
        bookService.doService();
        context.close();
    }
}
