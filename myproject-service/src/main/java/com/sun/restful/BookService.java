package com.sun.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
	private static Map<String, Book> books = new HashMap<>();
	static {
		for (int i = 0; i < 10; i++) {
			Book book = new Book(i + "", i, "name" + i);
			books.put(i + "", book);
		}
	}

	public List<Book> getList() {
		 return new ArrayList<Book>(books.values());
	}

	public Book getBook(String id) {
		return books.get(id);
	}
	
	public void add(Book book) {
		books.put(book.getId(), book);
	}
	
	public void edit(Book book) {
		books.put(book.getId(), book);
	}
	
	public void delete(String id) {
		books.remove(id);
	}
}
