package Model;
import java.util.Scanner;
public class LibraryManagementSystem {
	private static String currentUserRole;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataHandler db = new DataHandler();
        int loggedInUserId = -1;

        while (true) {
            try {
                if (loggedInUserId == -1) {
                    // Authentication Menu
                    System.out.println("\n==== Welcome to Library System ====");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Exit");
                    System.out.print("Enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter Username: ");
                            String uname = sc.nextLine();
                            System.out.print("Enter Password: ");
                            String pass = sc.nextLine();
                            System.out.print("Enter user_Type (Student/Teacher): ");
                            String user_type = sc.nextLine();
                            db.registerUser(name, uname, pass, user_type);
                            break;
                        case 2:
                            System.out.print("Enter Username: ");
                            String lu = sc.nextLine();
                            System.out.print("Enter Password: ");
                            String lp = sc.nextLine();
                            loggedInUserId = db.loginUser(lu, lp);
                            break;
                        case 3:
                            System.out.println("👋 Exiting...");
                            sc.close();
                            return;
                        default:
                            System.out.println("❌ Invalid Choice!");
                    }
                } else {
                    // Library Menu
                    System.out.println("\n==== Library Menu ====");
                    System.out.println("1. Add Book");
                    System.out.println("2. View Books");
                    System.out.println("3. Issue Book");
                    System.out.println("4. Return Book");
                    System.out.println("5. Add Previous Year Paper");
                    System.out.println("6. View Papers");
                    System.out.println("7. Logout");
                    System.out.print("Enter choice: ");
                    int ch = sc.nextInt();
                    sc.nextLine();

                    switch (ch) {
                    case 1:
                        System.out.print("Enter Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        
                        // Pass the logged-in user's role
                        db.addBook(title, author, currentUserRole);
                        break;
                        case 2:
                            db.viewBooks();
                            break;
                        case 3:
                            System.out.print("Enter Book ID: ");
                            int bid = sc.nextInt();
                            db.issueBook(loggedInUserId, bid);
                            break;
                        case 4:
                            System.out.print("Enter Book ID: ");
                            int bookId = sc.nextInt();
                            db.returnBook(bookId);
                            break;
                        case 5:
                            System.out.print("Enter Subject: ");
                            String sub = sc.nextLine();
                            System.out.print("Enter Year: ");
                            int yr = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter File Path: ");
                            String path = sc.nextLine();
                            db.addPaper(sub, yr, path);
                            break;
                        case 6:
                            db.viewPapers();
                            break;
                        case 7:
                            loggedInUserId = -1;
                            System.out.println("🔒 Logged Out!");
                            break;
                        default:
                            System.out.println("❌ Invalid choice!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


