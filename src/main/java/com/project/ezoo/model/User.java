package com.project.ezoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.sun.istack.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name="users")
public class User extends Auditable{
    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @NotNull
    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
            allowSetters = true)
    private List<Useremail> useremails = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
            allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    public User(){}

    public User(String username) {
        this.username = username;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Useremail> getUseremails() {
        return useremails;
    }

    public void setUseremails(List<Useremail> useremails) {
        this.useremails = useremails;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority(){
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for(UserRoles r : this.roles){
            String myRole = "ROLE_" + r.getRole()
                    .getName()
                    .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }
        return rtnList;
    }
}
