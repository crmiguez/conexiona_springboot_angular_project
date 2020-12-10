package com.practicas.conexiona.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity(name = "User")
@Table
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Size(max = 36)
    @Column(name = "userId")
    private String userId;

    @JoinColumn(name = "accountId", referencedColumnName = "accountId", nullable = false)
    @ManyToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account account;

    @Column(name = "userName")
    private String userName;

    @Column(name = "emailAddress")
    private String emailAddress;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "enabled")
    @ColumnDefault(value = "1")
    private Integer enabled;

    @Column(name = "lastLogin")
    private String lastLogin;

    @OneToMany(mappedBy = "user")
    @org.springframework.data.annotation.Transient
    @JsonIgnore
    private Set<UserGroupUser> userGroups = new HashSet<>();

    public User() {
    }

    public User (String userId, String accountId, String userName, String emailAddress, String password, Integer enabled, String lastLogin) {
        this.userId = userId;
        this.account = new Account(accountId);
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.enabled = enabled;
        this.lastLogin = lastLogin;
    }

    public User(String userId, Account account, String userName, String emailAddress, String password, Integer enabled, String lastLogin) {
        this.userId = userId;
        this.account = account;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.enabled = enabled;
        this.lastLogin = lastLogin;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        this.getPermissionList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });

        // Extract list of roles (ROLE_name)
        this.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });

        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled() == 1;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<UserGroupUser> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroupUser> userGroupUser) {
        this.userGroups = userGroupUser;
    }



    public List<String> getRoleList(){
        List<String> userRoleList = new ArrayList<>();
        if (!userGroups.isEmpty()) {
            for (UserGroupUser usu : userGroups) {
                if (usu.getUserAdmin() == 1)
                    userRoleList.add("ADMIN");
                else
                    userRoleList.add("GUEST");
            }
            return userRoleList;
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> getPermissionList(){
        List<String> userPermissionsList = new ArrayList<>();
        if (!userGroups.isEmpty()) {
            for (UserGroupUser usu : userGroups) {
                if (usu.getUserAdmin() == 1) {
                    userPermissionsList.add("PERM_CREATE_ACCOUNT");
                    userPermissionsList.add("PERM_READ_ACCOUNT");
                    userPermissionsList.add("PERM_READ_ALL_ACCOUNTS");
                    userPermissionsList.add("PERM_UPDATE_ACCOUNT");
                    userPermissionsList.add("PERM_DELETE_ACCOUNT");
                    userPermissionsList.add("PERM_CREATE_USER");
                    userPermissionsList.add("PERM_READ_USER");
                    userPermissionsList.add("PERM_READ_ALL_USERS");
                    userPermissionsList.add("PERM_UPDATE_USER");

                } else {
                    userPermissionsList.add("PERM_READ_ALL_USERS");
                }
            }
            return userPermissionsList;
        } else {
            return new ArrayList<>();
        }
    }




}
