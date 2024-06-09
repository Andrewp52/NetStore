package org.example.netstore.common.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserDto implements Serializable {
    private boolean quotaEnabled;
    private long quota;
    private long quotaRemains;
    private Integer id;
    private String username;

    public UserDto(Integer id, String username, boolean quotaEnabled, long quota, long quotaRemains) {
        this.id = id;
        this.username = username;
        this.quotaEnabled = quotaEnabled;
        this.quota = quota;
        this.quotaRemains = quotaRemains;
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

    public boolean isQuotaEnabled() {
        return quotaEnabled;
    }

    public long getQuota() {
        return quota;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(username, userDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public long getQuotaRemains() {
        return quotaRemains;
    }

    public long increaseQuotaRemains(long amount){
        quotaRemains += amount;
        return quotaRemains;
    }

    public long decreaseQuotaRemains(long amount){
        quotaRemains -= amount;
        return quotaRemains;
    }
}
