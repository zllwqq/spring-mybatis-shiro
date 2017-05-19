package com.github.zllwqq.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zllwqq.mapper.SysResourceMapper;

@Service
public class SysResourceServiceImpl implements SysResourceService {

	private SysResourceMapper sysResourceMapper;

	public SysResourceMapper getSysResourceMapper() {
		return sysResourceMapper;
	}

	@Autowired
	public void setSysResourceMapper(SysResourceMapper sysResourceMapper) {
		this.sysResourceMapper = sysResourceMapper;
	}
	
	public Set<String> findPermissionByUsername(String username) {
		return sysResourceMapper.selectPermissionByUsername(username);
	}
}
