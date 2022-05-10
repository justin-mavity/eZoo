package com.project.ezoo.services;

import com.project.ezoo.exceptions.ResourceFoundException;
import com.project.ezoo.exceptions.ResourceNotFoundException;
import com.project.ezoo.model.Role;
import com.project.ezoo.repository.RoleRepository;
import com.project.ezoo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository rolerepo;

    @Autowired
    UserRepository userrepo;

    @Autowired UserAuditing userAuditing;

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        rolerepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role findRoleById(long id) {
        return rolerepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Transactional
    @Override
    public Role save(Role role) {
        if(role.getUsers().size() > 0){
            throw new ResourceFoundException("User Roles are not updates through Role.");
        }
        return rolerepo.save(role);
    }

    @Override
    public Role findByName(String name) {
        Role rr = rolerepo.findByNameIgnoreCase(name);
        if(rr != null){
            return rr;
        }else{
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        rolerepo.deleteAll();
    }

    @Transactional
    @Override
    public Role update(long id, Role role) {
        if(role.getName() == null){
            throw new ResourceNotFoundException("No role name found to update!");
        }
        if(role.getUsers().size() > 0){
            throw new ResourceFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
        }
        Role newRole = findRoleById(id);
        rolerepo.updateRoleName(userAuditing.getCurrentAuditor().get(), id, role.getName());
        return findRoleById(id);
    }
}
