package com.project.ezoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "useremails")
public class Useremail extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long useremailid;

    @NotNull
    @Email
    private String useremail;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "useremails",
        allowSetters = true)
    private User user;

    public Useremail() {
    }

    public Useremail(String useremail, User user) {
        this.useremail = useremail;
        this.user = user;
    }

    public long getUseremailid() {
        return useremailid;
    }

    public void setUseremailid(long useremailid) {
        this.useremailid = useremailid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
