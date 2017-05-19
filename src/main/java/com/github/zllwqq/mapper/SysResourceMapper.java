package com.github.zllwqq.mapper;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.github.zllwqq.entity.SysResource;

@Repository
public interface SysResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysResource record);

    int insertSelective(SysResource record);

    SysResource selectByPrimaryKey(Long id);
    
    Set<String> selectPermissionByUsername(String username);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKey(SysResource record);
}