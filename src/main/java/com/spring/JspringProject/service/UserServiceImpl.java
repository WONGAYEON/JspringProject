package com.spring.JspringProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.UserDao;
import com.spring.JspringProject.dao.UserDaoImpl;
import com.spring.JspringProject.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoImpl UserDao; // 서비스객체가 직접 UserDaoImpl 구현객체를 참조하고있다.

	@Override
	public UserVo getUserIdSearch(String mid) {
		return UserDao.getUserIdSearch(mid);
	}

	@Override
	public int setUserInput(UserVo vo) {
		return UserDao.setUserInput(vo);
	}

	@Override
	public UserVo getUserSearchPart(UserVo vo) {
		return UserDao.getUserSearchPart(vo);
	}

	@Override
	public List<UserVo> getUserList() {
		return UserDao.getUserList();
	}

	@Override
	public int setUserDeleteOk(int idx) {
		return UserDao.setUserDeleteOk(idx);
	}

	@Override
	public UserVo getSearchIdx(int idx) {
		return UserDao.getSearchIdx(idx);
	}

	@Override
	public int setUserUpdate(UserVo vo) {
		return UserDao.setUserUpdate(vo);
	}

	@Override
	public int getUserCount() {
		return UserDao.getUserCount();
	}
}
