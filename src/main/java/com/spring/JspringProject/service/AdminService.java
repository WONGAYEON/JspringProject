package com.spring.JspringProject.service;

import com.spring.JspringProject.vo.ComplaintVo;

public interface AdminService {

	int setMemberLevelChange(int level, int idx);

	int getMemberTotRecCnt(int level);

	int setBoardComplaintInput(ComplaintVo vo);

	int setBoardTableComplaintOk(int partIdx);

}
