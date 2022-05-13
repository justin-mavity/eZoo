package com.project.ezoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "FeedingSchedule",
    description = "A feeding schedule record with a primary key id, feeding_Time String")
@Entity
@Table(name = "feedingSchedules")
public class FeedingSchedule extends Auditable{
    @ApiModelProperty(name = "scheduleid",
        value = "primary key for feedingSchedule",
        required = true,
        example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long scheduleid;

    @ApiModelProperty(name = "feeding_Time",
        value = "feeding time String",
        required = true,
        example = "12:00")
    @NotNull
    @Column(unique = true)
    private String feeding_Time;

    @OneToMany(mappedBy = "feedingSchedule",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "feedingschedule",
        allowSetters = true)
    private Set<AnimalFeedingSchedules> animals = new HashSet<>();

    public FeedingSchedule() {
    }

    public FeedingSchedule(String feeding_Time) {
        this.feeding_Time = feeding_Time;
    }

    public long getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(long scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getFeeding_Time() {
        return feeding_Time;
    }

    public void setFeeding_Time(String feeding_Time) {
        this.feeding_Time = feeding_Time;
    }

    @Override
    public String toString() {
        return "Feeding schedule [scheduleid = " + scheduleid + ", feeding time = " + feeding_Time + "]";
    }

}
