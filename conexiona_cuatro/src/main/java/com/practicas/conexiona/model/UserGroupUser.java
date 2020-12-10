package com.practicas.conexiona.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="UserGroupUser")
@Table

public class UserGroupUser implements Serializable {

    @EmbeddedId
    private UserGroupUserId userGroupUserId = new UserGroupUserId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userGroupId")
    @JoinColumn(name = "userGroupId", nullable = false, columnDefinition = "varchar(36)")
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", nullable = false, columnDefinition = "varchar(36)")
    private User user;

    @Column(name = "userAdmin")
    @ColumnDefault(value = "0")
    private Integer userAdmin;

    public UserGroupUser() {
    }

    public UserGroupUser(UserGroupUserId userGroupUserId) {
        this.userGroupUserId = userGroupUserId;
    }

    public UserGroupUser(String userId, String userGroupId) {
        this.userGroupUserId = new UserGroupUserId(userId, userGroupId);
    }

    public UserGroupUser(String userId, String userGroupId, Integer userAdmin) {
        this.userGroupUserId = new UserGroupUserId(userId, userGroupId);
        this.userAdmin = userAdmin;
    }


    public UserGroupUser(UserGroupUserId userGroupUserId, Integer userAdmin) {
        super();
        this.userGroupUserId= userGroupUserId;
        this.userAdmin = userAdmin;
    }

    public UserGroupUser(UserGroupUserId userGroupUserId, UserGroup userGroup, User user, Integer userAdmin) {
        this.userGroupUserId = userGroupUserId;
        this.userGroup = userGroup;
        this.user = user;
        this.userAdmin = userAdmin;
    }

    public UserGroupUserId getUserGroupUserId() {
        return userGroupUserId;
    }

    public void setUserGroupUserId(UserGroupUserId userGroupUserId) {
        this.userGroupUserId = userGroupUserId;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(Integer userAdmin) {
        this.userAdmin = userAdmin;
    }
}