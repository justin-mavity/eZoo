package com.project.ezoo.services;

import com.project.ezoo.exceptions.ResourceFoundException;
import com.project.ezoo.exceptions.ResourceNotFoundException;
import com.project.ezoo.model.User;
import com.project.ezoo.model.Useremail;
import com.project.ezoo.repository.UseremailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Transactional
@Service(value = "useremailService")
public class UseremailServiceImpl implements UseremailService{
    @Autowired
    private UseremailRepository useremailrepo;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperfunctions;

    @Override
    public List<Useremail> findAll() {
        List<Useremail> list = new ArrayList<>();

        useremailrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Useremail findUseremailById(long id) {
        return useremailrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Useremail with id id " + id + " not found!"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if(useremailrepo.findById(id).isPresent()){
            if(helperfunctions.isAuthorizedToMakeChange(useremailrepo.findById(id).get().getUser().getUsername())){
                useremailrepo.deleteById(id);
            }else{
                throw new ResourceNotFoundException("Useremail with id " + id + " not found!");
            }
        }
    }

    @Transactional
    @Override
    public Useremail update(long useremailid, String emailaddress) {
        if(useremailrepo.findById(useremailid).isPresent()){
            if(helperfunctions.isAuthorizedToMakeChange(useremailrepo.findById(useremailid).get().getUser().getUsername())){
                Useremail useremail = findUseremailById(useremailid);
                useremail.setUseremail(emailaddress.toLowerCase());
                return useremailrepo.save(useremail);
            } else{
                throw new ResourceNotFoundException("This user is not authorized to make change");
            }
        }else{
            throw new ResourceNotFoundException("Usereamil with id " + useremailid + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Useremail save(long userid, String emailaddress) {
        User currentUser = userService.findUserById(userid);

        if(helperfunctions.isAuthorizedToMakeChange(currentUser.getUsername())){
            Useremail newUserEmail = new Useremail(emailaddress, currentUser);
            return useremailrepo.save(newUserEmail);
        } else{
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }
}
