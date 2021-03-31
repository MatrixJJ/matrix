package com.sj.web.service.impl;

import com.sj.web.domain.TsysUser;
import com.sj.web.domain.TsysUserDTO;
import com.sj.web.mapper.TsysUserMapper;
import com.sj.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShengJie.Wang
 * @date 2021-03-31 18:05
 */
@Service
public class UserServiceImpl implements UserService {

  private static  final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class) ;
  
  @Autowired
  TsysUserMapper tsysUserMapper ;
  @Override
  public TsysUserDTO getTsysUserById(Integer userId) {
    TsysUser tsysUser = tsysUserMapper.selectByPrimaryKey(userId);
    TsysUserDTO userDTO = new TsysUserDTO(tsysUser) ;
    return userDTO;
  }
}
