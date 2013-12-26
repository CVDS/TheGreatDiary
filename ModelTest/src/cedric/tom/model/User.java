package cedric.tom.model;

import cedric.tom.exception.DiaryException;

public class User {
	private String username;
	private String password;

	public User(String username, String password) throws DiaryException {
		setUsername(username);
		setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws DiaryException {
		if (username == null)
			throw new DiaryException("A username is required. ");
		
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws DiaryException {
		if (password == null || password.length() < 6)
			throw new DiaryException("Password needs to be atleast 6 characters.");
		
		this.password = password;
	}
}
