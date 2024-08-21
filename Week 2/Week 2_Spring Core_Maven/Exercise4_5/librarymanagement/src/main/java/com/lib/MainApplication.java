package com.lib;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get the BookService bean
        BookService bookService = (BookService) context.getBean("bookService");

        // Add some books
        bookService.addBook("To Kill a Mockingbird");
        bookService.addBook("1984");
        bookService.addBook("The Great Gatsby");

        // Display all books
        bookService.displayBooks();
        context.close();
    }
}

