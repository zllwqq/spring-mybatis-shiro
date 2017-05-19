package com.github.zllwqq.service;

import com.github.zllwqq.entity.SysUser;

public interface SysUserService {

	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	public SysUser get(Long id);
	
	/**
	 * 根据username查询
	 * @param username
	 * @return
	 */
	public SysUser getByUsername(String username);
}
