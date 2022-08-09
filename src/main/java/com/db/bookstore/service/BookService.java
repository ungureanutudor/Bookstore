package com.db.bookstore.service;

import com.db.bookstore.model.Book;
import com.db.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    public List<Book> listAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public void insertBooks(Book book) {
        bookRepository.save(book);
    }
}
