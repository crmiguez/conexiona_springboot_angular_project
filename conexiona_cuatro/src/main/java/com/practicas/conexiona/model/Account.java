package com.practicas.conexiona.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "Account")
@Table
public class Account implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Size(max = 36)
    @Column(name = "accountId")
    private String accountId;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "company")
    private String company;

    @Column(name = "address")
    private String address;

    @Column(name = "emailAddress")
    private String emailAddress;

    @Column(name = "dicomId")
    private String dicomId;

    @Column(name = "enabled")
    @ColumnDefault(value = "1")
    private Integer enabled;

    @OneToMany (mappedBy = "account")
    @org.springframework.data.annotation.Transient
    @JsonIgnore
    Set<User> users = new HashSet<>();

    @OneToMany (mappedBy = "account")
    @org.springframework.data.annotation.Transient
    @JsonIgnore
    Set<UserGroup> userGroups = new HashSet<>();

    public Account() {

    }

    public Account(String accountId) {
        this.accountId = accountId;
    }

    public Account(String accountId, String accountName, String company, String address, String emailAddress, String dicomId, Integer enabled) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.company = company;
        this.address = address;
        this.emailAddress = emailAddress;
        this.dicomId = dicomId;
        this.enabled = enabled;
    }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDicomId() {
        return dicomId;
    }

    public void setDicomId(String dicomId) {
        this.dicomId = dicomId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
