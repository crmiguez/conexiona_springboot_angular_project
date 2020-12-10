package com.practicas.conexiona.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class UserGroupUserId implements Serializable {

    @Column(name = "userId")
    private String userId;

    @Column(name = "userGroupId")
    private String userGroupId;

    public UserGroupUserId() {
    }

    public UserGroupUserId(String userId, String userGroupId) {
        this.userId = userId;
        this.userGroupId = userGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }
}
