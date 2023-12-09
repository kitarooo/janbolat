package com.example.Weeek7.service;

import com.example.Weeek7.entity.Role;
import com.example.Weeek7.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get() ;
    }
}
