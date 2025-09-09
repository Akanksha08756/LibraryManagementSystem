package Model;

public class Bank {
	class Book {
	    private int book_id;
	    private String title;
	    private String author;
	    private boolean available;

	    public Book(int book_id, String title, String author, boolean available) {
	        this.book_id = book_id;
	        this.title = title;
	        this.author = author;
	        this.available = available;
	    }

	    public int getId() { return book_id; }
	    public String getTitle() { return title; }
	    public String getAuthor() { return author; }
	    public boolean isAvailable() { return available; }
	}


}
