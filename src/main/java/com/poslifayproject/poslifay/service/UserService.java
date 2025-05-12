package com.poslifayproject.poslifay.service;

import com.poslifayproject.poslifay.dto.UserInfoDto;
import com.poslifayproject.poslifay.model.Users;
import com.poslifayproject.poslifay.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getUserForServiceRequirements(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("user bulunamadı"));
    }
    public UserInfoDto getUserForUserProfilePage()
    {

    }

}
