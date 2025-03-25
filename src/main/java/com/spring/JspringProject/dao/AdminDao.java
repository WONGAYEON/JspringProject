package com.spring.JspringProject.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.ComplaintVo;

public interface AdminDao {

	int setMemberLevelChange(@Param("level") int level, @Param("idx") int idx);

	int getMemberTotRecCnt(@Param("level") int level);

	int setBoardComplaintInput(@Param("vo") ComplaintVo vo);

	int setBoardTableComplaintOk(@Param("partIdx") int partIdx);

}
