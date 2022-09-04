package com.danny.web.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeView extends Notice {

	private int cmtCount;
	
	public NoticeView(int id, String title, String writerId, Date regdate, int hit, String files,boolean pub, int cmtCount) {
		super(id, title, writerId, regdate, hit, files, "", pub);
		this.cmtCount = cmtCount;
	} // 

} // end class
