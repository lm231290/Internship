package databasetasks;

import java.sql.*;
import java.util.ArrayList;

public class JDBC {

    public JDBC(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private String url;
    private String username;
    private String password;

    private ArrayList<Object[]> sendQuery(String query) {

       ArrayList<Object[]> objects = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                resultSet.close();
                statement.close();
                connection.close();
                return null;
            }
            int columnCount = resultSet.getMetaData().getColumnCount();
            do {
                Object[] addedArray = new Object[columnCount];
                objects.add(addedArray);
                for (int i = 0; i < columnCount; i++) {
                    addedArray[i] = resultSet.getObject(i);
                }
            } while (resultSet.next());

        } catch (SQLException ignore) {ignore.printStackTrace();}
        return objects;
    }

    public void deleteBook(String name) {
        sendQuery("DELETE FROM book WHERE Name='" + name + "';");
    }

    public ArrayList<Object[]> getAuthorBooks(String authorName) {
        return sendQuery("select a.Id, book.Name, authorId from book" +
                " join book_author ba on book.Id = ba.bookId" +
                " join author a on ba.authorId = a.Id" +
                " where a.Name='"+authorName+"';");
    }

    public void editBookAuthor(String bookName, String newAuthor) {
        sendQuery("UPDATE book_author SET authorId=(select Id from author where Name='" + newAuthor + "' LIMIT 1) where bookId=(select Id from book where Name='" + bookName + "' LIMIT 1);");
    }

    public void addAuthor(String authorName) {
        sendQuery("insert into author(Name) values ('" + authorName +" ');");
    }

    public void addBook(String authorName, String bookName) {
        ArrayList<Object[]> result = sendQuery(
                "select * from book_author" +
                        " join book b on book_author.bookId = b.Id" +
                        " join author a on book_author.authorId = a.Id" +
                        " where b.Name='" + bookName + "' and a.Name='" + authorName + "';"
        );

        if (result == null || result.size() == 0) {
            String strBookInsert = "call addBook(?, ?);";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(strBookInsert)
            ) {
                preparedStatement.setString(1, bookName);
                preparedStatement.setString(2, authorName);
                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
