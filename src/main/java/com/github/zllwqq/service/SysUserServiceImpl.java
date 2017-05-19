package com.github.zllwqq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zllwqq.entity.SysUser;
import com.github.zllwqq.mapper.SysUserMapper;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	private SysUserMapper sysUserMapper;
	
	public SysUserMapper getSysUserMapper() {
		return sysUserMapper;
	}

	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	public SysUser get(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
	
	public SysUser getByUsername(String username) {
		return sysUserMapper.selectByUsername(username);
	}
}
