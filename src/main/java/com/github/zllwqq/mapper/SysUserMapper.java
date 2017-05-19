package com.github.zllwqq.mapper;

import org.springframework.stereotype.Repository;

import com.github.zllwqq.entity.SysUser;

@Repository
public interface SysUserMapper {
	public int deleteByPrimaryKey(Long id);

    public int insert(SysUser record);

    public int insertSelective(SysUser record);

    public SysUser selectByPrimaryKey(Long id);
    
    public SysUser selectByUsername(String username);

    public int updateByPrimaryKeySelective(SysUser record);

    public int updateByPrimaryKey(SysUser record);
}