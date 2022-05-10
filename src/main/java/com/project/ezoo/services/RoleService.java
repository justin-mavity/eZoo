package com.project.ezoo.services;

import com.project.ezoo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findRoleById(long id);
    Role save(Role role);
    Role findByName(String name);
    void deleteAll();
    Role update(long id, Role role);
}
