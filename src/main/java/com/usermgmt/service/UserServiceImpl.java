package com.usermgmt.service;

import com.usermgmt.entity.User;
import com.usermgmt.enums.Role;
import com.usermgmt.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> search(String query, Role role, Sort sort) {
        boolean hasQuery = query != null && !query.isBlank();
        boolean hasRole = role != null;
        String q = hasQuery ? query.trim() : null;

        if (hasQuery && hasRole) return userRepository.searchByRole(q, role, sort);
        if (hasQuery)            return userRepository.search(q, sort);
        if (hasRole)             return userRepository.findByRole(role, sort);
        return userRepository.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailTakenByOther(String email, Long excludeId) {
        if (excludeId == null) {
            return userRepository.findByEmail(email).isPresent();
        }
        return userRepository.existsByEmailAndIdNot(email, excludeId);
    }
}
