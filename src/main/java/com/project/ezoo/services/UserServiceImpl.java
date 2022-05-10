package com.project.ezoo.services;

import com.project.ezoo.exceptions.ResourceNotFoundException;
import com.project.ezoo.model.Role;
import com.project.ezoo.model.User;
import com.project.ezoo.model.UserRoles;
import com.project.ezoo.model.Useremail;
import com.project.ezoo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userrepo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userrepo.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public User findUserById(long id) throws ResourceNotFoundException {
        return userrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public User findByName(String name) {
        User uu = userrepo.findByUsername(name.toLowerCase());
        if(uu == null){
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public void delete(long id) {
        userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User id " + id + " not found!"));
        userrepo.deleteById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();
        if(user.getUserid() != 0){
            userrepo.findById(user.getUserid()).orElseThrow(()->new ResourceNotFoundException("User id " + user.getUserid()  + " not found!"));
            newUser.setUserid(user.getUserid());
        }
        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.getRoles().clear();

        for(UserRoles ur : user.getRoles()){
            Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
            newUser.getRoles().add(new UserRoles(newUser,addRole));
        }

        newUser.getUseremails().clear();
        for(Useremail ue : user.getUseremails()){
            newUser.getUseremails().add(new Useremail(ue.getUseremail(), newUser));
        }
        return userrepo.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);

        // update own thing
        // admin update
        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername()
                        .toLowerCase());
            }

            if (user.getRoles()
                    .size() > 0)
            {
                currentUser.getRoles()
                        .clear();
                for (UserRoles ur : user.getRoles())
                {
                    Role addRole = roleService.findRoleById(ur.getRole()
                            .getRoleid());

                    currentUser.getRoles()
                            .add(new UserRoles(currentUser,
                                    addRole));
                }
            }

            if (user.getUseremails()
                    .size() > 0)
            {
                currentUser.getUseremails()
                        .clear();
                for (Useremail ue : user.getUseremails())
                {
                    currentUser.getUseremails()
                            .add(new Useremail(ue.getUseremail(), currentUser));
                }
            }

            return userrepo.save(currentUser);
        } else
        {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        userrepo.deleteAll();
    }
}
