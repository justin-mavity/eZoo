package com.project.ezoo.services;

import com.project.ezoo.model.Useremail;

import java.util.List;

public interface UseremailService {
    List<Useremail> findAll();

    Useremail findUseremailById(long id);

    void delete(long id);

    Useremail update(long useremailid, String emailaddress);

    Useremail save(long userid, String emailaddress);
}
