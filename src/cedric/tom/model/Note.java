package cedric.tom.model;

import cedric.tom.exception.DiaryException;

public class Note {
	private int id;
	private String title;
	private String content;
	
	public Note(int id, String title, String content) throws DiaryException {
		setTitle(title);
		setContent(content);
	}
	
	public Note(String content, String title) throws DiaryException {
		this(-1, title, content);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) throws DiaryException {
		if (content == null || content.length() <= 0)
			throw new DiaryException("A note must have some content, throw me a bone here.");
		
		this.content = content;
	}
}
