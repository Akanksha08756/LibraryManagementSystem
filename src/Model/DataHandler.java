package Model;
import java.sql.*;


public class DataHandler {
	    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
	    private static final String USER = "Developer";
	    private static final String PASS = "Admin@9696";

	    public Connection getConnection() throws SQLException {
	    
	    	    try {
	    	        // JDBC Driver load karne ke liye
	    	        Class.forName("com.mysql.cj.jdbc.Driver");
	    	    } catch (ClassNotFoundException e) {
	    	        e.printStackTrace(); // Agar driver nahi mila to error show karega
	    	    }
	    	    // Database connection return karega
	    	    return DriverManager.getConnection(URL, USER, PASS);
	    	}

	      
	    

	    // Add a new book
	    public void addBook(String title, String author, String role) throws SQLException {
	        if (!"Librarian".equalsIgnoreCase(role)) {
	            System.out.println("❌ Permission Denied! Only Librarian can add books.");
	            return;  // student/teacher ke liye function yahi ruk jayega
	        }

	        try (Connection con = getConnection()) {
	            PreparedStatement ps = con.prepareStatement(
	                "INSERT INTO books(title, author, available) VALUES(?,?,true)"
	            );
	            ps.setString(1, title);
	            ps.setString(2, author);
	            ps.executeUpdate();
	            System.out.println("✅ Book Added Successfully!");
	        }
	    }

	    // View all books
	    public void viewBooks() throws SQLException {
	        try (Connection con = getConnection()) {
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery("SELECT * FROM books");
	            while (rs.next()) {
	                System.out.println(rs.getInt("book_id") + " | " + rs.getString("title") +
	                        " | " + rs.getString("author") +
	                        " | Available: " + rs.getBoolean("available"));
	            }
	        }
	    }

	    // Issue a book
	    public void issueBook(int user_Id, int book_Id) throws SQLException {
	        try (Connection con = getConnection()) {
	            PreparedStatement check = con.prepareStatement("SELECT available FROM books WHERE book_id=?");
	            check.setInt(1, book_Id);
	            ResultSet rs = check.executeQuery();
	            if (rs.next() && rs.getBoolean("available")) {
	                PreparedStatement ps1 = con.prepareStatement("INSERT INTO transactions(user_id, book_id, issue_date) VALUES(?,?,CURDATE())");
	                ps1.setInt(1, user_Id);
	                ps1.setInt(2, book_Id);
	                ps1.executeUpdate();

	                PreparedStatement ps2 = con.prepareStatement("UPDATE books SET available=false WHERE book_id=?");
	                ps2.setInt(1, book_Id);
	                ps2.executeUpdate();

	                System.out.println("📖 Book Issued Successfully!");
	            } else {
	                System.out.println("❌ Book Not Available!");
	            }
	        }
	    }

	    // Return a book
	    public void returnBook(int book_Id) throws SQLException {
	        try (Connection con = getConnection()) {
	            PreparedStatement ps1 = con.prepareStatement("UPDATE books SET available=true WHERE book_id=?");
	            ps1.setInt(1, book_Id);
	            ps1.executeUpdate();

	            PreparedStatement ps2 = con.prepareStatement("UPDATE transactions SET return_date=CURDATE() WHERE book_id=? AND return_date IS NULL");
	            ps2.setInt(1, book_Id);
	            ps2.executeUpdate();

	            System.out.println("✅ Book Returned Successfully!");
	        }
	    }
	 // Add new previous year paper
	    public void addPaper(String subject, int year, String file_path) throws SQLException {
	        try (Connection con = getConnection()) {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO papers(subject, year, file_path) VALUES(?,?,?)");
	            ps.setString(1, subject);
	            ps.setInt(2, year);
	            ps.setString(3, file_path);
	            ps.executeUpdate();
	            System.out.println("✅ Paper Added Successfully!");
	        }
	    }

	    // View all papers
	    public void viewPapers() throws SQLException {
	        try (Connection con = getConnection()) {
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery("SELECT * FROM papers");
	            System.out.println("=== Previous Year Papers ===");
	            while (rs.next()) {
	                System.out.println(rs.getInt("paper_id") + " | " + rs.getString("subject") +
	                        " | " + rs.getInt("year") +
	                        " | File: " + rs.getString("file_path"));
	            }
	        }
	    }
	 // Register a new user
	    public void registerUser(String name, String username, String password, String user_type) throws SQLException {
	        try (Connection con = getConnection()) {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO users(name, username, password, user_type) VALUES(?,?,?,?)");
	            ps.setString(1, name);
	            ps.setString(2, username);
	            ps.setString(3, password);
	            ps.setString(4, user_type);
	            ps.executeUpdate();
	            System.out.println("✅ User Registered Successfully!");
	        }
	    }

	    // Login user
	    public int loginUser(String username, String password) throws SQLException {
	        try (Connection con = getConnection()) {
	            PreparedStatement ps = con.prepareStatement("SELECT user_id FROM users WHERE username=? AND password=?");
	            ps.setString(1, username);
	            ps.setString(2, password);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                System.out.println("✅ Login Successful!");
	                return rs.getInt("user_id");  // return user ID
	            } else {
	                System.out.println("❌ Invalid Username/Password!");
	                return -1;
	            }
	        }
	    }


	}


