package com.sj.web.domain;

/**
 * @author ShengJie.Wang
 * @date 2021-03-31 18:07
 */
public class TsysUserDTO extends TsysUser {

  public  TsysUserDTO(TsysUser tsysUser){
    this.setUserId(tsysUser.getUserId());
    this.setUserName(tsysUser.getUserName());
    this.setUserStatus(tsysUser.getUserStatus());
    this.setGmtCreate(tsysUser.getGmtCreate());
    this.setGmtModify(tsysUser.getGmtModify());
    this.setLoginErrorTimes(tsysUser.getLoginErrorTimes());
    this.setMobileTel(tsysUser.getMobileTel());
    this.setOrgId(tsysUser.getOrgId());
    this.setRemark(tsysUser.getRemark());
  }

}
