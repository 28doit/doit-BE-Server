package com.example.photologger.photo.repository;

import com.example.photologger.photo.domain.User;

import java.util.Optional;

public interface UserRepository{
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nicName);
    User login(User user);

}
