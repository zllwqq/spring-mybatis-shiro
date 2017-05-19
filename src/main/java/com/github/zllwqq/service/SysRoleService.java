package com.github.zllwqq.service;

import java.util.List;
import java.util.Set;

import com.github.zllwqq.entity.SysRole;

public interface SysRoleService {

	/**
	 * 根据username查询
	 * @param username
	 * @return
	 */
	public List<SysRole> findByUsername(String username);
	
	/**
	 * 根据username查询role名称
	 * @param username
	 * @return
	 */
	public Set<String> findRoleByUsername(String username);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SysRole> getAll();
}
