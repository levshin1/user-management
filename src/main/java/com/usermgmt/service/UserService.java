package com.usermgmt.service;

import com.usermgmt.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    boolean isEmailTakenByOther(String email, Long excludeId);
}
