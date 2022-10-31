package com.library.management.LibraryManagement.repository;

import com.library.management.LibraryManagement.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends MongoRepository<Book, String> {

//    @Query("SELECT b FROM Book b WHERE b.bookName LIKE %?1%")
//    List<Book> findAllMatchingBooks(String book);

      List<Book> findByBookNameContainingIgnoreCase(String bookName);
}
