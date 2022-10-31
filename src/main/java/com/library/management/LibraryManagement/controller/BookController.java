package com.library.management.LibraryManagement.controller;

import com.library.management.LibraryManagement.dao.LibraryDAO;
import com.library.management.LibraryManagement.exception.LibraryManagementException;
import com.library.management.LibraryManagement.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private LibraryDAO libraryDAO;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> library = libraryDAO.all();
        return ResponseEntity.ok(library);
    }

    @GetMapping("/match/name/{name}")
    public ResponseEntity<List<Book>> getAllByBookName(@PathVariable String name) {
        List<Book> library = libraryDAO.matchField(name);
        return ResponseEntity.ok(library);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Book> getBookByName(@PathVariable String name) {
       Book book = libraryDAO.byId(name);
       return ResponseEntity.ok(book);
    }

    @PostMapping("/register")
    public ResponseEntity<Book> registerBook(@RequestBody Book book) {
        if (libraryDAO.existById(book.getBookName()))
            throw new LibraryManagementException("already exist", "book with "+ book.getBookName() +" already exist !!", 400);
        Book book1 = libraryDAO.save(book);
        return ResponseEntity.ok(book1);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if (!libraryDAO.existById(book.getBookName()))
            throw new LibraryManagementException("not exist", "no book with "+ book.getBookName() +" found !!", 400);
        Book book1 = libraryDAO.update(book);
        return ResponseEntity.ok(book1);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteBookById(@PathVariable String name) {
        if (!libraryDAO.existById(name))
            throw new LibraryManagementException("not exist", "no book with "+ name +" found !!", 400);
        libraryDAO.deleteById(name);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/bypages/{page}/{pageSize}")
    public ResponseEntity<List<Book>> getLimitedBooksWithPageSize(@PathVariable int page, @PathVariable int pageSize) {
        List<Book> library = libraryDAO.booksByPage(page, pageSize);
        return ResponseEntity.ok(library);
    }

    @Autowired
    public void setLibraryDAO(LibraryDAO libraryDAO) {
        this.libraryDAO = libraryDAO;
    }
}
