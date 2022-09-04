package com.danny.web.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Notice {
	
	@Getter private Integer id;
	@Getter private String title;
	@Getter private String writerId; 
	@Getter private Date regdate; 
	@Getter private Integer hit;
	@Getter private String files;
	@Getter private String content;
	private boolean pub;
	
	public boolean getPub() {
		return pub;
	} // getPub
	
//	public Notice(int id, String title, String writerId, Date regdate, int hit, String files, String content) {
//		this.id = id;
//		this.title = title;
//		this.writerId = writerId;
//		this.regdate = regdate;
//		this.hit = hit;
//		this.files = files;
//		this.content = content;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getWriterId() {
//		return writerId;
//	}
//
//	public void setWriterId(String writerId) {
//		this.writerId = writerId;
//	}
//
//	public Date getRegdate() {
//		return regdate;
//	}
//
//	public void setRegdate(Date regdate) {
//		this.regdate = regdate;
//	}
//
//	public int getHit() {
//		return hit;
//	}
//
//	public void setHit(int hit) {
//		this.hit = hit;
//	}
//
//	public String getFiles() {
//		return files;
//	}
//
//	public void setFiles(String files) {
//		this.files = files;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	};
//	
	
} // end class
