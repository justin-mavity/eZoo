package com.project.ezoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleid;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "role",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "role",
        allowSetters = true)
    private Set<UserRoles> users = new HashSet<>();

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRoles> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRoles> users) {
        this.users = users;
    }
}
