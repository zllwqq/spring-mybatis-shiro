package com.github.zllwqq.service;

import java.util.Set;

public interface SysResourceService {

	/**
	 * 根据username多表查询permission字段
	 * @param username
	 * @return
	 */
	public Set<String> findPermissionByUsername(String username);
}
