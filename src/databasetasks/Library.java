package databasetasks;

import java.sql.*;
import java.util.ArrayList;

public class Library {
    public Library() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        jdbc = new JDBC( "jdbc:mysql://lcoalhost:3306/library",
                    "root",
                    "AKRJzFi3");
    }

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private JDBC jdbc;

    public void createBook(String bookName, String authorName) {
        jdbc.addBook(authorName, bookName);
    }

    public ArrayList<Book> retrieveAuthorBooks(String authorName) {
        ArrayList<Object[]> list = jdbc.getAuthorBooks(authorName);
        ArrayList<Book> resultList = new ArrayList<>();
        list.stream().map( o -> resultList.add(new Book((int)o[0], o[1].toString(), (int)o[2])));
        return resultList;
    }

    public void deleteBook(String bookName) {
        jdbc.deleteBook(bookName);
    }

    public void editBooksAuthor(String bookName, String newAuthor) {
        jdbc.editBookAuthor(bookName, newAuthor);
    }

    public void addAuthor(String name) {
        jdbc.addAuthor(name);
    }
//
//    public void



}
