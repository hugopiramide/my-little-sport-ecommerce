package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.request.UpdateUserRequestDTO;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.vo.Birthday;
import com.ecommerce.backend.model.vo.PersonalData;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByPersonalDataEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByPersonalDataUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findExpiredNonVerifiedUsers(Instant now) {
        return userRepository.findByEmailVerifiedFalseAndEmailVerificationCodeExpiryBefore(now);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void deleteAll(List<User> users) {
        userRepository.deleteAll(users);
    }

    @Override
    @Transactional
    public User updateUserData(Long id, UpdateUserRequestDTO dto) {
        User user = getById(id);
        PersonalData pd = user.getPersonalData();
        if (pd == null) {
            pd = new PersonalData();
        }
        pd.setName(dto.name());
        pd.setSurname(dto.surname());
        pd.setEmail(dto.email());
        pd.setProfileImgUrl(dto.profileImgUrl());
        
        if (dto.birthday() != null) {
            pd.setBirthday(new Birthday(dto.birthday()));
        } else {
            pd.setBirthday(null);
        }
        
        user.setPersonalData(pd);
        return userRepository.save(user);
    }
}
