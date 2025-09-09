package Model;

	// Parent Class: User
	abstract class User {
	    protected int user_id;
	    protected String name;

	    public User(String name) {
	        this.name = name;
	    }

	    public abstract String getUserType();
	    public String getName() { return name; }
	}

	// Child Class: StudentUser
	class StudentUser extends User {
	    public StudentUser(String name) { super(name); }
	    @Override
	    public String getUserType() { return "Student"; }
	}

	// Child Class: TeacherUser
	class TeacherUser extends User {
	    public TeacherUser(String name) { super(name); }
	    @Override
	    public String getUserType() { return "Teacher"; }
	}

	
	