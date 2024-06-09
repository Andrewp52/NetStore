package org.example.netstore.nettyserver;

import java.util.Objects;

public class User {
    private Integer id;
    private String username;
    private String password;
    private boolean quotaEnabled;
    private long quota;
    private long quotaRemains;

    public User(Integer id, String username, String password, boolean quotaEnabled, long quota, long quotaRemains) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.quotaEnabled = quotaEnabled;
        this.quota = quota;
        this.quotaRemains = quotaRemains;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isQuotaEnabled() {
        return quotaEnabled;
    }

    public void setQuotaEnabled(boolean quotaEnabled) {
        this.quotaEnabled = quotaEnabled;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    public long getQuotaRemains() {
        return quotaRemains;
    }

    public void setQuotaRemains(long quotaRemains) {
        this.quotaRemains = quotaRemains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return quotaEnabled == user.quotaEnabled && Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, quotaEnabled);
    }
}
