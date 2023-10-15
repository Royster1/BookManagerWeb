package com.book.service;

import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Borrow> getBorrowList();
    void returnBook(String id);

    // 过滤掉已经被借阅掉的书籍
    List<Book> getActiveBookList();

    List<Student> getStudentList();

    void addBorrow(int sid, int bid);

    Map<Book, Boolean> getBookList();

    void deleteBook(int bid);

    // 添加书籍
    void addBook(String title, String desc, double price);
}
