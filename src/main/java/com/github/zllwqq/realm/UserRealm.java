package com.github.zllwqq.realm;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zllwqq.entity.SysUser;
import com.github.zllwqq.service.SysResourceService;
import com.github.zllwqq.service.SysRoleService;
import com.github.zllwqq.service.SysUserService;

public class UserRealm extends AuthorizingRealm {

	private SysUserService userService;
	
	private SysRoleService roleService;
	
	private SysResourceService resourceService;
	
	public SysUserService getUserService() {
		return userService;
	}
	
	@Autowired
	public void setUserService(SysUserService userService) {
		this.userService = userService;
	}

	public SysRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(SysRoleService roleService) {
		this.roleService = roleService;
	}

	public SysResourceService getResourceService() {
		return resourceService;
	}

	@Autowired
	public void setResourceService(SysResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roles = roleService.findRoleByUsername(username);
		if (roles.size() > 0) {
			authorizationInfo.setRoles(roles);
		}
		
		Set<String> stringPermissions = resourceService.findPermissionByUsername(username);
		if (stringPermissions.size() > 0) {
			authorizationInfo.setStringPermissions(stringPermissions);
		}
		
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String username = (String)token.getPrincipal();
		
		SysUser user = userService.getByUsername(username);
		
		if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
	}

}
