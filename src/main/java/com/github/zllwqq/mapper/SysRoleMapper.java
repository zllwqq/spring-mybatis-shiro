package com.github.zllwqq.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.github.zllwqq.entity.SysRole;

@Repository
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);
    
    List<SysRole> selectAll();
    
    List<SysRole> selectByUsername(String username);
    
    Set<String> selectRoleByUsername(String username);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}