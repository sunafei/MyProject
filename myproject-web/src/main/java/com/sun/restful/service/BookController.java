package com.sun.restful.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.restful.Book;
import com.sun.restful.BookService;
import com.sun.restful.util.ServResultUtils;

public class BookController extends ActionSupport implements ModelDriven<Object>, SessionAware, ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -7948283880381716611L;

	protected Map<String, Object> sessionAtt;
	protected HttpServletResponse response;
	protected HttpServletRequest request;

	private String id;
	private Object model;
	public Book book = new Book();
	private List<Book> list = new ArrayList<>();
	private BookService bookService = new BookService();

	public BookController() {
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	// http://localhost:8080/myproject-web/app/book
	public void index() throws IOException {
		Object searchModel = bookService.getList();
		setModel(ServResultUtils.getResult(searchModel));
	}

	// http://localhost:8080/myproject-web/app/book/1/get
	public void get() throws IOException {
		Book book = bookService.getBook(id);
		setModel(ServResultUtils.getResult(book));
	}
	// http://localhost:8080/myproject-web/app/book/1/delete
	public void delete() {
		bookService.delete(id);
	}
	// http://localhost:8080/myproject-web/app/book/saveOrUpdate?book.id=2&book.name=111&book.age=111
	public void saveOrUpdate() throws IOException {
		if (StringUtils.isBlank(book.getId())) {
			book.setId(UUID.randomUUID().toString());
			bookService.add(book);
		} else {
			bookService.edit(book);
		}
		setModel(ServResultUtils.getResult(book));
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		if (StringUtils.isNotBlank(id)) {
			this.model = bookService.getBook(id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSession(Map att) {
		this.sessionAtt = att;
	}

	public Map<String, Object> getSessionAtt() {
		return sessionAtt;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setRequestAttribute(String name, Object obj) {
		request.setAttribute(name, obj);
	}

	@SuppressWarnings("unchecked")
	public <T> T getRequestAttribute(String name) {
		return (T) request.getAttribute(name);
	}

	public String getRequestHeader(String name) {
		return request.getHeader(name);
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
}
