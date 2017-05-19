package com.github.zllwqq.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.zllwqq.entity.SysRole;
import com.github.zllwqq.service.SysRoleService;

@Controller
@RequestMapping(value ="/role")
public class RoleController {

	private final Logger logger = LogManager.getLogger(RoleController.class);
	
	private SysRoleService roleService;
	
	public SysRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(SysRoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping
	public String index(HttpServletRequest request, Model model) {
		logger.info("进入角色");
		List<SysRole> roles = roleService.getAll();
		model.addAttribute("roles", roles);
		return "admin/role/index";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request) {
		
		return "admin/role/add";
	}
	
	
}
