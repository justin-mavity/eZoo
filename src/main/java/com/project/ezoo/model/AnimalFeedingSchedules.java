package com.project.ezoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "animalfeedingschedules")
@IdClass(AnimalFeedingSchedulesId.class)
public class AnimalFeedingSchedules extends Auditable implements Serializable {
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "animalid")
    @JsonIgnoreProperties( value = "feedingSchedules", allowSetters = true)
    private Animal animal;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "scheduleid")
    @JsonIgnoreProperties(value = "animals", allowSetters = true)
    private FeedingSchedule feedingSchedule;

    public AnimalFeedingSchedules() {
    }

    public AnimalFeedingSchedules(Animal animal, FeedingSchedule feedingSchedule) {
        this.animal = animal;
        this.feedingSchedule = feedingSchedule;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public FeedingSchedule getFeedingSchedule() {
        return feedingSchedule;
    }

    public void setFeedingSchedule(FeedingSchedule feedingSchedule) {
        this.feedingSchedule = feedingSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalFeedingSchedules that = (AnimalFeedingSchedules) o;
        return ((animal == null) ? 0 : animal.getAnimalid()) == ((that.animal == null) ? 0 : that.animal.getAnimalid()) &&
                ((feedingSchedule == null) ? 0 : feedingSchedule.getScheduleid()) == ((that.feedingSchedule == null) ? 0 : that.feedingSchedule.getScheduleid());
    }

    @Override
    public int hashCode() {
        return 37;
    }
}
