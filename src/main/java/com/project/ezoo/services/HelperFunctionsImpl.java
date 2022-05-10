package com.project.ezoo.services;

import com.project.ezoo.exceptions.ResourceNotFoundException;
import com.project.ezoo.model.ValidationError;
import org.springframework.stereotype.Service;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.ArrayList;
import java.util.List;


@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions{
    public List<ValidationError> getConstraintViolation(Throwable cause){
        while((cause != null) && !(cause instanceof ConstraintViolationException || cause instanceof MethodArgumentNotValidException)){
            System.out.println(cause.getCause().toString());
            cause = cause.getCause();
        }
        List<ValidationError> listVE = new ArrayList<>();

        if(cause != null){
            if(cause instanceof ConstraintViolationException){
                ConstraintViolationException ex = (ConstraintViolationException) cause;
                ValidationError newVe = new ValidationError();
                newVe.setCode(ex.getMessage());
                newVe.setMessage(ex.getConstraintName());
                listVE.add(newVe);
            }else{
                if(cause instanceof MethodArgumentNotValidException){
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
                    for(FieldError err : fieldErrors){
                        ValidationError newVe = new ValidationError();
                        newVe.setCode(err.getField());
                        newVe.setMessage(err.getDefaultMessage());
                        listVE.add(newVe);
                    }
                }else{
                    System.out.println("Error in producing constraint violations exceptions. " +
                            "If we see this in the console a major logic error has occurred in the " +
                            "helperfunction.getConstraintViolation method that we should investigate. " +
                            "Note the application will keep running as this only affects exception reporting!");
                }
            }
        }
        return listVE;
    }

    @Override
    public boolean isAuthorizedToMakeChange(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(username.equalsIgnoreCase(authentication.getName().toLowerCase()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return true;
        }else{
            throw new ResourceNotFoundException(authentication.getName() + " not authorized to make changes");
        }
    }
}
