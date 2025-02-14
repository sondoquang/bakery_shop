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
    public User addUser(User user) {
        // 1. Kiểm tra sdt đã tồn tại hay chưa //
        User existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        User saveUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(existingUser == null && saveUser == null) {
            // 3. Thêm user : Return User vua them
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        // 1. Kiểm tra sự tồn tại của user
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            return userRepository.save(user);
        }else
            return null;
    }

    @Override
    public void deleteUser(User user) {
        // 1. Kiểm tra sự tồn tại của user
        userRepository.findById(user.getId()).ifPresent(userRepository::delete);
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
}
