package com.github.zllwqq.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zllwqq.entity.SysRole;
import com.github.zllwqq.mapper.SysRoleMapper;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	private SysRoleMapper sysRoleMapper;

	public SysRoleMapper getSysRoleMapper() {
		return sysRoleMapper;
	}

	@Autowired
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}
	
	public List<SysRole> findByUsername(String username) {
		return sysRoleMapper.selectByUsername(username);
	}
	
	public Set<String> findRoleByUsername(String username) {
		return sysRoleMapper.selectRoleByUsername(username);
	}
	
	public List<SysRole> getAll() {
		return sysRoleMapper.selectAll();
	}
}
