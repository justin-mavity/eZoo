package com.project.ezoo.services;

import com.project.ezoo.exceptions.ResourceNotFoundException;
import com.project.ezoo.model.FeedingSchedule;
import com.project.ezoo.repository.FeedingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("feedingScheduleService")
public class FeedingScheduleServiceImpl implements FeedingScheduleService{
    @Autowired
    FeedingScheduleRepository fsrepos;

    @Override
    public List<FeedingSchedule> findAll() {
        List<FeedingSchedule> list = new ArrayList<>();
        fsrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public FeedingSchedule findScheduleById(long id) {
        return fsrepos.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Feeding Schedule with id " + id + " Not Found!"));
    }
}
