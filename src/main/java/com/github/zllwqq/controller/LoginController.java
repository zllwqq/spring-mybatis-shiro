package com.github.zllwqq.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.zllwqq.entity.SysUser;
import com.github.zllwqq.service.SysUserService;

@Controller
@RequestMapping(value = "/")
public class LoginController {
	private final Logger logger = LogManager.getLogger(LoginController.class); 
	
	private SysUserService sysUserService;

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	@Autowired
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	@RequestMapping(value = "/sysUser/{id}", method = RequestMethod.GET)
	public @ResponseBody SysUser get(@PathVariable("id") long id) {
		SysUser user = sysUserService.get(id);
		return user;
	}
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		logger.info("测试{}", "login");
		String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
		logger.info("异常信息：{}", exceptionClassName);
		String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if("jCaptcha.error".equals(exceptionClassName)) {
            error = "验证码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        
        Subject subject = SecurityUtils.getSubject();
        logger.info("是否拥有角色admin:{}", subject.hasRole("admin"));
		//...省略
        model.addAttribute("error", error);
		return "login";
	}
}
