package cedric.tom.model;

public class Photo {
	private String title;
	private int id;
	private String source;
	
	public Photo(int id, String title, String source) {
		setId(id);
		setTitle(title);
		setSource(source);
	}
	
	public Photo(String title, String source) {
		this(-1, title, source);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String toString(){
		return id + ". " + title + ": " + source;
	}
}
