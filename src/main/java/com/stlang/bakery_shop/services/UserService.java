package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.domains.enums.Role;
import com.stlang.bakery_shop.dto.RegisterDTO;
import com.stlang.bakery_shop.exceptions.DataExistingException;
import com.stlang.bakery_shop.repositories.UserRepository;
import com.stlang.bakery_shop.services.iservices.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public Integer createUser(User user) {
        User saveUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(saveUser != null)
            return -2;
        User existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        if(existingUser != null)
            return -3;
        if(user.getRole() == null){
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        return 1;
    }

    @Override
    public Integer updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            User existUserWithEmail = userRepository.findByEmailAndIdNot(user.getEmail(), user.getId());
            if(existUserWithEmail != null)
                return -2;
            User existUserWithPhone = userRepository.findByPhoneNumberAndIdNot(user.getPhoneNumber(), user.getId());
            if(existUserWithPhone != null)
                return -3;
            user.setPassword(existingUser.getPassword());
            userRepository.save(user);
            return 1;
        }else
            return -1;
    }

    @Override
    public Integer deleteUser(Integer userId, String email) {
        User existingUser = userRepository.findById(Long.valueOf(userId)).orElse(null);
        if(existingUser != null) {
            if(email.equals(existingUser.getEmail()))
                return -2;
            userRepository.delete(existingUser);
            return 1;
        }
        return -1;
    }

    @Override
    public User registerDTOToUser(RegisterDTO registerDTO) {
        User user = User.builder()
                .fullname(registerDTO.getFullName())
                .email(registerDTO.getEmail())
                .address(registerDTO.getAddress())
                .phoneNumber(registerDTO.getPhone())
                .password(registerDTO.getPassword())
                .isActive(true)
                .role(Role.USER)
                .build();
        return user;
    }

    @Override
    public int getTotalUsers() {
        return userRepository.getTotalUsers();
    }
}
