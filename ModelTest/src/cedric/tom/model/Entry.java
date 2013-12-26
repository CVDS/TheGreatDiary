package cedric.tom.model;

import java.util.Date;

public class Entry	{
	private int id;
	private Date date; 
	private int photoID;
	private String content;

	public Entry(Date date, String content) {
		this(-1, date, -1, content);
	}
	
	public Entry(int id, Date date, String content) {
		this(id, date, -1, content);
	}
	
	public Entry(Date date, int photoID, String content) {
		this(-1, date, photoID, content);
	}
	
	public Entry(int id, Date date, int photoID, String content) {
		setId(id);
		setDate(date);
		setPhotoID(photoID);
		setContent(content);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getPhotoID() {
		return photoID;
	}
	public void setPhotoID(int photoID) {
		this.photoID = photoID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		return id + " (" + date + ") " + photoID + ": " + content;
	}
}
