package com.gjj.igden.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persistent_logins ")
public class PersistentLogins {

    private String series;
    private String username;
    private String token;
    private Date lastUsed;

    @Id
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used")
    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
