package com.library.management.LibraryManagement.dao;

import com.library.management.LibraryManagement.models.Book;
import com.library.management.LibraryManagement.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryDAO {

    private LibraryRepository repository;

    public List<Book> all() {
        return repository.findAll();
    }

    public List<Book> matchField(String id) {
        return repository.findByBookNameContainingIgnoreCase(id);
    }

    @Cacheable(value = "books", key = "#id", unless = "#result != null")
    public Book byId(String id) {
        return repository.findById(id).get();
    }

    @CachePut(value = "books", key = "#book.bookName")
    public Book update(Book book) {
        return repository.save(book);
    }

    @CachePut(value = "books", key = "#book.bookName")
    public Book save(Book book) {
        return repository.save(book);
    }

    @CacheEvict(value = "books", key = "#id")
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public boolean existById(String id) {
        return repository.existsById(id);
    }

    // return limited no of rows per pages or request
    public List<Book> booksByPage(int page, int pageSize) {
        return repository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    // return limited no of rows per pages or request sorted order
    public List<Book> booksByPageSorted(int page, int pageSize, String field) {
        return repository.findAll(PageRequest.of(page, pageSize, Sort.by(field))).getContent();
    }

    @Autowired
    public void setRepository(LibraryRepository repository) {
        this.repository = repository;
    }
}
