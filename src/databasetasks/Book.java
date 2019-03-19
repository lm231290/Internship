package databasetasks;

public class Book {
    public Book(int id, String name, int authorId) {
        this.Id=id;
        this.name = name;
        this.authorId = authorId;
    }
    private int Id;
    private String name;
    private int authorId;

    public int getId() {
        return Id;
    }

    public int getAuthor() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return Id + name + authorId;
    }
}
