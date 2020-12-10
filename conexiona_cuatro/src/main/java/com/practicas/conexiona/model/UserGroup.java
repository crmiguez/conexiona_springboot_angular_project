package com.practicas.conexiona.model;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "UserGroup")
@Table

public class UserGroup implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Size(max = 36)
    private String userGroupId;

    @JoinColumn(name = "accountId", referencedColumnName = "accountId", nullable = false)
    @ManyToOne
    private Account account;

    @Column(name = "userGroupName")
    private String userGroupName;

    @Column(name = "enabled")
    @ColumnDefault(value = "1")
    private Integer enabled;

    @OneToMany(
            mappedBy = "userGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserGroupUser> usersWithGroups = new HashSet<>();

    public UserGroup() {
    }

    public UserGroup(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public UserGroup(String userGroupId, Account account, String userGroupName, Integer enabled) {
        this.userGroupId = userGroupId;
        this.account = account;
        this.userGroupName = userGroupName;
        this.enabled = enabled;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Set<UserGroupUser> getUsers() {
        return usersWithGroups;
    }

    public void setUsers(Set<UserGroupUser> usersWithGroups) {
        this.usersWithGroups = usersWithGroups;
    }
}
