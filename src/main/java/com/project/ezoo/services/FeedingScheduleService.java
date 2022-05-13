package com.project.ezoo.services;

import com.project.ezoo.model.FeedingSchedule;

import java.util.List;

public interface FeedingScheduleService {
    List<FeedingSchedule> findAll();
    FeedingSchedule findScheduleById(long id);
}
