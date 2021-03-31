package com.sj.web.controller;

import com.sj.web.domain.TsysUser;
import com.sj.web.domain.TsysUserDTO;
import com.sj.web.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShengJie.Wang
 * @date 2021-03-31 17:12
 */
@RestController
public class UserController {

  public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  UserService userService ;

  @GetMapping("/user")
  public TsysUserDTO queryUser(HttpServletRequest request, HttpServletResponse response) {
    TsysUserDTO tsysUser = userService.getTsysUserById(1);
    return tsysUser;
  }

}
