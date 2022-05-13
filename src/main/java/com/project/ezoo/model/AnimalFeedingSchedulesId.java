package com.project.ezoo.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AnimalFeedingSchedulesId implements Serializable {
    private long animal;

    private long feedingSchedule;

    public AnimalFeedingSchedulesId() {
    }

    public long getAnimal() {
        return animal;
    }

    public void setAnimal(long animal) {
        this.animal = animal;
    }

    public long getFeedingSchedule() {
        return feedingSchedule;
    }

    public void setFeedingSchedule(long feedingSchedule) {
        this.feedingSchedule = feedingSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalFeedingSchedulesId that = (AnimalFeedingSchedulesId) o;
        return animal == that.animal && feedingSchedule == that.feedingSchedule;
    }

    @Override
    public int hashCode() {
        return 37;
    }
}
