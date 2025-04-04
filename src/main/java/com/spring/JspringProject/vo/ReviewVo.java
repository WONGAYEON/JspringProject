package com.spring.JspringProject.vo;

import lombok.Data;

@Data
public class ReviewVo {
	private int idx;
	private String part;
	private int partIdx;
	private String mid;
	private String nickName;
	private int star;
	private String content;
	private String rDate;
	
	private int replyIdx;
	private int reviewIdx;
	private String replyMid;
	private String replyNickName;
	private String replyRDate;
	private String replyContent;
}
