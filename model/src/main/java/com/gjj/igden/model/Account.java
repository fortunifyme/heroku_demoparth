package com.gjj.igden.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "account")
public class Account implements UserDetails {


    private Integer id;
    private String accountName;
    private String email;
    private String additionalInfo;
    private String password;
    private List<IWatchListDesc> descriptions;
    private String creationDate;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();

    public Account() {
    }

    public Account(String accountName, String email, String additionalInfo, String password,
                   List<IWatchListDesc> descriptions, String creationDate) {
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
        this.password = password;
        this.descriptions = descriptions;
        this.creationDate = creationDate;
    }

    public Account(int id, String accountName, String email,
                   String additionalInfo, String password,
                   List<IWatchListDesc> dataSets, String creationDate) {
        this.id = id;
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
        this.password = password;
        this.descriptions = dataSets;
        this.creationDate = creationDate;
    }

    public Account(String accountName, String email,
                   String additionalInfo) {
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
    }

    public Account(Integer id, String accountName, String email, String additionalInfo,
                   String creationDate) {
        this.id = id;
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
        this.creationDate = creationDate;
    }

/*  public Account(String accountName, String email, String additionalInfo,
                 List<WatchListDesc> dataSets) {
    this(null, accountName, email, additionalInfo, dataSets);
  }*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_Id")
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account(int accountId, String accountName, String email, String additionalInfo) {
        this(accountId, accountName, email, additionalInfo, (String) null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "creation_date")
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "additional_info")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    @Column(name = "account_name", unique = true)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Transient
    public List<IWatchListDesc> getAttachedWatchedLists() {
        return descriptions;
    }

    public void setDescriptions(List<IWatchListDesc> descriptions) {
        this.descriptions = descriptions;
    }

    @Transient
    public List<IWatchListDesc> getDataSets() {
        return descriptions;
    }

    public void setDataSets(List<IWatchListDesc> dataSets) {
        this.descriptions = dataSets;
    }


    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(getEmail(), accountName);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account && ((Account) obj).getEmail().equals(this.getEmail()) &&
                Objects.equals(((Account) obj).accountName, this.accountName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" [id=");
        builder.append(id);
        builder.append(", accountName= ");
        builder.append(accountName);
        builder.append(", email= ");
        builder.append(getEmail());
        builder.append(", additionalInfo= ");
        builder.append(additionalInfo);
        builder.append(", data_containers Sets names= ");
        builder.append(descriptions);
        builder.append("]\n");
        return builder.toString();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    @Transient
    public String getUsername() {
        return getAccountName();
    }


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }


    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
