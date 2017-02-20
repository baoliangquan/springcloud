package com.company.cloud.module.model.manager;

import com.company.cloud.base.common.CommonStatusEnum;
import com.company.cloud.base.model.Entity;

import java.util.Date;

/**
 * Created by yoara on 2016/4/25.
 * 使用人数据模型
 */
public class ManagerModel extends Entity<String> {
    private String name;
    private String positionId;
    private String phoneNumber;
    private String password;
    private Date createTime;
    private CommonStatusEnum status;

//    /** 登录时查询的权限列表 **/
//    private Set<AuthorityAnnotationEnums> authSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CommonStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CommonStatusEnum status) {
        this.status = status;
    }

//    public Set<AuthorityAnnotationEnums> getAuthSet() {
//        return authSet;
//    }
//
//    public void setAuthSet(Set<AuthorityAnnotationEnums> authSet) {
//        this.authSet = authSet;
//    }
}
