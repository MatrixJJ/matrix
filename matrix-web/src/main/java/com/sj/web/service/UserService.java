package com.sj.web.service;

import com.sj.web.domain.TsysUserDTO;

/**
 * @author ShengJie.Wang
 * @date 2021-03-31 18:05
 */
public interface UserService {

  TsysUserDTO getTsysUserById(Integer userId);

}
